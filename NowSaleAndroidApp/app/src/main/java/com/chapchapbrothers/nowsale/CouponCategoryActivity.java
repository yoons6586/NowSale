package com.chapchapbrothers.nowsale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chapchapbrothers.nowsale.VO.ClientVO;
import com.chapchapbrothers.nowsale.VO.DangolWithMarketMenuImg;
import com.chapchapbrothers.nowsale.VO.LoginVO;
import com.chapchapbrothers.nowsale.VO.OwnerVO;
import com.chapchapbrothers.nowsale.http.AllService;
import com.chapchapbrothers.nowsale.http.ClientService;
import com.chapchapbrothers.nowsale.http.OwnerService;
import com.chapchapbrothers.nowsale.network.ConnectionDetector;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.extras.Base64;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{ // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private RelativeLayout alcohol,food,health,play,fashion,beauty,study,cafe,others;
    private Intent nowSaleIntent,loginIntent,ownerRegisterIntent,walletIntent;
    private ImageView alcohol_img,food_img,health_img,play_img,fashion_img,beauty_img,study_img,cafe_img,others_img;
    private Context context;
    private View nav_header_view,drawer;
    private Menu activity_main_drawer;
    private TextView nav_header_nickname;
//    private TextView nav_header_loginPlease;
    private LinearLayout nav_header_loginBtn;
    private Intent loginPopupIntent;
    final private int loginPopup = 1000;
    final private int loginActivity = 2000;
    final private int ownerCouponRegisterRequest = 4000;
    private final int loginResult=3000,ownerChangeResult=4001;
    private int nav_header_nickName_textSize=30;


    // 자동로그인을 위함
    private String sfName = "myFile";
    private String user_id,user_pw;
    private ClientService clientService;
    private OwnerService ownerService;
    private ViewPager indicatorViewPager;
    private SliderAdvertisementImageAdapter adapter;
    private TabLayout tabLayout;

    //네트워크
    private ConnectionDetector connectionDetector;
    private final int pleaseConnectToInternet = 356;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

//        LoadingAnimationApplication.getInstance().progressON(this, null);
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.yoonsung.nowsale", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        Log.e("화면","clientcouponcategoryactivity실행");
//        ownerCouponData = new OwnerCouponData();
        Intent intent = new Intent(this, Loading1Activity.class);
        startActivity(intent);

        context = getApplicationContext();
        nowSaleIntent = new Intent(this,FMainActivity.class);
        walletIntent = new Intent(this,ClientMenuActivity.class);
        loginIntent = new Intent(this,LoginActivity.class);
        ownerRegisterIntent = new Intent(CouponCategoryActivity.this,OwnerRegisterCoupon2.class);

        alcohol=findViewById(R.id.category_alcohol);
        food=findViewById(R.id.category_food);
        health = findViewById(R.id.category_health);
        play = findViewById(R.id.category_play);
        fashion = findViewById(R.id.category_fashion);
        beauty = findViewById(R.id.category_beauty);
        study=findViewById(R.id.category_study);
        cafe=findViewById(R.id.category_cafe);
        others=findViewById(R.id.category_others);

        alcohol_img = findViewById(R.id.alcohol_img);
        food_img=findViewById(R.id.food_img);
        health_img = findViewById(R.id.health_img);
        play_img = findViewById(R.id.play_img);
        fashion_img=findViewById(R.id.fashion_img);
        beauty_img = findViewById(R.id.beauty_img);
        study_img = findViewById(R.id.study_img);
        cafe_img=findViewById(R.id.cafe_img);
        others_img = findViewById(R.id.others_img);



    //네트워크
        connectionDetector = new ConnectionDetector(this);
        if(!connectionDetector.isConnectingToInternet()) {
            Intent network_intent = new Intent(this, Dialog.class);
            network_intent.putExtra("message", getString(R.string.please_connect_to_internet));
            startActivityForResult(network_intent,pleaseConnectToInternet);
        }

        AllService allService = Config.retrofit.create(AllService.class);
        Call<List<AdvImgVO>> ownerMenuRequest = allService.getAdvUri();
        ownerMenuRequest.enqueue(new Callback<List<AdvImgVO>>() {
            @Override
            public void onResponse(Call<List<AdvImgVO>> call, Response<List<AdvImgVO>> response) {
                List<AdvImgVO> advImgVOList = response.body();
                indicatorViewPager = (ViewPager) findViewById(R.id.view);
                adapter = new SliderAdvertisementImageAdapter(CouponCategoryActivity.this, advImgVOList);
                indicatorViewPager.setClipToPadding(false);
                indicatorViewPager.setAdapter(adapter);


                tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(indicatorViewPager, true);
            }

            @Override
            public void onFailure(Call<List<AdvImgVO>> call, Throwable t) {

            }
        });

        alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 0);
                startActivity(nowSaleIntent);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 1);
                startActivity(nowSaleIntent);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 2);
                startActivity(nowSaleIntent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 3);
                startActivity(nowSaleIntent);
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 4);
                startActivity(nowSaleIntent);
            }
        });
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 5);
                startActivity(nowSaleIntent);
            }
        });
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 6);
                startActivity(nowSaleIntent);
            }
        });
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 7);
                startActivity(nowSaleIntent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position", 8);
                startActivity(nowSaleIntent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    /*
    nav_header 설정하기 -> 처음에 로그인 안되어 있으면 당연히 로그인 해달라고 떠야함
     */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_header_view = navigationView.getHeaderView(0);

        nav_header_nickname = (TextView) nav_header_view.findViewById(R.id.nav_header_nickname);
//        nav_header_loginPlease = (TextView) nav_header_view.findViewById(R.id.nav_header_loginPlease);
        nav_header_loginBtn = (LinearLayout) nav_header_view.findViewById(R.id.nav_header_loginBtn);


        nav_header_nickname.setVisibility(View.GONE);
//        nav_header_loginPlease.setVisibility(View.VISIBLE);
        nav_header_loginBtn.setVisibility(View.VISIBLE);
        nav_header_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(loginIntent, loginActivity);
            }
        });

        nav_header_nickname.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body1);

//        navigationView.inflateMenu(0);
        activity_main_drawer = navigationView.getMenu(); // 네비게이션 메

        //ㅈㅏ동 로그인
        SharedPreferences sf = getSharedPreferences(sfName, MODE_PRIVATE);
        user_id = sf.getString("ID", "");
        user_pw = sf.getString("PW", "");

        activity_main_drawer.getItem(4).setVisible(false);

        //자동로그인해라
        clientService = Config.retrofit.create(ClientService.class);
        Call<List<ClientVO>> request = clientService.isLogin(new LoginVO(user_id, user_pw));
        request.enqueue(new Callback<List<ClientVO>>() {
            @Override
            public void onResponse(Call<List<ClientVO>> call, Response<List<ClientVO>> response) {
//                        int count = response.body();
                List<ClientVO> list = response.body();
                if (response.code() == 200) {
                    Config.clientVO = list.get(0);
                    Config.who_key = "C";
                    nav_header_nickname.setVisibility(View.VISIBLE);
//                    nav_header_loginPlease.setVisibility(View.GONE);
                    nav_header_loginBtn.setVisibility(View.GONE);
                    nav_header_nickname.setText("" + Config.clientVO.getNickName() + "님");
                    nav_header_nickname.setTextSize(nav_header_nickName_textSize);
                    activity_main_drawer.getItem(5).setVisible(false);
                } else {
                    ownerService = Config.retrofit.create(OwnerService.class);
                    Call<List<OwnerVO>> request = ownerService.isLogin(new LoginVO(user_id, user_pw));
                    request.enqueue(new Callback<List<OwnerVO>>() {
                        @Override
                        public void onResponse(Call<List<OwnerVO>> call, Response<List<OwnerVO>> response) {
                            List<OwnerVO> list = response.body();
                            if (response.code() == 200) {
                                Config.ownerVO = list.get(0);
                                Config.who_key = "O";

                                ownerService = Config.retrofit.create(OwnerService.class);
                                Call<Integer> couponCntRequest = ownerService.couponCount(Config.ownerVO.getOwner_key());
                                couponCntRequest.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        Config.ownerVO.setCoupon_cnt(response.body());

                                        Log.e("Login", "couponCount : " + Config.ownerVO.getCoupon_cnt());
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {

                                    }
                                });
                                ownerService = Config.retrofit.create(OwnerService.class);
                                Call<Integer> saleCntRequest = ownerService.saleCount(Config.ownerVO.getOwner_key());
                                saleCntRequest.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        Config.ownerVO.setSale_cnt(response.body());
                                        Log.e("Login", "saleCount : " + Config.ownerVO.getSale_cnt());
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {

                                    }
                                });
                                nav_header_nickname.setVisibility(View.VISIBLE);
//                                nav_header_loginPlease.setVisibility(View.GONE);
                                nav_header_loginBtn.setVisibility(View.GONE);
                                nav_header_nickname.setText("" + Config.ownerVO.getNickName() + "사장님");
                                nav_header_nickname.setTextSize(nav_header_nickName_textSize);

                                activity_main_drawer.getItem(5).setVisible(false);
                                activity_main_drawer.getItem(6).setVisible(true);
                                activity_main_drawer.getItem(7).setVisible(true);
                                activity_main_drawer.getItem(8).setVisible(true);
                                activity_main_drawer.getItem(9).setVisible(true);
                                activity_main_drawer.getItem(10).setVisible(true);
                                activity_main_drawer.getItem(0).setVisible(false);
                                activity_main_drawer.getItem(1).setVisible(false);
                                activity_main_drawer.getItem(2).setVisible(false);
                                activity_main_drawer.getItem(3).setVisible(false);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<OwnerVO>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ClientVO>> call, Throwable t) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_help){
            startActivity(new Intent(this,HelpActivity.class));
        }
        else if(id==R.id.nav_owner_menu){
            if(activity_main_drawer.getItem(6).isVisible()){
                activity_main_drawer.getItem(6).setVisible(false);
                activity_main_drawer.getItem(7).setVisible(false);
                activity_main_drawer.getItem(8).setVisible(false);
                activity_main_drawer.getItem(9).setVisible(false);
                activity_main_drawer.getItem(10).setVisible(false);
            }
            else{
                activity_main_drawer.getItem(6).setVisible(true);
                activity_main_drawer.getItem(7).setVisible(true);
                activity_main_drawer.getItem(8).setVisible(true);
                activity_main_drawer.getItem(9).setVisible(true);
                activity_main_drawer.getItem(10).setVisible(true);
            }
            return false; // false면 네비게이션 뷰 계속 열려있고 true면 닫혀있는다.
        }
        else if(id==R.id.nav_owner_registerd){
            walletIntent.putExtra("wallet",5); // 점주가 등록한 쿠폰 및 행사
            startActivity(walletIntent);
        }
        else if(id==R.id.nav_owner_register_coupon){
            ownerService = Config.retrofit.create(OwnerService.class);
            Call<Integer> couponCntRequest = ownerService.couponCount(Config.ownerVO.getOwner_key());
            couponCntRequest.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {


                    Config.ownerVO.setCoupon_cnt(response.body());

                    Log.e("Login","couponCount : "+Config.ownerVO.getCoupon_cnt());
                    if(Config.ownerVO.getCoupon_cnt()>=3) {
                        loginPopupIntent = new Intent(CouponCategoryActivity.this, LoginCancelPopupActivity.class);
                        loginPopupIntent.putExtra("down", 3);
                        startActivity(loginPopupIntent);
                    }
                    else {
                        ownerRegisterIntent.putExtra("choose", 1); // 쿠폰등록
                        startActivityForResult(ownerRegisterIntent,ownerCouponRegisterRequest);
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });

        }
        else if(id==R.id.nav_owner_register_discount){
//            ownerCouponData.setChoose("sale");
            ownerService = Config.retrofit.create(OwnerService.class);
            Call<Integer> saleCntRequest = ownerService.saleCount(Config.ownerVO.getOwner_key());
            saleCntRequest.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Config.ownerVO.setSale_cnt(response.body());
                    Log.e("Login","saleCount : "+Config.ownerVO.getSale_cnt());

                    if(Config.ownerVO.getSale_cnt()>=3) {
                        loginPopupIntent = new Intent(CouponCategoryActivity.this, LoginCancelPopupActivity.class);
                        loginPopupIntent.putExtra("down", 4);
                        startActivity(loginPopupIntent);
                    } else {
                        ownerRegisterIntent.putExtra("choose", 2); // 할인등록
                        startActivityForResult(ownerRegisterIntent,ownerCouponRegisterRequest);
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });

        }
        else if(id==R.id.nav_owner_frequenter){
            AllService service = Config.retrofit.create(AllService.class);
            //단골삭제
            Call<DangolWithMarketMenuImg> request = service.dangolwithImg(Config.ownerVO.getOwner_key(),0);
            request.enqueue(new Callback<DangolWithMarketMenuImg>() {
                @Override
                public void onResponse(Call<DangolWithMarketMenuImg> call, Response<DangolWithMarketMenuImg> response) {
                    DangolWithMarketMenuImg dangolWithMarketMenuImg = response.body();
                    Intent intent = new Intent(CouponCategoryActivity.this,OwnerDangolActivity.class);
                    intent.putExtra("dangolCnt",dangolWithMarketMenuImg.getDangol_count());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<DangolWithMarketMenuImg> call, Throwable t) {
                    Log.e("destroy", "OwnerInfoActivity의 http 코드 : " );
                }
            });

        }
        else if(id==R.id.nav_owner_change_info){
            startActivityForResult(new Intent(CouponCategoryActivity.this,OwnerChangeInfo.class),ownerChangeResult);
        }
        else if(Config.ownerVO.getOwner_key()!=0){
            loginPopupIntent = new Intent(CouponCategoryActivity.this,LoginCancelPopupActivity.class);
            loginPopupIntent.putExtra("isOwner",1);
            startActivityForResult(loginPopupIntent,loginPopup);

        }
        else if(Config.clientVO.getClient_key()==0) {
            startActivityForResult(new Intent(CouponCategoryActivity.this, LoginCancelPopupActivity.class), loginPopup);
        }
        else if (id == R.id.nav_coupon_wallet) {
            walletIntent.putExtra("wallet",0); // coupon 지갑
            startActivity(walletIntent);

        } else if (id == R.id.nav_change_info) {
            Intent clientMyInfoIntent = new Intent(this,ClientMyInfoActivity.class);
            clientMyInfoIntent.putExtra("clientOwner",1);
            startActivityForResult(clientMyInfoIntent,loginResult);
        } else if (id == R.id.nav_sale_wallet) {
            walletIntent.putExtra("wallet",1); // sale 지갑
            startActivity(walletIntent);
        } else if (id == R.id.nav_like_market) { // 단골가게
            walletIntent.putExtra("wallet",2); // 단골 가게 목록
            startActivity(walletIntent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==loginActivity){ // 로그인 시
            if(resultCode==2){ // client
                //데이터 받기
                nav_header_nickname.setVisibility(View.VISIBLE);
//                nav_header_loginPlease.setVisibility(View.GONE);
                nav_header_loginBtn.setVisibility(View.GONE);
                nav_header_nickname.setText(""+Config.clientVO.getNickName()+"님");
                nav_header_nickname.setTextSize(nav_header_nickName_textSize);
                activity_main_drawer.getItem(5).setVisible(false);
            }
        }
        if(requestCode==loginActivity){ // 로그인 시
            if(resultCode==3){ // owner
                //데이터 받기
                nav_header_nickname.setVisibility(View.VISIBLE);
//                nav_header_loginPlease.setVisibility(View.GONE);
                nav_header_loginBtn.setVisibility(View.GONE);
                nav_header_nickname.setText(""+Config.ownerVO.getNickName()+"님");
                nav_header_nickname.setTextSize(nav_header_nickName_textSize);
                activity_main_drawer.getItem(0).setVisible(false);
                activity_main_drawer.getItem(1).setVisible(false);
                activity_main_drawer.getItem(2).setVisible(false);
                activity_main_drawer.getItem(3).setVisible(false);
                activity_main_drawer.getItem(5).setVisible(false);
                activity_main_drawer.getItem(6).setVisible(true);
                activity_main_drawer.getItem(7).setVisible(true);
                activity_main_drawer.getItem(8).setVisible(true);
                activity_main_drawer.getItem(9).setVisible(true);
                activity_main_drawer.getItem(10).setVisible(true);
            }
        }
        if(requestCode==loginPopup){
            if(resultCode==RESULT_OK){
                startActivityForResult(loginIntent,loginActivity);
            }
            else{

            }
        }

        if(requestCode==loginResult){
            if(resultCode==RESULT_OK){
                Intent intentDialog = new Intent(this,Dialog.class);
                int cld = data.getIntExtra("change_logout_deleteClient",-1);
                switch (cld){
                    case 1: // change
                        intentDialog.putExtra("message", "변경되었습니다");
                        startActivity(intentDialog);
                        if(Config.clientVO.getClient_key()==0)
                            nav_header_nickname.setText(Config.ownerVO.getNickName());
                        else
                            nav_header_nickname.setText(Config.clientVO.getNickName());
                        break;
                    case 2: // logout
                        intentDialog.putExtra("message", "로그아웃되었습니다.");
                        startActivity(intentDialog);
                        nav_header_nickname.setVisibility(View.GONE);
//                        nav_header_loginPlease.setVisibility(View.VISIBLE);
                        nav_header_loginBtn.setVisibility(View.VISIBLE);
                        activity_main_drawer.getItem(0).setVisible(true);
                        activity_main_drawer.getItem(1).setVisible(true);
                        activity_main_drawer.getItem(2).setVisible(true);
                        activity_main_drawer.getItem(3).setVisible(true);
                        activity_main_drawer.getItem(5).setVisible(false);
                        activity_main_drawer.getItem(6).setVisible(false);
                        activity_main_drawer.getItem(7).setVisible(false);
                        activity_main_drawer.getItem(8).setVisible(false);
                        activity_main_drawer.getItem(9).setVisible(false);
                        activity_main_drawer.getItem(10).setVisible(false);
                        Config.clientVO.setId(null);
                        Config.clientVO.setPw(null);
                        Config.ownerVO.setId(null);
                        Config.ownerVO.setPw(null);
                        break;
                    case 3: // 회원탈퇴
                        Log.e("CouponCategoryActivity","회원탈퇴");
                        ClientService service = Config.retrofit.create(ClientService.class);
                        Call<ClientVO> request = service.deleteClient(Config.clientVO.getClient_key());
                        request.enqueue(new Callback<ClientVO>() {
                            @Override
                            public void onResponse(Call<ClientVO> call, Response<ClientVO> response) {
                                if(response.code()== HttpStatus.SC_OK){
                                    Log.e("delete","고객 정보 삭제");
                                }
                            }
                            @Override
                            public void onFailure(Call<ClientVO> call, Throwable t) {

                            }
                        });
                        intentDialog.putExtra("message", "탈퇴되었습니다.");
                        startActivity(intentDialog);
                        nav_header_nickname.setVisibility(View.GONE);
//                        nav_header_loginPlease.setVisibility(View.VISIBLE);
                        nav_header_loginBtn.setVisibility(View.VISIBLE);
                        break;
                }

            }
        }
        if(requestCode==ownerChangeResult){
            if(resultCode==RESULT_OK){
                Intent intentDialog = new Intent(this,Dialog.class);
                int cld = data.getIntExtra("change_logout_deleteClient",-1);
                switch (cld){
                    case 1:
                        nav_header_nickname.setText(Config.ownerVO.getNickName());
                        break;
                    case 2: // logout
                        intentDialog.putExtra("message", "로그아웃되었습니다.");
                        startActivity(intentDialog);
                        nav_header_nickname.setVisibility(View.GONE);
//                        nav_header_loginPlease.setVisibility(View.VISIBLE);
                        nav_header_loginBtn.setVisibility(View.VISIBLE);
                        activity_main_drawer.getItem(0).setVisible(true);
                        activity_main_drawer.getItem(1).setVisible(true);
                        activity_main_drawer.getItem(2).setVisible(true);
                        activity_main_drawer.getItem(3).setVisible(true);
                        activity_main_drawer.getItem(5).setVisible(false);
                        activity_main_drawer.getItem(6).setVisible(false);
                        activity_main_drawer.getItem(7).setVisible(false);
                        activity_main_drawer.getItem(8).setVisible(false);
                        activity_main_drawer.getItem(9).setVisible(false);
                        activity_main_drawer.getItem(10).setVisible(false);
                        Config.clientVO.setId(null);
                        Config.clientVO.setPw(null);
                        Config.ownerVO.setId(null);
                        Config.ownerVO.setPw(null);
                        break;
                }
            }
        }
        if(requestCode==ownerCouponRegisterRequest){
            if(resultCode==RESULT_OK) {
                Intent intentDialog = new Intent(this, Dialog.class);
                intentDialog.putExtra("message", "발급되었습니다");
                startActivity(intentDialog);
            }
        }

        //네트워크
        if(requestCode==pleaseConnectToInternet){
            finish();
        }



    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    public void onDestroy() {
        SharedPreferences sf = getSharedPreferences(sfName, 0);
        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
        if(Config.ownerVO.getOwner_key()!=0) {
            editor.putString("ID", Config.ownerVO.getId()); // 입력
            editor.putString("PW", Config.ownerVO.getPw()); // 입력
        } else if(Config.clientVO.getClient_key()!=0){
            editor.putString("ID", Config.clientVO.getId()); // 입력
            editor.putString("PW", Config.clientVO.getPw()); // 입력

        } else{
            editor.putString("ID",""); // 입력
            editor.putString("PW", ""); // 입력
        }

        editor.commit(); // 파일에 최종 반영함

        super.onDestroy();
    }
}


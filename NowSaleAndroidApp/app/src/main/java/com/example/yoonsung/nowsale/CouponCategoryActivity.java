package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class CouponCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{ // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private LinearLayout alcohol,food,health,play,fashion,beauty,study,cafe,others;
    private Intent nowSaleIntent,loginIntent,ownerRegisterIntent,walletIntent;
    private Context context;
    private View nav_header_view,drawer;
    private Menu activity_main_drawer;
    private TextView nav_header_nickname;
    private TextView nav_header_loginPlease;
    private LinearLayout nav_header_loginBtn;
    private OwnerCouponData ownerCouponData;
    private Intent loginPopupIntent;
    final private int loginPopup = 1000;
    final private int loginActivity = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));



        Log.e("화면","clientcouponcategoryactivity실행");
        ownerCouponData = new OwnerCouponData();
        Intent intent = new Intent(this, Loading1Activity.class);
        startActivity(intent);
        context = getApplicationContext();
        nowSaleIntent = new Intent(this,FMainActivity.class);
        walletIntent = new Intent(this,ClientMenuActivity.class);
        loginIntent = new Intent(this,LoginActivity.class);
        ownerRegisterIntent = new Intent(CouponCategoryActivity.this,OwnerRegisterCoupon1.class);

        alcohol=findViewById(R.id.category_alcohol);
        food=findViewById(R.id.category_food);
        health = findViewById(R.id.category_health);
        play = findViewById(R.id.category_play);
        fashion = findViewById(R.id.category_fashion);
        beauty = findViewById(R.id.category_beauty);
        study=findViewById(R.id.category_study);
        cafe=findViewById(R.id.category_cafe);
        others=findViewById(R.id.category_others);

        alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",0);
                startActivity(nowSaleIntent);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",1);
                startActivity(nowSaleIntent);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",2);
                startActivity(nowSaleIntent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",3);
                startActivity(nowSaleIntent);
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",4);
                startActivity(nowSaleIntent);
            }
        });
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",5);
                startActivity(nowSaleIntent);
            }
        });
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",6);
                startActivity(nowSaleIntent);
            }
        });
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",7);
                startActivity(nowSaleIntent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowSaleIntent.putExtra("position",8);
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

        nav_header_nickname= (TextView) nav_header_view.findViewById(R.id.nav_header_nickname);
        nav_header_loginPlease = (TextView) nav_header_view.findViewById(R.id.nav_header_loginPlease);
        nav_header_loginBtn = (LinearLayout) nav_header_view.findViewById(R.id.nav_header_loginBtn);


        nav_header_nickname.setVisibility(View.GONE);
        nav_header_loginPlease.setVisibility(View.VISIBLE);
        nav_header_loginBtn.setVisibility(View.VISIBLE);
        nav_header_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(loginIntent,loginActivity);
            }
        });

        nav_header_nickname.setTextAppearance(this,R.style.TextAppearance_AppCompat_Body1);

//        navigationView.inflateMenu(0);
        activity_main_drawer = navigationView.getMenu(); // 네비게이션 메
        /*Config.clientInfoData.setWho_key("O");
        if(Config.clientInfoData.getWho_key().equals("O")){
            activity_main_drawer.getItem(5).setVisible(true);
        }*/
//        activity_main_drawer.getItem(0).setVisible(false);
//        drawer = navigationView.get;
//        navigationView.get
//        activity_main_drawer = navigationView.getMenu(0);

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

        }
        else if(id==R.id.nav_owner_register_coupon){
            ownerCouponData.setChoose("coupon");
            ownerRegisterIntent.putExtra("OwnerCouponData",ownerCouponData);
            startActivity(ownerRegisterIntent);
        }
        else if(id==R.id.nav_owner_register_discount){
            ownerCouponData.setChoose("sale");
            ownerRegisterIntent.putExtra("OwnerCouponData",ownerCouponData);
            startActivity(ownerRegisterIntent);
        }
        else if(id==R.id.nav_owner_frequenter){

        }
        else if(id==R.id.nav_owner_change_info){

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
            startActivity(new Intent(CouponCategoryActivity.this,ClientMyInfoActivity.class));
        } else if (id == R.id.nav_sale_wallet) {
            walletIntent.putExtra("wallet",1); // sale 지갑
            startActivity(walletIntent);
        } else if (id == R.id.nav_like_market) {

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
                nav_header_loginPlease.setVisibility(View.GONE);
                nav_header_loginBtn.setVisibility(View.GONE);
                nav_header_nickname.setText(""+Config.clientVO.getNickName());
                nav_header_nickname.setTextSize(40);
                activity_main_drawer.getItem(5).setVisible(false);
            }
        }
        if(requestCode==loginActivity){ // 로그인 시
            if(resultCode==3){ // owner
                //데이터 받기
                nav_header_nickname.setVisibility(View.VISIBLE);
                nav_header_loginPlease.setVisibility(View.GONE);
                nav_header_loginBtn.setVisibility(View.GONE);
                nav_header_nickname.setText(""+Config.ownerVO.getNickName());
                nav_header_nickname.setTextSize(40);
                activity_main_drawer.getItem(5).setVisible(true);
            }
        }
        if(requestCode==loginPopup){
            if(resultCode==RESULT_OK){
                startActivityForResult(loginIntent,loginActivity);
            }
            else{

            }
        }


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


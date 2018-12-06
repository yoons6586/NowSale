package com.example.yoonsung.nowsale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.AllOwnerClientKeyVO;
import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.DangolWithMarketMenuImg;
import com.example.yoonsung.nowsale.VO.MenuVO;
import com.example.yoonsung.nowsale.http.AllService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerInfoActivity extends FragmentActivity implements OwnerInfoTabFragment1.OnFavSetListener { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent;

    private TextView titleText;
    private LinearLayout backBtn,linear;
    private FrameLayout frame;
    private SliderOwnerImageAdapter adapter;
    private ViewPager indicatorViewPager;
    private ViewPager ownerInfoViewPager;
    private CouponVO couponVO;
    private String category;
    private AllOwnerClientKeyVO allOwnerClientKeyVO;
    private DangolWithMarketMenuImg dangolWithMarketMenuImg;
    private AllService allService;
    private List<MenuVO> list;
    private TabLayout tabLayout,ownerPageTab,marketImgTab;
    private int what,position;
    private Intent resultIntent;
    private boolean mFav=false;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        titleText=findViewById(R.id.title_txt);
        ownerInfoViewPager =findViewById(R.id.ownerInfoPager);
        indicatorViewPager = (ViewPager) findViewById(R.id.view);
        frame = findViewById(R.id.frame);
        linear = findViewById(R.id.linear);

        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        get_intent=getIntent();
        couponVO = (CouponVO) get_intent.getSerializableExtra("CouponVO");
        dangolWithMarketMenuImg = (DangolWithMarketMenuImg) get_intent.getSerializableExtra("dangol");
        what = get_intent.getIntExtra("what",-1);
        position = get_intent.getIntExtra("position",-1);
        Log.e("OwnerInfoActivity","position : "+position);
        Log.e("Map","longitude : "+couponVO.getLongitude()+", latitude : "+couponVO.getLatitude());

        category=get_intent.getStringExtra("category");

        if(what==4) {
            resultIntent = new Intent();
            resultIntent.putExtra("mFav",mFav);
            resultIntent.putExtra("position",position);
            setResult(RESULT_OK,resultIntent);
        }
        /*
        viewPager(사진 슬랑이드 되는 것)
        indicator 추가
         */


        if(dangolWithMarketMenuImg.getMarketImgVOList()!=null)
            adapter = new SliderOwnerImageAdapter(this,couponVO.getOwner_key(),dangolWithMarketMenuImg);
        else {
            adapter = new SliderOwnerImageAdapter(this, couponVO.getOwner_key(), null);
        }
        indicatorViewPager.setAdapter(adapter);

        marketImgTab = (TabLayout) findViewById(R.id.tab_layout);
        if(dangolWithMarketMenuImg.getMarketImgVOList()!=null) {
            marketImgTab.setupWithViewPager(indicatorViewPager, true);
        }
        else
            marketImgTab.setVisibility(View.GONE);

        //tab 크기 조절
//        tabHost.getCurrentTab();


        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);


//        LinearLayout.LayoutParams frame_params = (LinearLayout.LayoutParams) frame.getLayoutParams();


//        frame_params.width = (int) (metrics.widthPixels);
//        frame_params.height = (int) (metrics.heightPixels)/3;
//
//        frame.setLayoutParams(frame_params);


        Log.e("OwnerInfoActivity","owner_key : "+couponVO.getOwner_key());
        Log.e("OwnerInfoActivity","client_key : "+Config.clientVO.getClient_key());
//        couponKey=intent.getIntExtra("CouponKey",0);
        switch (category){
            case "food" :
                titleText.setText("맛집");
                break;
            case "alcohol" :
                titleText.setText("술집");
                break;
        }

//        Call<String> request = service.isFavorite(couponVO.getOwner_key(),Config.clientInfoData.getUser_key())

        LinearLayout.LayoutParams paramv = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // Initializing the TabLayout
        ownerPageTab = (TabLayout) findViewById(R.id.ownerInfoTabLayout);
//        tabLayout.
//        ownerPageTab.addTab(ownerPageTab.newTab().setText("매장 정보"));
//        ownerPageTab.addTab(ownerPageTab.newTab().setText("쿠폰 및 할인소식"));




        // Initializing ViewPager


        // Creating TabPagerAdapter adapter
//        FragmentManager fm = getSupportFragmentManager();

        allService = Config.retrofit.create(AllService.class);
        Call<List<MenuVO>> ownerMenuRequest = allService.getMenuList(couponVO.getOwner_key());
        ownerMenuRequest.enqueue(new Callback<List<MenuVO>>() {
            @Override
            public void onResponse(Call<List<MenuVO>> call, Response<List<MenuVO>> response) {
                list = response.body();
                try{
                    Log.e("ListSize",""+list.size());
                }catch (NullPointerException e){

                }
//                sectionAdapter.addSection(new OwnerInfoTabFragment1.MovieSection("주요 상품", menuDatas));
                OwnerInfoTabPagerAdapter pagerAdapter = new OwnerInfoTabPagerAdapter(getSupportFragmentManager(), 2,couponVO,dangolWithMarketMenuImg,list);

                ownerInfoViewPager.setAdapter(pagerAdapter);
                ownerInfoViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(ownerPageTab));

                ownerPageTab.setupWithViewPager(ownerInfoViewPager);

                View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.owner_info_custom_tab, null, false);


                LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll);
                LinearLayout linearLayout2 = (LinearLayout) headerView.findViewById(R.id.ll2);

                ownerPageTab.getTabAt(0).setCustomView(linearLayoutOne);
                ownerPageTab.getTabAt(1).setCustomView(linearLayout2);

                ownerPageTab.setTabGravity(TabLayout.GRAVITY_FILL);
                ownerPageTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.appMainColor));

            }

            @Override
            public void onFailure(Call<List<MenuVO>> call, Throwable t) {

            }
        });


        // Set TabSelectedListener
        ownerPageTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ownerInfoViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onFavSet(boolean fav) {
        mFav=fav;
        Log.e("OwnerInfoActivity","mFav : "+mFav);
        if(what==4){
            resultIntent.putExtra("mFav",mFav);
            setResult(RESULT_OK,resultIntent);
        }
    }
    public Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return  size;
    }



}


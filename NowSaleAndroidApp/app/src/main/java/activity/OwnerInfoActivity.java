package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yoonsung.nowsale.Config;
import com.example.yoonsung.nowsale.OwnerInfoTabFragment1;
import com.example.yoonsung.nowsale.OwnerInfoTabPagerAdapter;
import com.example.yoonsung.nowsale.R;
import com.example.yoonsung.nowsale.SliderOwnerImageAdapter;
import com.example.yoonsung.nowsale.VO.AllOwnerClientKeyVO;
import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.IsFavoriteGetCountVO;
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
    private LinearLayout backBtn;
    private SliderOwnerImageAdapter adapter;
    private ViewPager indicatorViewPager;
    private ViewPager ownerInfoViewPager;
    private CouponVO couponVO;
    private String category;
    private AllOwnerClientKeyVO allOwnerClientKeyVO;
    private IsFavoriteGetCountVO isFavoriteGetCountVO;
    private AllService allService;
    private List<MenuVO> list;
    private TabLayout tabLayout;
    private int what,position;
    private Intent resultIntent;
    private boolean mFav=false;

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

        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        get_intent=getIntent();
        couponVO = (CouponVO) get_intent.getSerializableExtra("CouponVO");
        isFavoriteGetCountVO = (IsFavoriteGetCountVO) get_intent.getSerializableExtra("dangol");
        what = get_intent.getIntExtra("what",-1);
        position = get_intent.getIntExtra("position",-1);
        Log.e("OwnerInfoActivity","position : "+position);

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
        indicatorViewPager = (ViewPager) findViewById(R.id.view);

        adapter = new SliderOwnerImageAdapter(this,couponVO.getOwner_key(),isFavoriteGetCountVO.getMarket_img_cnt());
        indicatorViewPager.setAdapter(adapter);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(indicatorViewPager,true);


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

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.ownerInfoTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("매장 정보"));
        tabLayout.addTab(tabLayout.newTab().setText("쿠폰 및 할인소식"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        ownerInfoViewPager =findViewById(R.id.ownerInfoPager);

        // Creating TabPagerAdapter adapter
//        FragmentManager fm = getSupportFragmentManager();

        allService = Config.retrofit.create(AllService.class);
        Call<List<MenuVO>> ownerMenuRequest = allService.getMenuList(couponVO.getOwner_key());
        ownerMenuRequest.enqueue(new Callback<List<MenuVO>>() {
            @Override
            public void onResponse(Call<List<MenuVO>> call, Response<List<MenuVO>> response) {
                list = response.body();
//                sectionAdapter.addSection(new OwnerInfoTabFragment1.MovieSection("주요 상품", menuDatas));
                OwnerInfoTabPagerAdapter pagerAdapter = new OwnerInfoTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),couponVO,isFavoriteGetCountVO,list);
                ownerInfoViewPager.setAdapter(pagerAdapter);
                ownerInfoViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            }

            @Override
            public void onFailure(Call<List<MenuVO>> call, Throwable t) {

            }
        });


        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
}


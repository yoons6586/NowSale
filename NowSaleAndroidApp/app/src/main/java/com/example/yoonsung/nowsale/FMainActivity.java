package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yoonsung.nowsale.FAdapter;
import com.example.yoonsung.nowsale.R;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class FMainActivity extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int position;
    private Intent FMainActivityIntent;
    private LinearLayout backBtn;
    private TextView toolbar_title;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        FMainActivityIntent=getIntent();
        position = FMainActivityIntent.getIntExtra("position",0);

        Toolbar toolbar = findViewById(R.id.f_toolbar);

        toolbar_title = findViewById(R.id.tab_toolbar_title);
        setSupportActionBar(toolbar);
        backBtn=findViewById(R.id.frame_back);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appMainColor));

        //안드로이드 새로고침
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
//        mSwipeRefreshLayout.setOnRefreshListener(this);


        switch (position){
            case 0:
                toolbar_title.setText("술집");
                break;
            case 1:
                toolbar_title.setText("맛집");
                break;
            case 2:
                toolbar_title.setText("헬스");
                break;
            case 3:
                toolbar_title.setText("여가문화");
                break;
            case 4:
                toolbar_title.setText("패션잡화");
                break;
            case 5:
                toolbar_title.setText("뷰티");
                break;
            case 6:
                toolbar_title.setText("학습");
                break;
            case 7:
                toolbar_title.setText("카페");
                break;
            case 8:
                toolbar_title.setText("기타");
                break;
        }

        tabLayout.addTab(tabLayout.newTab().setText("술집"));
        tabLayout.addTab(tabLayout.newTab().setText("맛집"));

        tabLayout.addTab(tabLayout.newTab().setText("헬스"));
        tabLayout.addTab(tabLayout.newTab().setText("여가문화"));
        tabLayout.addTab(tabLayout.newTab().setText("패션잡화"));
        tabLayout.addTab(tabLayout.newTab().setText("뷰티"));
        tabLayout.addTab(tabLayout.newTab().setText("학습"));
        tabLayout.addTab(tabLayout.newTab().setText("카페"));
        tabLayout.addTab(tabLayout.newTab().setText("기타"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        pagerAdapter = new FAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position); // 안드로이드 탭 선택하는 방법!!
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                toolbar_title.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    protected void onResume() {
        super.onResume();
        pagerAdapter.notifyDataSetChanged();
    }
}
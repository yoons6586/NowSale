package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.http.AllService;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerInfoActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent;

    private TextView nameText;
    private TextView phoneText;
    private TextView introduceText,addressText,titleText,countText;
    private ImageView heart;
    private int owner_key=0;
    private Boolean favBool = false; // 즐겨찾기면 true 즐겨찾기 아니면 false
    private SliderOwnerImageAdapter adapter;
    private ViewPager viewPager;
    private CouponVO couponVO;
    private String category;
    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);

        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new SliderOwnerImageAdapter(this);
        viewPager.setAdapter(adapter);

        nameText=(TextView)findViewById(R.id.marketName);
        phoneText=(TextView)findViewById(R.id.marketPhone);
        introduceText=findViewById(R.id.marketIntroduce);
        addressText=findViewById(R.id.marketAddress);
        titleText=findViewById(R.id.title_txt);
        countText=findViewById(R.id.favorite_count);

        heart=findViewById(R.id.dangol);

        get_intent=getIntent();
        couponVO = (CouponVO) get_intent.getSerializableExtra("CouponVO");
        category=get_intent.getStringExtra("category");
//        couponKey=intent.getIntExtra("CouponKey",0);
        switch (category){
            case "food" :
                titleText.setText("맛집");
                break;
            case "alcohol" :
                titleText.setText("술집");
        }
        AllService service = Config.retrofit.create(AllService.class);

        Call<Integer> request = service.getFavoriteCount(couponVO.getOwner_key());
        request.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int count = response.body();
//                countText.setText(count);
                Log.e("count","count : "+count);
                countText.setText(""+count);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
//        Call<String> request = service.isFavorite(couponVO.getOwner_key(),Config.clientInfoData.getUser_key())

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.setImageResource(R.drawable.colorheart);

            }
        });





        nameText.setText(couponVO.getMarket_name());
        introduceText.setText(couponVO.getMarket_introduce());

    }

}


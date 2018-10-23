package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.AllOwnerClientKeyVO;
import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.IsFavoriteGetCountVO;
import com.example.yoonsung.nowsale.http.AllService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerInfoActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent;

    private TextView nameText;
    private TextView phoneText;
    private TextView introduceText,addressText,titleText,countText;
    private ImageView heart;
    private SliderOwnerImageAdapter adapter;
    private ViewPager viewPager;
    private CouponVO couponVO;
    private String category;
    private IsFavoriteGetCountVO isFavoriteGetCountVO;
    private Boolean favBool;
    private Intent loginNeedPopupIntent;
    private AllOwnerClientKeyVO allOwnerClientKeyVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);

        viewPager = (ViewPager) findViewById(R.id.view);
//        Glide.with(this).load("Config.url+"/drawable/owner/").into(imageView);

//        Glide.with(getActivity()).load(Config.url+"/drawable/owner/"+list.get(position).getLogo_img()).into(itemHolder.imgLogo);


        nameText=(TextView)findViewById(R.id.marketName);
        phoneText=(TextView)findViewById(R.id.marketPhone);
        introduceText=findViewById(R.id.marketIntroduce);
        addressText=findViewById(R.id.marketAddress);
        titleText=findViewById(R.id.title_txt);
        countText=findViewById(R.id.favorite_count);

        heart=findViewById(R.id.dangol);

        loginNeedPopupIntent = new Intent(this,LoginCancelPopupActivity.class);
        get_intent=getIntent();
        couponVO = (CouponVO) get_intent.getSerializableExtra("CouponVO");
        isFavoriteGetCountVO = (IsFavoriteGetCountVO) get_intent.getSerializableExtra("dangol");
//        count = get_intent.getIntExtra("dangol_count",0);

        favBool = isFavoriteGetCountVO.isDangol();
        countText.setText(""+isFavoriteGetCountVO.getDangol_count());
        category=get_intent.getStringExtra("category");

        adapter = new SliderOwnerImageAdapter(this,couponVO.getOwner_key());
        viewPager.setAdapter(adapter);

        Log.e("OwnerInfoActivity","owner_key : "+couponVO.getOwner_key());
        Log.e("OwnerInfoActivity","client_key : "+Config.clientVO.getClient_key());
//        couponKey=intent.getIntExtra("CouponKey",0);
        switch (category){
            case "food" :
                titleText.setText("맛집");
                break;
            case "alcohol" :
                titleText.setText("술집");
        }

//        Call<String> request = service.isFavorite(couponVO.getOwner_key(),Config.clientInfoData.getUser_key())

        if(isFavoriteGetCountVO.isDangol()){
            heart.setImageResource(R.drawable.colorheart);
        }
        else{
            heart.setImageResource(R.drawable.blackheart);
        }
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Config.clientVO.getClient_key()!=0) {
                    if (isFavoriteGetCountVO.isDangol()) {
                        isFavoriteGetCountVO.setDangol_count(isFavoriteGetCountVO.getDangol_count() - 1);
                        isFavoriteGetCountVO.setDangol(false);
                        heart.setImageResource(R.drawable.blackheart);
                        countText.setText("" + isFavoriteGetCountVO.getDangol_count());
                    } else {
                        isFavoriteGetCountVO.setDangol_count(isFavoriteGetCountVO.getDangol_count() + 1);
                        isFavoriteGetCountVO.setDangol(true);
                        heart.setImageResource(R.drawable.colorheart);
                        countText.setText("" + isFavoriteGetCountVO.getDangol_count());
                    }
                }
                else{
                    if(Config.ownerVO.getOwner_key()!=0)
                        loginNeedPopupIntent.putExtra("down",1);
                    else
                        loginNeedPopupIntent.putExtra("down",0);
                    startActivity(loginNeedPopupIntent);
                }

            }
        });

        nameText.setText(couponVO.getMarket_name());
        introduceText.setText(couponVO.getMarket_introduce());

    }
    @Override
    public void onDestroy() {
        allOwnerClientKeyVO = new AllOwnerClientKeyVO(couponVO.getOwner_key(), Config.clientVO.getClient_key());
        AllService service = Config.retrofit.create(AllService.class);
        if (favBool) {
            if (favBool != isFavoriteGetCountVO.isDangol()) {
                //단골삭제
                Call<String> request = service.deleteFavorite(allOwnerClientKeyVO);
                request.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("destroy", "OwnerInfoActivity의 http 코드 : " + response.code());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        } else {
            if (favBool != isFavoriteGetCountVO.isDangol()) {
                //단골등록
                Call<String> request = service.insertFavorite(allOwnerClientKeyVO);
                request.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("destroy", "OwnerInfoActivity의 http 코드 : " + response.code());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        }
        Log.e("destroy", "OwnerInfoActivity");
        super.onDestroy();
    }


}


package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.LoginVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;
import com.example.yoonsung.nowsale.http.ClientService;
import com.example.yoonsung.nowsale.http.OwnerService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent owner_intent, result_intent;
    private EditText id_edit;
    private EditText pw_edit;
    private String user_id, user_pw;
    private TextView loginBtn;
    private TextView signupBtn;
    private ClientService clientService;
    private OwnerService ownerService;
    private ImageView backBtn,logo,harin_coupon;
    private LinearLayout id_edit_layout;
    private RelativeLayout login_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        id_edit = (EditText) findViewById(R.id.id_text);
        pw_edit = (EditText) findViewById(R.id.pw_text);
        backBtn = findViewById(R.id.back);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signUpBtn);
        logo = findViewById(R.id.logo);
        harin_coupon = findViewById(R.id.harin_coupon);
        login_linear = findViewById(R.id.login_layout);
        id_edit_layout = findViewById(R.id.id_edit_layout);
//        client_intent = new Intent(this,)

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        RelativeLayout.LayoutParams logo_params = (RelativeLayout.LayoutParams) logo.getLayoutParams();
        FrameLayout.LayoutParams harin_params = (FrameLayout.LayoutParams) harin_coupon.getLayoutParams();
        FrameLayout.LayoutParams linear_params = (FrameLayout.LayoutParams) login_linear.getLayoutParams();
        RelativeLayout.LayoutParams edit_params = (RelativeLayout.LayoutParams) id_edit_layout.getLayoutParams();


        logo_params.width = (int) (metrics.widthPixels/3.6);
        logo_params.height = (int) (logo_params.width/1.8);
        harin_params.width = (int) (metrics.widthPixels/3);
        harin_params.height = harin_params.width;
        linear_params.topMargin = harin_params.height/2;
        edit_params.topMargin = harin_params.height/2+50;

        logo.setLayoutParams(logo_params);
        harin_coupon.setLayoutParams(harin_params);
        login_linear.setLayoutParams(linear_params);
        id_edit_layout.setLayoutParams(edit_params);

/*
        LinearLayout.LayoutParams alcohol_linear_params = (LinearLayout.LayoutParams) alcohol.getLayoutParams();
        LinearLayout.LayoutParams food_linear_params = (LinearLayout.LayoutParams) food.getLayoutParams();
        LinearLayout.LayoutParams health_linear_params = (LinearLayout.LayoutParams) health.getLayoutParams();

        RelativeLayout.LayoutParams img_params = (RelativeLayout.LayoutParams) alcohol_img.getLayoutParams();

        img_params.width = metrics.widthPixels/4;
        img_params.height = img_params.width;

        alcohol_img.setLayoutParams(img_params);
        food_img.setLayoutParams(img_params);
        health_img.setLayoutParams(img_params);
*/

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingAnimationApplication.getInstance().progressON(LoginActivity.this, Config.loadingContext);
                user_id = id_edit.getText().toString();
                user_pw = pw_edit.getText().toString();

                clientService = Config.retrofit.create(ClientService.class);
                Call<List<ClientVO>> request = clientService.isLogin(new LoginVO(user_id, user_pw));
                request.enqueue(new Callback<List<ClientVO>>() {
                    @Override
                    public void onResponse(Call<List<ClientVO>> call, Response<List<ClientVO>> response) {
//                        int count = response.body();
                        List<ClientVO> list = response.body();
                        if (response.code()==200) {
                            Config.clientVO = list.get(0);
                            Config.who_key = "C";
                            Config.clientVO.setPw(user_pw);
                            result_intent = new Intent();
                            setResult(2, result_intent);
                            finish();
                        } else {
                            ownerService = Config.retrofit.create(OwnerService.class);
                            Call<List<OwnerVO>> request = ownerService.isLogin(new LoginVO(user_id, user_pw));
                            request.enqueue(new Callback<List<OwnerVO>>() {
                                @Override
                                public void onResponse(Call<List<OwnerVO>> call, Response<List<OwnerVO>> response) {
                                    List<OwnerVO> list = response.body();
                                    if (response.code()==200) {
                                        Config.ownerVO = list.get(0);
                                        result_intent = new Intent();
                                        setResult(3, result_intent);
                                        Config.who_key = "O";

                                        ownerService = Config.retrofit.create(OwnerService.class);
                                        Call<Integer> couponCntRequest = ownerService.couponCount(Config.ownerVO.getOwner_key());
                                        couponCntRequest.enqueue(new Callback<Integer>() {
                                            @Override
                                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                Config.ownerVO.setCoupon_cnt(response.body());

                                                Log.e("Login","couponCount : "+Config.ownerVO.getCoupon_cnt());
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
                                                Log.e("Login","saleCount : "+Config.ownerVO.getSale_cnt());
                                            }

                                            @Override
                                            public void onFailure(Call<Integer> call, Throwable t) {

                                            }
                                        });


                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "없는 id 또는 pw입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    LoadingAnimationApplication.getInstance().progressOFF();
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
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity1.class));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadingAnimationApplication.getInstance().progressOFF();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
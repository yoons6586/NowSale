package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class SignUpActivity1 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private CheckBox box1,box2,box3,box4,box5;
    private Intent next_intent;
    private TextView btn;
    private ClientVO clientVO;
    private TextView txt1,txt2,txt3,txt4,txt5;
    private ImageView back;

    private RelativeLayout layout;
    private LinearLayout start_layout;
    private ImageView harin_coupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up1);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }


        clientVO = new ClientVO();
        next_intent = new Intent(this,SignUpActivity2.class);

        back = findViewById(R.id.back);
        box1 = findViewById(R.id.checkBox1);
        box2 = findViewById(R.id.checkBox2);
        box3 = findViewById(R.id.checkBox3);
        box4 = findViewById(R.id.checkBox4);
        box5 = findViewById(R.id.checkBox5);
        btn = findViewById(R.id.next);
        txt1 = findViewById(R.id.text1);
        txt2 = findViewById(R.id.text2);
        txt3 = findViewById(R.id.text3);
        txt4 = findViewById(R.id.text4);
        txt5 = findViewById(R.id.text5);

        layout = findViewById(R.id.layout);
        start_layout = findViewById(R.id.start_layout);
        harin_coupon = findViewById(R.id.harin_coupon);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        FrameLayout.LayoutParams harin_params = (FrameLayout.LayoutParams) harin_coupon.getLayoutParams();
        FrameLayout.LayoutParams linear_params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        RelativeLayout.LayoutParams edit_params = (RelativeLayout.LayoutParams) start_layout.getLayoutParams();

        harin_params.width = (int) (metrics.widthPixels/3);
        harin_params.height = harin_params.width;
        linear_params.topMargin = harin_params.height/2;
        edit_params.topMargin = harin_params.height/2+50;

        harin_coupon.setLayoutParams(harin_params);
        layout.setLayoutParams(linear_params);
        start_layout.setLayoutParams(edit_params);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box1.isChecked()){
                    box2.setChecked(true);
                    box3.setChecked(true);
                    box4.setChecked(true);
                    box5.setChecked(true);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.yellow_btn_selector));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.yellow_btn_selector));
                    }
                    btn.setTextColor(Color.BLACK);
                }
                else{
                    box2.setChecked(false);
                    box3.setChecked(false);
                    box4.setChecked(false);
                    box5.setChecked(false);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
        });
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box2.isChecked()){
                    if(box3.isChecked() && box4.isChecked()){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.yellow_btn_selector));
                        } else {
                            btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.yellow_btn_selector));
                        }
                        btn.setTextColor(Color.BLACK);
                    }
                }
                else{
                    if(box1.isChecked())
                        box1.setChecked(false);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
        });
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box3.isChecked()){
                    if(box2.isChecked() && box4.isChecked()){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.yellow_btn_selector));
                        } else {
                            btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.yellow_btn_selector));
                        }
                        btn.setTextColor(Color.BLACK);
                    }
                }
                else{
                    if(box1.isChecked())
                        box1.setChecked(false);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
        });
        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box4.isChecked()){
                    if(box3.isChecked() && box2.isChecked()){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.yellow_btn_selector));
                        } else {
                            btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.yellow_btn_selector));
                        }
                        btn.setTextColor(Color.BLACK);
                    }
                }
                else{
                    if(box1.isChecked())
                        box1.setChecked(false);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity1.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity1.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
        });
        box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!box5.isChecked())
                    if(box1.isChecked())
                        box1.setChecked(false);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box5.isChecked()){
                    clientVO.setAlarm_SMS("T");
                    clientVO.setAlarm_push("T");
                    clientVO.setAlarm_mail("T");
                }
                else{
                    clientVO.setAlarm_SMS("F");
                    clientVO.setAlarm_push("F");
                    clientVO.setAlarm_mail("F");
                }
                if(box2.isChecked() && box3.isChecked() && box4.isChecked()) {
                    next_intent.putExtra("ClientVO", clientVO);
                    startActivityForResult(next_intent,1);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
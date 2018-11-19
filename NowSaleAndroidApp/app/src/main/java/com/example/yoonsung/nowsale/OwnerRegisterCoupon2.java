package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.SaleVO;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OwnerRegisterCoupon2 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private EditText edit1,edit2;
    private TextView txt1,txt2,txtContent;
    private TextView next;
    private ImageView back;
    private CouponVO couponVO;
    private SaleVO saleVO;
    private int choose=0;

    private RelativeLayout layout;
    private LinearLayout start_layout;
    private ImageView harin_coupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register_coupon2);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        couponVO = new CouponVO();
        saleVO = new SaleVO();

        txtContent = findViewById(R.id.txt_content);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);

        layout = findViewById(R.id.layout);
        start_layout = findViewById(R.id.start_layout);
        harin_coupon = findViewById(R.id.harin_coupon);

        get_intent=getIntent();

        choose = get_intent.getIntExtra("choose",0);
        if(choose==2){

            txtContent.setText("어떤 내용의 할인행사를 등록하시겠어요?");
        }

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


        edit1.addTextChangedListener(textWatcherInput1);
        edit2.addTextChangedListener(textWatcherInput2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                    if(choose==1) {
                        next_intent = new Intent(OwnerRegisterCoupon2.this,OwnerRegisterCoupon3.class);
                        couponVO.setQualification(edit1.getText().toString());
                        couponVO.setContent(edit2.getText().toString());
                        next_intent.putExtra("CouponVO", couponVO);
                        startActivityForResult(next_intent,2);
                    }
                    else{
                        next_intent = new Intent(OwnerRegisterCoupon2.this,OwnerRegisterCoupon4.class);
                        saleVO.setQualification(edit1.getText().toString());
                        saleVO.setContent(edit2.getText().toString());
                        next_intent.putExtra("SaleVO", saleVO);
                        next_intent.putExtra("choose",2);
                        startActivityForResult(next_intent,3);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private boolean isValidPassword(String password) {
        boolean err = false;
        String regex = "^[a-zA-Z0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
    TextWatcher textWatcherInput1 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon2.this,R.drawable.yellow_btn_selector));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon2.this, R.drawable.yellow_btn_selector));
                }
                next.setTextColor(Color.BLACK);
            }
            else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon2.this,R.drawable.yellow_btn_selector));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon2.this, R.drawable.yellow_btn_selector));
                }
                next.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.i("afterTextChanged", s.toString());
        }
    };
    TextWatcher textWatcherInput2 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon2.this,R.drawable.yellow_btn_selector));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon2.this, R.drawable.yellow_btn_selector));
                }
                next.setTextColor(Color.BLACK);
            }
            else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon2.this,R.drawable.round_button_gray));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon2.this, R.drawable.round_button_gray));
                }
                next.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.i("afterTextChanged", s.toString());
        }
    };
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2){ // 쿠폰 발급 팝업
            if(resultCode==RESULT_OK){ // client
                setResult(RESULT_OK);
                finish();
            }
        }
        else if(requestCode==3){ // 할인 발급 팝업
            if(resultCode==RESULT_OK){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

}
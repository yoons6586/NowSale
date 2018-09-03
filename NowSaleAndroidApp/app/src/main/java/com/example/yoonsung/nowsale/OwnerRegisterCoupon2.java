package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OwnerRegisterCoupon2 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private EditText edit1,edit2;
    private TextView txt1,txt2,txtTitle,txtContent;
    private Button next;
    private ImageView back;
    private OwnerCouponData ownerCouponData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register_coupon2);
        ownerCouponData = new OwnerCouponData();
        get_intent = getIntent();

        ownerCouponData = (OwnerCouponData) get_intent.getSerializableExtra("OwnerCouponData");

        txtTitle = findViewById(R.id.txt_title);
        txtContent = findViewById(R.id.txt_content);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);

        if(ownerCouponData.getChoose().equals("sale")){
            txtTitle.setText("할인행사 등록");
            txtContent.setText("어떤 내용의 할인행사를 등록하시겠어요?");
        }


        if(get_intent.getStringExtra("what").equals("service")){
            edit2.setHint("무엇이");
            txt2.setText("제공");
        }
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
                    if(ownerCouponData.getChoose().equals("coupon")) {
                        next_intent = new Intent(OwnerRegisterCoupon2.this,OwnerRegisterCoupon3.class);
                        ownerCouponData.setRange(edit1.getText().toString());
                        ownerCouponData.setGift(edit2.getText().toString());
                        next_intent.putExtra("OwnerCouponData", ownerCouponData);
                        startActivity(next_intent);
                    }
                    else{
                        next_intent = new Intent(OwnerRegisterCoupon2.this,OwnerRegisterCoupon4.class);
                        ownerCouponData.setRange(edit1.getText().toString());
                        ownerCouponData.setGift(edit2.getText().toString());
                        next_intent.putExtra("OwnerCouponData", ownerCouponData);
                        startActivity(next_intent);
                    }
                }
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
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon2.this,R.drawable.round_button_yellow));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon2.this, R.drawable.round_button_yellow));
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
    TextWatcher textWatcherInput2 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon2.this,R.drawable.round_button_yellow));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon2.this, R.drawable.round_button_yellow));
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

}
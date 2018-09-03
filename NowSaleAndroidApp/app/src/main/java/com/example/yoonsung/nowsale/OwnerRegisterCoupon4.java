package com.example.yoonsung.nowsale;

import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class OwnerRegisterCoupon4 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private EditText edit1,edit2;
    private TextView txt1,txt2,txtTitle,txtStep,txtContent;
    private Button next;
    private ImageView back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private OwnerCouponData ownerCouponData;
    private CheckBox check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register_coupon4);
        ownerCouponData = new OwnerCouponData();
        get_intent = getIntent();
        ownerCouponData = (OwnerCouponData) get_intent.getSerializableExtra("OwnerCouponData");

        check  = findViewById(R.id.check);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        txtStep = findViewById(R.id.txt_step);
        txtTitle = findViewById(R.id.txt_title);
        txtContent = findViewById(R.id.txt_content);

        if(ownerCouponData.getChoose().equals("sale")){
            txtTitle.setText("할인행사 등록");
            txtStep.setText("STEP 3");
            txtContent.setText("행사기간을 지정하시겠어요?");
            check.setVisibility(View.VISIBLE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_yellow));
            } else {
                next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_yellow));
            }
        }


        edit1.setClickable(false);
        edit1.setFocusable(false);
        edit2.setClickable(false);
        edit2.setFocusable(false);

        edit1.addTextChangedListener(textWatcherInput);
        edit2.addTextChangedListener(textWatcherInput);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.getVisibility()==View.GONE || check.isChecked()) {
                    new DatePickerDialog(OwnerRegisterCoupon4.this, mDateSetListener1, mYear,
                            mMonth, mDay).show();
                }
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.getVisibility()==View.GONE || check.isChecked()) {
                    new DatePickerDialog(OwnerRegisterCoupon4.this, mDateSetListener2, mYear,
                            mMonth, mDay).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edit1.getText().toString().equals("")) {
                    ownerCouponData.setStart(edit1.getText().toString());
                    ownerCouponData.setExpire(edit2.getText().toString());
                }
                Log.e("ownerCouponData",ownerCouponData.getCouponCnt());
                Log.e("ownerCouponData",ownerCouponData.getStart());
                Log.e("ownerCouponData",ownerCouponData.getExpire());
                Log.e("ownerCouponData",ownerCouponData.getGift());
                Log.e("ownerCouponData",ownerCouponData.getRange());
                if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                    /*
                    서버로 쿠폰 발급 보내기~
                     */
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_gray));
                    } else {
                        next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_gray));
                    }
                    next.setTextColor(Color.GRAY);

                    edit1.setText("");
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_yellow));
                    } else {
                        next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_yellow));
                    }
                    next.setTextColor(Color.BLACK);

                    edit1.setText("");
                    edit1.setHint("");
                }
            }
        });
    }
    TextWatcher textWatcherInput = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_yellow));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_yellow));
                }
                next.setTextColor(Color.BLACK);
            }
            else if(check.getVisibility()==View.GONE || check.isChecked()){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_gray));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_gray));
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
    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //텍스트뷰의 값을 업데이트함
            edit1.setText(String.format(String.format("%d-%d-%d", mYear,mMonth + 1, mDay)));
        }
    };
    DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //텍스트뷰의 값을 업데이트함
            edit2.setText(String.format(String.format("%d-%d-%d", mYear,mMonth + 1, mDay)));
        }
    };
}
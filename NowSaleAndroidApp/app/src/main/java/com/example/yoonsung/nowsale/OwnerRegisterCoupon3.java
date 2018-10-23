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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yoonsung.nowsale.VO.CouponVO;

public class OwnerRegisterCoupon3 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private EditText edit1;
    private Button next;
    private ImageView back;
    private CheckBox check;
    private InputMethodManager imm;
    private CouponVO couponVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register_coupon3);
//        couponVO = new CouponVO();
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        get_intent = getIntent();
        couponVO = (CouponVO) get_intent.getSerializableExtra("CouponVO");

        next_intent = new Intent(this,OwnerRegisterCoupon4.class);

        edit1 = findViewById(R.id.edit1);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        check = findViewById(R.id.check);

        edit1.addTextChangedListener(textWatcherInput1);
        edit1.setClickable(false);
        edit1.setFocusable(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((check.isChecked() && (!edit1.getText().toString().equals(""))) || (!check.isChecked())){
//                    ownerCouponData.setCouponCnt(edit1.getText().toString());
                    couponVO.setStart_count(Integer.parseInt(edit1.getText().toString()));
                    couponVO.setRemain_count(Integer.parseInt(edit1.getText().toString()));
//                    next_intent.putExtra("OwnerCouponData",ownerCouponData);
                    next_intent.putExtra("CouponVO",couponVO);
                    next_intent.putExtra("choose",1);
                    startActivity(next_intent);
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon3.this,R.drawable.round_button_gray));
                    } else {
                        next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon3.this, R.drawable.round_button_gray));
                    }
                    next.setTextColor(Color.GRAY);

                    edit1.setText("");
                    edit1.setHint("숫자로 입력");
                    edit1.setFocusableInTouchMode(true);
                    edit1.setClickable(true);
                    edit1.setFocusable(true);
                    showKeyboard(edit1);
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon3.this,R.drawable.round_button_yellow));
                    } else {
                        next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon3.this, R.drawable.round_button_yellow));
                    }
                    next.setTextColor(Color.BLACK);

                    edit1.setText("");
                    edit1.setHint("");
                    edit1.setClickable(false);
                    edit1.setFocusable(false);
                }
            }
        });

    }
    TextWatcher textWatcherInput1 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(!edit1.getText().toString().equals("")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon3.this,R.drawable.round_button_yellow));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon3.this, R.drawable.round_button_yellow));
                }
                next.setTextColor(Color.BLACK);
            }
            else if(check.isChecked()){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon3.this,R.drawable.round_button_gray));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon3.this, R.drawable.round_button_gray));
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
    private void showKeyboard(EditText editText) {
        imm.showSoftInput(editText, 0);
    }

    private void hideKeyboard(EditText editText) {
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
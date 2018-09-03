package com.example.yoonsung.nowsale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class SignUpActivity3 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up3);


    }

}
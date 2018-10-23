package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.yoonsung.nowsale.VO.ClientVO;

public class SignUpActivity3 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private ClientVO clientVO;
    private Intent get_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up3);

        get_intent=getIntent();
        clientVO = (ClientVO) get_intent.getSerializableExtra("ClientVO");


    }

}
package com.example.yoonsung.nowsale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LoginCancelPopupActivity extends Activity {
    private Intent getIntent,loginIntent;
    private TextView loginBtn,cancelBtn,contentText;
    private Button okBtn;
    private int isOwner,down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_login_need);

        loginBtn = findViewById(R.id.loginBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        contentText = findViewById(R.id.contentText);

        getIntent = getIntent();
        isOwner = getIntent.getIntExtra("isOwner",0);
        down = getIntent.getIntExtra("down",-1);

        if(isOwner == 1 || down ==1){ // 점주입니다.
            contentText.setText("고객으로 로그인 해주세요!");
            loginBtn.setVisibility(View.INVISIBLE);
        }
        if(down == 0){
            loginBtn.setVisibility(View.INVISIBLE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }






}

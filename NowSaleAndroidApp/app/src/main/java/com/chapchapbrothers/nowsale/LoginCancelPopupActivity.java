package com.chapchapbrothers.nowsale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginCancelPopupActivity extends Activity {
    private Intent getIntent,loginIntent;
    private TextView contentText,okText,cancelText,pleaseLoginText;
    private LinearLayout okBtn,cancelBtn;
    private int isOwner,down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_login_need);

        okBtn = findViewById(R.id.okBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        contentText = findViewById(R.id.contentText);
        okText = findViewById(R.id.okText);
        cancelText = findViewById(R.id.cancelText);
        pleaseLoginText = findViewById(R.id.pleaseLoginText);

        getIntent = getIntent();
        isOwner = getIntent.getIntExtra("isOwner",0);
        down = getIntent.getIntExtra("down",-1);

        if(isOwner == 1 || down ==1){ // 점주입니다.
            contentText.setText("고객으로 로그인 해주세요!");
            okBtn.setVisibility(View.GONE);
            pleaseLoginText.setVisibility(View.GONE);
            contentText.setTextColor(getResources().getColor(R.color.baseColor));
        }
        if(down == 0){
            okBtn.setVisibility(View.GONE);
        }
        else if(down==2){ //회원 탈퇴
            okText.setText("탈퇴하기");
            contentText.setText("정말 탈퇴하시겠습니까?");
            pleaseLoginText.setVisibility(View.GONE);
            contentText.setTextColor(getResources().getColor(R.color.baseColor));
        }
        else if(down==3){ // 쿠폰등록은 3개까지만
            okBtn.setVisibility(View.GONE);
            cancelText.setText("확인");
            contentText.setText("쿠폰 등록은 3개까지 가능합니다");
            pleaseLoginText.setVisibility(View.GONE);
            contentText.setTextColor(getResources().getColor(R.color.baseColor));
        }
        else if(down==4){
            okBtn.setVisibility(View.GONE);
            cancelText.setText("확인");
            contentText.setText("할인 등록은 3개까지 가능합니다");
            pleaseLoginText.setVisibility(View.GONE);
            contentText.setTextColor(getResources().getColor(R.color.baseColor));
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
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

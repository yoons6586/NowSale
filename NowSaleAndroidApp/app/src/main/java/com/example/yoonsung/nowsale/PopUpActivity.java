package com.example.yoonsung.nowsale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class PopUpActivity extends Activity {
    private Intent intent;
    private int key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        intent = getIntent();
    }
    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        /*Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);
        */
        //액티비티(팝업) 닫기
        this.finish();
    }

    public void mOnDelete(View v){ // 여기서 레알 삭제 시켜야됨
        key = intent.getIntExtra("couponKey",0);
        Log.e("coupon",""+key);
        Intent intent = new Intent();
        intent.putExtra("KEY", ""+key);
        setResult(RESULT_OK, intent);

        finish();
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

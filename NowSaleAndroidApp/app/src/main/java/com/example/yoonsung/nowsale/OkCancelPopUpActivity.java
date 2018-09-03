package com.example.yoonsung.nowsale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OkCancelPopUpActivity extends Activity {
    private Intent intent;
    private TextView txtText;
    private Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ok_cancel_popup);
        txtText=findViewById(R.id.okCancelText);
        okBtn = findViewById(R.id.deleteBtn);
        intent = getIntent();
        txtText.setText(intent.getStringExtra("title"));
        okBtn.setText(intent.getStringExtra("change"));

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
        Intent intent = new Intent();

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

package com.example.yoonsung.nowsale;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class Loading1Activity extends Activity {
    //네트워크
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        startLoading();

        //네트워크





    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               finish();
            }
        }, 2000);
    }


}
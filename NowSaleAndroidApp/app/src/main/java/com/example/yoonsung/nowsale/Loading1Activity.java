package com.example.yoonsung.nowsale;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class Loading1Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // 3G나 LTE등 데이터 네트워크에 연결된 상태
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // 와이파이에 연결된 상태

        if (wifi.isConnected()) { // 와이파이에 연결된 경우
            finish();
        } else if (mobile.isConnected()) { // 데이터 네트워크에 연결된 경우
            finish();
        } else { // 인터넷에 연결되지 않은 경우

        }
//        startLoading();
        finish();
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
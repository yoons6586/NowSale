package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class OwnerDangolActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private LinearLayout backBtn;
    private ImageView harinDangolImg;
    private Intent getIntent;
    private int dangolCnt;
    private TextView dangolCntText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_dangol);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        backBtn=findViewById(R.id.back);
        harinDangolImg = findViewById(R.id.harindangol);
        dangolCntText=findViewById(R.id.dangolCnt);

        getIntent = getIntent();
        dangolCnt = getIntent.getIntExtra("dangolCnt",-1);

        dangolCntText.setText(dangolCnt+"명");

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        LinearLayout.LayoutParams harin_params = (LinearLayout.LayoutParams) harinDangolImg.getLayoutParams();

        harin_params.width = (int) (metrics.widthPixels/2.3);
        harin_params.height = harin_params.width;

        harinDangolImg.setLayoutParams(harin_params);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10){
            if(resultCode==RESULT_OK){


            }
        }
    }
}
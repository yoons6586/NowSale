package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoonsung.nowsale.VO.OwnerVO;
import com.example.yoonsung.nowsale.kakao.PlusFriendService;
import com.kakao.util.exception.KakaoException;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class OwnerChangeInfo extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private LinearLayout backBtn;
    private TextView ownerChangeInfoBtn;
    private Intent resultIntent;
    private RelativeLayout kakaoLinkBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_change_info);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        backBtn=findViewById(R.id.back);
        kakaoLinkBtn = findViewById(R.id.kakaoLinkBtn);
        ownerChangeInfoBtn = findViewById(R.id.ownerChangeInfoBtn);

        resultIntent = new Intent();



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        kakaoLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlusFriendService.getInstance().addFriend(OwnerChangeInfo.this, "_cxgxjxgC");
                } catch (KakaoException e) {
                    // 에러 처리 (앱키 미설정 등등)
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


        ownerChangeInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerChangeInfo.this,ClientMyInfoActivity.class);
                intent.putExtra("clientOwner",2);
                startActivityForResult(intent,10);
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
                int change_logout = data.getIntExtra("change_logout_deleteClient",-1);
                if(change_logout==1) {
                    Intent intentDialog = new Intent(this,Dialog.class);
                    resultIntent.putExtra("change_logout_deleteClient", 1);
                    setResult(RESULT_OK, resultIntent);
                    intentDialog.putExtra("message", "수정되었습니다.");
                    startActivity(intentDialog);
                } else if (change_logout==2) {
                    resultIntent.putExtra("change_logout_deleteClient", 2);
                    setResult(RESULT_OK,resultIntent);
                    Config.ownerVO = new OwnerVO();
                    finish();
                }

            }
        }
    }
}
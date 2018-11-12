package activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoonsung.nowsale.Config;
import com.example.yoonsung.nowsale.R;
import com.example.yoonsung.nowsale.VO.OwnerVO;
import com.example.yoonsung.nowsale.kakao.PlusFriendService;
import com.kakao.util.exception.KakaoException;

public class OwnerChangeInfo extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private LinearLayout backBtn;
    private TextView logoutBtn;
    private Intent resultIntent;
    private FrameLayout kakaoLinkBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_change_info);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

        backBtn=findViewById(R.id.back);
        logoutBtn = findViewById(R.id.logoutBtn);
        kakaoLinkBtn = findViewById(R.id.kakaoLinkBtn);
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

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultIntent.putExtra("change_logout_deleteClient",2);
                setResult(RESULT_OK,resultIntent);
                Config.ownerVO = new OwnerVO();
                finish();
            }
        });

    }

}
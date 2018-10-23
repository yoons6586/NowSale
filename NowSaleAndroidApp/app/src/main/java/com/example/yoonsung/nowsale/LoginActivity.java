package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.LoginVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;
import com.example.yoonsung.nowsale.http.ClientService;
import com.example.yoonsung.nowsale.http.OwnerService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent owner_intent, result_intent;
    private EditText id_edit;
    private EditText pw_edit;
    private String user_id, user_pw;
    private TextView loginBtn;
    private TextView signupBtn;
    private ClientService clientService;
    private OwnerService ownerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));
        id_edit = (EditText) findViewById(R.id.id_text);
        pw_edit = (EditText) findViewById(R.id.pw_text);


        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signUpBtn);
//        client_intent = new Intent(this,)


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = id_edit.getText().toString();
                user_pw = pw_edit.getText().toString();

                clientService = Config.retrofit.create(ClientService.class);
                Call<List<ClientVO>> request = clientService.isLogin(new LoginVO(user_id, user_pw));
                request.enqueue(new Callback<List<ClientVO>>() {
                    @Override
                    public void onResponse(Call<List<ClientVO>> call, Response<List<ClientVO>> response) {
//                        int count = response.body();
                        List<ClientVO> list = response.body();
                        if (response.code()==200) {
                            Config.clientVO = list.get(0);
                            Config.who_key = "C";
                            result_intent = new Intent();
                            setResult(2, result_intent);
                            finish();
                        } else {
                            ownerService = Config.retrofit.create(OwnerService.class);
                            Call<List<OwnerVO>> request = ownerService.isLogin(new LoginVO(user_id, user_pw));
                            request.enqueue(new Callback<List<OwnerVO>>() {
                                @Override
                                public void onResponse(Call<List<OwnerVO>> call, Response<List<OwnerVO>> response) {
                                    List<OwnerVO> list = response.body();
                                    if (response.code()==200) {
                                        Config.ownerVO = list.get(0);
                                        result_intent = new Intent();
                                        setResult(3, result_intent);
                                        Config.who_key = "O";
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "없는 id 또는 pw입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<OwnerVO>> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClientVO>> call, Throwable t) {

                    }
                });

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity1.class));
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
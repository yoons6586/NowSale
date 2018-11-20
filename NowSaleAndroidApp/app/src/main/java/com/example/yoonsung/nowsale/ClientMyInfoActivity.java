package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;
import com.example.yoonsung.nowsale.http.ClientService;
import com.example.yoonsung.nowsale.http.OwnerService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientMyInfoActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private CheckBox boxPush1,boxPush2,boxEmail1,boxEmail2,boxSMS1,boxSMS2;
    private ImageView backBtn,harin_coupon;
    private TextView textEmail,logoutBtn,deleteClientBtn;
    private EditText editNickname,editPW,editPWCheck;
    private Intent intentDialog,resultIntent,getIntent;
    private LinearLayout start_layout, changeBtn;
    private RelativeLayout layout;
    private int deleteClient = 3;
    private int clientOwner;
    private final int isClient=1,isOwner=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_myinfo);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));


        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }


        intentDialog = new Intent(this,Dialog.class);
        resultIntent = new Intent();
        getIntent = getIntent();

        clientOwner = getIntent.getIntExtra("clientOwner",-1);

        boxPush1 = findViewById(R.id.check_push1);
        boxPush2 = findViewById(R.id.check_push2);
        boxEmail1 = findViewById(R.id.check_mail1);
        boxEmail2 = findViewById(R.id.check_mail2);
        boxSMS1 = findViewById(R.id.check_sms1);
        boxSMS2 = findViewById(R.id.check_sms2);

        backBtn = findViewById(R.id.back);
        changeBtn = findViewById(R.id.changeBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        deleteClientBtn = findViewById(R.id.deleteClientBtn);

        textEmail = findViewById(R.id.txt_email);

        editNickname = findViewById(R.id.edit_nickname);
        editPW = findViewById(R.id.edit_pw);
        editPWCheck=findViewById(R.id.edit_pw_check);

        layout = findViewById(R.id.layout);
        start_layout = findViewById(R.id.start_layout);
        harin_coupon = findViewById(R.id.harin_coupon);



        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        FrameLayout.LayoutParams harin_params = (FrameLayout.LayoutParams) harin_coupon.getLayoutParams();
        FrameLayout.LayoutParams linear_params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        RelativeLayout.LayoutParams edit_params = (RelativeLayout.LayoutParams) start_layout.getLayoutParams();

        harin_params.width = (int) (metrics.widthPixels/3);
        harin_params.height = harin_params.width;
        linear_params.topMargin = harin_params.height/2;
        edit_params.topMargin = harin_params.height/2+50;

        harin_coupon.setLayoutParams(harin_params);
        layout.setLayoutParams(linear_params);
        start_layout.setLayoutParams(edit_params);


        switch (clientOwner){
            case isClient :

                textEmail.setText(Config.clientVO.getId());
                editNickname.setText(Config.clientVO.getNickName());
                editPW.setText(Config.clientVO.getPw());
                editPWCheck.setText(Config.clientVO.getPw());

                if(Config.clientVO.getAlarm_push().equals("T")) {
                    boxPush1.setChecked(true);
                    boxPush2.setChecked(false);
                }
                else {
                    boxPush1.setChecked(false);
                    boxPush2.setChecked(true);
                }
                if(Config.clientVO.getAlarm_mail().equals("T")) {
                    boxEmail1.setChecked(true);
                    boxEmail2.setChecked(false);
                }
                else {
                    boxEmail1.setChecked(false);
                    boxEmail2.setChecked(true);
                }
                if(Config.clientVO.getAlarm_SMS().equals("T")) {
                    boxSMS1.setChecked(true);
                    boxSMS2.setChecked(false);
                }
                else {
                    boxSMS1.setChecked(false);
                    boxSMS2.setChecked(true);
                }
                break;
            case isOwner :
                deleteClientBtn.setVisibility(View.GONE);
                textEmail.setText(Config.ownerVO.getId());
                editNickname.setText(Config.ownerVO.getNickName());
                editPW.setText(Config.ownerVO.getPw());
                editPWCheck.setText(Config.ownerVO.getPw());

                if(Config.ownerVO.getAlarm_push().equals("T")) {
                    boxPush1.setChecked(true);
                    boxPush2.setChecked(false);
                }
                else {
                    boxPush1.setChecked(false);
                    boxPush2.setChecked(true);
                }
                if(Config.ownerVO.getAlarm_mail().equals("T")) {
                    boxEmail1.setChecked(true);
                    boxEmail2.setChecked(false);
                }
                else {
                    boxEmail1.setChecked(false);
                    boxEmail2.setChecked(true);
                }
                if(Config.ownerVO.getAlarm_SMS().equals("T")) {
                    boxSMS1.setChecked(true);
                    boxSMS2.setChecked(false);
                }
                else {
                    boxSMS1.setChecked(false);
                    boxSMS2.setChecked(true);
                }
                break;
        }


        boxPush1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxPush1.setChecked(true);
                boxPush2.setChecked(false);
            }
        });
        boxPush2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxPush1.setChecked(false);
                boxPush2.setChecked(true);
            }
        });
        boxEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxEmail1.setChecked(true);
                boxEmail2.setChecked(false);
            }
        });
        boxEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxEmail1.setChecked(false);
                boxEmail2.setChecked(true);
            }
        });
        boxSMS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxSMS1.setChecked(true);
                boxSMS2.setChecked(false);
            }
        });
        boxSMS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxSMS1.setChecked(false);
                boxSMS2.setChecked(true);
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check=true;
                if(!isValidNickname(editNickname.getText().toString())){
                    check=false;
                    editNickname.setText("");
                    editNickname.setHint("닉네임을 확인해주세요");
                    editNickname.setHintTextColor(Color.RED);
                }
                if(!isValidPassword(editPW.getText().toString())){
                    check=false;
                    editPW.setText("");
                    editPW.setHint("비밀번호를 확인해주세요");
                    editPW.setHintTextColor(Color.RED);
                }
                else if(!editPW.getText().toString().equals(editPWCheck.getText().toString())){
                    check=false;
                    editPWCheck.setText("");
                    editPWCheck.setHint("비밀번호확인이 틀렸습니다");
                    editPWCheck.setHintTextColor(Color.RED);
                } else if(editPW.getText().toString().length() < 8){
                    check=false;
                    editPW.setText("");
                    editPWCheck.setText("");
                    editPW.setHint("비밀번호는 8자 이상입니다.");
                    editPW.setHintTextColor(Color.RED);
                }

                if(check){
                    switch (clientOwner){
                        case isClient :
                            Config.clientVO.setPw(editPW.getText().toString());
                            Config.clientVO.setNickName(editNickname.getText().toString());
                            if(boxPush1.isChecked())
                                Config.clientVO.setAlarm_push("T");
                            else
                                Config.clientVO.setAlarm_push("F");
                            if(boxEmail1.isChecked())
                                Config.clientVO.setAlarm_mail("T");
                            else
                                Config.clientVO.setAlarm_mail("F");
                            if(boxSMS1.isChecked())
                                Config.clientVO.setAlarm_SMS("T");
                            else
                                Config.clientVO.setAlarm_SMS("F");

//                    new ClientMyInfoActivity.SendPostJSON().execute(Config.url+"/changeClientInfo?user_key="+Config.clientInfoData.getUser_key());
                            ClientService clientService = Config.retrofit.create(ClientService.class);
                            Call<Void> request = clientService.updateClientInfo(Config.clientVO.getClient_key(),Config.clientVO);
                            request.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code()== HttpStatus.SC_OK) {

                                        Log.e("ClientMyInfoActivity", "client수정완료");
                                        resultIntent.putExtra("change_logout_deleteClient",1);
                                        setResult(RESULT_OK,resultIntent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("ClientMyInfoActivity", "client수정실패");
                                }
                            });


                            break;

                        case isOwner :
                            Config.ownerVO.setPw(editPW.getText().toString());
                            Config.ownerVO.setNickName(editNickname.getText().toString());
                            if(boxPush1.isChecked())
                                Config.ownerVO.setAlarm_push("T");
                            else
                                Config.ownerVO.setAlarm_push("F");
                            if(boxEmail1.isChecked())
                                Config.ownerVO.setAlarm_mail("T");
                            else
                                Config.ownerVO.setAlarm_mail("F");
                            if(boxSMS1.isChecked())
                                Config.ownerVO.setAlarm_SMS("T");
                            else
                                Config.ownerVO.setAlarm_SMS("F");

//                    new ClientMyInfoActivity.SendPostJSON().execute(Config.url+"/changeClientInfo?user_key="+Config.clientInfoData.getUser_key());
                            OwnerService ownerService = Config.retrofit.create(OwnerService.class);
                            Call<Void> ownerRequest = ownerService.updateOwnerInfo(Config.ownerVO.getOwner_key(),Config.ownerVO);
                            ownerRequest.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code()== HttpStatus.SC_OK) {

                                        resultIntent.putExtra("change_logout_deleteClient",1);
                                        setResult(RESULT_OK,resultIntent);
                                        Log.e("ClientMyInfoActivity", "수정완료");

                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("ClientMyInfoActivity", "수정실패");
                                }
                            });

                            break;
                    }



                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intentDialog.putExtra("message","로그아웃 되었습니다");
//                startActivity(intentDialog);
                resultIntent.putExtra("change_logout_deleteClient",2);
                setResult(RESULT_OK,resultIntent);
                Config.clientVO = new ClientVO();
                Config.ownerVO = new OwnerVO();
                finish();
            }
        });

        deleteClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginCancelPopupActivity.class);
                intent.putExtra("down",2);
                startActivityForResult(intent,deleteClient);
            }
        });

    }
    private boolean isValidPassword(String password) {
        boolean err = false;
        String regex = "^[a-zA-Z0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
    private boolean isValidNickname(String nickname) {
        boolean err = false;
        String regex = "^[ㄱ-ㅎ가-힣0-9a-zA-Z]*$";
//        String regex_english = "^[a-zA-Z0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(nickname);
        if(m.matches()) {
            err = true;
        }
        /*else{
            regex = "^[a-zA-Z0-9]*$";
            p = Pattern.compile(regex);
            m = p.matcher(nickname);
            if(m.matches())
                err=true;
        }*/
        return err;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==deleteClient){
            if(resultCode==RESULT_OK){
                resultIntent.putExtra("change_logout_deleteClient",3);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
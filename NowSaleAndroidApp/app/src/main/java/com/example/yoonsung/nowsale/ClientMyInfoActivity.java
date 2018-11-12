package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.http.ClientService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientMyInfoActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private CheckBox boxPush1,boxPush2,boxEmail1,boxEmail2,boxSMS1,boxSMS2;
    private ImageView backBtn;
    private TextView textEmail,changeBtn,logoutBtn,deleteClientBtn;
    private EditText editNickname,editPW,editPWCheck;
    private Intent intentDialog,resultIntent;
    private int deleteClient = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_myinfo);

        intentDialog = new Intent(this,Dialog.class);
        resultIntent = new Intent();

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
                }
                if(check){
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
                    Call<ClientVO> request = clientService.updateClientInfo(Config.clientVO.getClient_key(),Config.clientVO);
                    request.enqueue(new Callback<ClientVO>() {
                        @Override
                        public void onResponse(Call<ClientVO> call, Response<ClientVO> response) {
                            if(response.code()== HttpStatus.SC_OK) {

                                Log.e("ClientMyInfoActivity", "수정완료");

                            }
                        }

                        @Override
                        public void onFailure(Call<ClientVO> call, Throwable t) {

                        }
                    });

                    resultIntent.putExtra("change_logout_deleteClient",1);
                    setResult(RESULT_OK,resultIntent);
                    finish();


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
}
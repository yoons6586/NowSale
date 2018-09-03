package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMyInfoActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private CheckBox boxPush1,boxPush2,boxEmail1,boxEmail2,boxSMS1,boxSMS2;
    private ImageView backBtn;
    private TextView textEmail,changeBtn;
    private EditText editNickname,editPW,editPWCheck;
    private Intent intentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_myinfo);

        intentDialog = new Intent(this,Dialog.class);

        boxPush1 = findViewById(R.id.check_push1);
        boxPush2 = findViewById(R.id.check_push2);
        boxEmail1 = findViewById(R.id.check_mail1);
        boxEmail2 = findViewById(R.id.check_mail2);
        boxSMS1 = findViewById(R.id.check_sms1);
        boxSMS2 = findViewById(R.id.check_sms2);

        backBtn = findViewById(R.id.back);
        changeBtn = findViewById(R.id.changeBtn);

        textEmail = findViewById(R.id.txt_email);

        editNickname = findViewById(R.id.edit_nickname);
        editPW = findViewById(R.id.edit_pw);
        editPWCheck=findViewById(R.id.edit_pw_check);

        textEmail.setText(Config.clientInfoData.getId());
        editNickname.setText(Config.clientInfoData.getNickName());
        editPW.setText(Config.clientInfoData.getPassword());
        editPWCheck.setText(Config.clientInfoData.getPassword());

        if(Config.clientInfoData.isAlarm_push()) {
            boxPush1.setChecked(true);
            boxPush2.setChecked(false);
        }
        else {
            boxPush1.setChecked(false);
            boxPush2.setChecked(true);
        }
        if(Config.clientInfoData.isAlarm_mail()) {
            boxEmail1.setChecked(true);
            boxEmail2.setChecked(false);
        }
        else {
            boxEmail1.setChecked(false);
            boxEmail2.setChecked(true);
        }
        if(Config.clientInfoData.isAlarm_SMS()) {
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
                    Config.clientInfoData.setPassword(editPW.getText().toString());
                    Config.clientInfoData.setNickName(editNickname.getText().toString());
                    if(boxPush1.isChecked())
                        Config.clientInfoData.setAlarm_push(true);
                    else
                        Config.clientInfoData.setAlarm_push(false);
                    if(boxEmail1.isChecked())
                        Config.clientInfoData.setAlarm_mail(true);
                    else
                        Config.clientInfoData.setAlarm_mail(false);
                    if(boxSMS1.isChecked())
                        Config.clientInfoData.setAlarm_SMS(true);
                    else
                        Config.clientInfoData.setAlarm_SMS(false);
                    intentDialog.putExtra("message","변경되었습니다");
                    startActivity(intentDialog);

                    new ClientMyInfoActivity.SendPostJSON().execute(Config.url+"/changeClientInfo?user_key="+Config.clientInfoData.getUser_key());
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
        String regex = "^[ㄱ-ㅎ가-힣0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(nickname);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public class SendPostJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                String bool;

                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
                jsonObject.accumulate("nickName", Config.clientInfoData.getNickName());
                jsonObject.accumulate("pw", Config.clientInfoData.getPassword());
                if(Config.clientInfoData.isAlarm_push())
                    jsonObject.accumulate("alarm_push", "T");
                else
                    jsonObject.accumulate("alarm_push", "F");
                if(Config.clientInfoData.isAlarm_mail())
                    jsonObject.accumulate("alarm_mail", "T");
                else
                    jsonObject.accumulate("alarm_mail", "F");
                if(Config.clientInfoData.isAlarm_SMS())
                    jsonObject.accumulate("alarm_SMS", "T");
                else
                    jsonObject.accumulate("alarm_SMS", "F");




//                jsonObject.accumulate("coupon1_key", coupon_key.get(0));
//                jsonObject.accumulate("coupon2_key", coupon_key.get(1));
//                jsonObject.accumulate("timeattack_key", coupon_key.get(2));


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("PUT");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.yoonsung.nowsale.Config.url;

public class LoginActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent owner_intent,result_intent;
    private EditText id_edit;
    private EditText pw_edit;
    private String user_id,user_pw;
    private Button loginBtn;
    private TextView signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_edit = (EditText)findViewById(R.id.id_text);
        pw_edit = (EditText)findViewById(R.id.pw_text);


        loginBtn = findViewById(R.id.loginBtn);
        signupBtn=findViewById(R.id.signUpBtn);
//        client_intent = new Intent(this,)
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id = id_edit.getText().toString();
                user_pw = pw_edit.getText().toString();

                new LoginActivity.JSONTask().execute(url+"/show_id_pw");



            }
        });
        Config.clientInfoData.setAlarm_mail(false);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity1.class));
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        //AsyncTask이용하는 이유는 UI변경을 위해서이다. 안드로이드는 UI를 담당하는 메인 스레드가 존재하는데 우리가 만든 스레드에서는 화면을 바꾸는 일이 불가능하기 때
        //이러한 이유로 안드로이드는 Background 작업을 할 수 있도록 AsyncTask를 지원한다.

        @Override
        protected String doInBackground(String... urls) {
            try{
                JSONObject jsonObject = new JSONObject();

//                jsonObject.accumulate("user_key",1);

                HttpURLConnection con=null;
                BufferedReader reader=null;
                try{
//                    Log.e("윤성",""+urls[0]);
                    URL url = new URL(urls[0]);

                    con = (HttpURLConnection)url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line="";
                    while((line=reader.readLine())!=null){
//                        Log.e("윤성",""+line); // json을 스트링으로 뽑는 수밖에 없을 듯
                        buffer.append(line);
                    }
                    return buffer.toString();
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    if(con!=null)
                        con.disconnect();
                    try{
                        if(reader!=null)
                            reader.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try{
                JSONArray jarray = new JSONArray(result);
                Log.e("윤성","result : "+jarray);
                for(int i=0;i<jarray.length();i++){ // json데이터를 뽑기 위함
                    JSONObject jObject = jarray.getJSONObject(i);
                    String id = jObject.getString("id");
                    String pw = jObject.getString("pw");

//                    Log.e("윤성","id : "+id+", pw : "+pw+"whi_key : "+Config.who_key);
                    // 여기서 점주인지 아닌지 확인해야됨
                    if(id.equals(user_id) && pw.equals(user_pw)){
                        Config.clientInfoData.setWho_key(jObject.getString("who_key"));
                        Config.clientInfoData.setUser_key(jObject.getInt("user_key"));

                        if(Config.clientInfoData.getWho_key().charAt(0) == 'O') { // 점주

                        }
                        else{ // 고객
                            Config.clientInfoData.setId(id);
                            Config.clientInfoData.setPassword(pw);
                            new LoginActivity.ClientJSONTask().execute(url+"/clientInfo?user_key="+Config.clientInfoData.getUser_key());

                        }
                        break;

                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    public class ClientJSONTask extends AsyncTask<String, String, String> {
        //AsyncTask이용하는 이유는 UI변경을 위해서이다. 안드로이드는 UI를 담당하는 메인 스레드가 존재하는데 우리가 만든 스레드에서는 화면을 바꾸는 일이 불가능하기 때
        //이러한 이유로 안드로이드는 Background 작업을 할 수 있도록 AsyncTask를 지원한다.

        @Override
        protected String doInBackground(String... urls) {
            try{
                JSONObject jsonObject = new JSONObject();

//                jsonObject.accumulate("user_key",1);

                HttpURLConnection con=null;
                BufferedReader reader=null;
                try{
//                    Log.e("윤성",""+urls[0]);
                    URL url = new URL(urls[0]);

                    con = (HttpURLConnection)url.openConnection();
                    con.connect();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line="";
                    while((line=reader.readLine())!=null){
//                        Log.e("윤성",""+line); // json을 스트링으로 뽑는 수밖에 없을 듯
                        buffer.append(line);
                    }
                    return buffer.toString();
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    if(con!=null)
                        con.disconnect();
                    try{
                        if(reader!=null)
                            reader.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try{
                JSONArray jarray = new JSONArray(result);
                Log.e("윤성","result : "+jarray);
                for(int i=0;i<jarray.length();i++){ // json데이터를 뽑기 위함
                    JSONObject jObject = jarray.getJSONObject(i);
                    Config.clientInfoData.setNickName(jObject.getString("nickName"));
                    Config.clientInfoData.setPhoneNum(jObject.getString("phone"));
                    if(jObject.getString("alarm_push").equals("F")){
                        Config.clientInfoData.setAlarm_push(false);
                    }
                    else{
                        Config.clientInfoData.setAlarm_push(true);
                    }
                    if(jObject.getString("alarm_mail").equals("F")){
                        Config.clientInfoData.setAlarm_mail(false);
                    }
                    else{
                        Config.clientInfoData.setAlarm_mail(true);
                    }
                    if(jObject.getString("alarm_SMS").equals("F")){
                        Config.clientInfoData.setAlarm_SMS(false);
                    }
                    else{
                        Config.clientInfoData.setAlarm_SMS(true);
                    }

//                    Config.nickName = jObject.getString("nickName");

//                    Log.e("Config",Config.nickName);
                }
                result_intent = new Intent();
                setResult(RESULT_OK,result_intent);
                finish();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}


//        intent.putExtra("ID",id_edit.getText().toString());
//        intent.putExtra("PW",pw_edit.getText().toString());

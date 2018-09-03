package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ClientSetUpActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함

    private EditText nameE,nickNameE,pwE,checkPwE;
    private TextView changeBtn,exitBtn;
    private Intent changeIntent,exitIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_setup);

        nameE=findViewById(R.id.setUpname);
        nickNameE = findViewById(R.id.setUpnickName);
        pwE = findViewById(R.id.setUppassword);
        checkPwE=findViewById(R.id.setUpcheckPassword);
        changeBtn=findViewById(R.id.setUpchangeBtn);
        exitBtn=findViewById(R.id.setUpexitBtn);

        nameE.setHint(Config.clientInfoData.getNickName());
//        nickNameE.setHint(Config.nickName);

        changeIntent = new Intent(this,OkCancelPopUpActivity.class);
        changeIntent.putExtra("title","정말 수정하시겠습니까?");
        changeIntent.putExtra("change","확인");

        exitIntent=new Intent(this,OkCancelPopUpActivity.class);
        exitIntent.putExtra("title","정말 회원탈퇴 하실껀가요ㅠㅠ");
        exitIntent.putExtra("change","떠나게따!");

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = pwE.getText().toString();
                if(pw.length()>=6 && pw.length()<=12){
                    if(pw.equals(checkPwE.getText().toString())) {
                        startActivityForResult(changeIntent,1);
                    }
                    else{
                        Intent pwCheckIntent = new Intent(ClientSetUpActivity.this,Dialog.class);
                        pwCheckIntent.putExtra("message","비밀번호를 확인해주세요");
                        startActivity(pwCheckIntent);
                    }
                }
                else{
                    Intent pwCheckIntent = new Intent(ClientSetUpActivity.this,Dialog.class);
                    pwCheckIntent.putExtra("message","비밀번호는 6~12자입니다");
                    startActivity(pwCheckIntent);
                }


            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(exitIntent,2);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1){ // 수정
            if(resultCode==RESULT_OK){
                //데이터 받기
                // 데이터 수정하기
                Log.e("하하","resultOK");
                new ClientSetUpActivity.SendPutJSON().execute(Config.url+"/changeClientInfo");
            }
        }
        else if(requestCode==2){ // 회원 탈퇴
            if(resultCode==RESULT_OK){
                new ClientSetUpActivity.SendDeleteJSON().execute(Config.url+"/deleteClientInfo");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
    public class SendDeleteJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.


                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
//                jsonObject.accumulate("user_key", Config.user_key);


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("DELETE");//PUT방식으로 보냄
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
                    Log.e("buffer",buffer.toString()); // 서버로부터 받은 값
                    return buffer.toString();

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

    public class SendPutJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.


                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
//                jsonObject.accumulate("user_key", Config.user_key);
                jsonObject.accumulate("name", nameE.getText().toString());
                jsonObject.accumulate("nickName", nickNameE.getText().toString());
                jsonObject.accumulate("password", pwE.getText().toString());
//                Config.name = nameE.getText().toString();
//                Config.nickName = nickNameE.getText().toString();


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

                    con.setRequestMethod("PUT");//PUT방식으로 보냄
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
                    Log.e("buffer",buffer.toString()); // 서버로부터 받은 값
                    return buffer.toString();

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
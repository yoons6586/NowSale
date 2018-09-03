package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
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

public class SignUpActivity extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private EditText name_edit,phone_edit,id_edit,pw_edit,pwCheck_edit;
    private TextView overlap_text,signupBtn;
    private String id,pw;
    private Boolean overlapBool=false,signupBool=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        name_edit = findViewById(R.id.signup_name);
        phone_edit = findViewById(R.id.signup_phone);
        id_edit=findViewById(R.id.signup_id);
        pw_edit = findViewById(R.id.signup_pw);
        pwCheck_edit=findViewById(R.id.signup_pwCheck);
        overlap_text=findViewById(R.id.signup_overlap);
        signupBtn=findViewById(R.id.signupBtn);

        overlap_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                post로 id 보내서 중복되는지 확인하기
                 */
                id = id_edit.getText().toString();
                if(id.length()<6) {
                    Intent idLenIntent = new Intent(SignUpActivity.this,Dialog.class);
                    idLenIntent.putExtra("message","아이디는 6~12자 입니다");
                    startActivity(idLenIntent);
                }
                else
                    new SignUpActivity.JSONTask().execute(Config.url+"/overlapID?id="+id);

            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(overlapBool){
                    if(id.equals(id_edit.getText().toString())){
                        pw = pw_edit.getText().toString();
                        if(pw.equals(pwCheck_edit.getText().toString())) {
                            if(name_edit.getText().toString().equals("")==false && phone_edit.getText().toString().equals("")==false) {
                                new SignUpActivity.SendPostJSON().execute(Config.url + "/signUpClient");
                                Intent pwCheckIntent = new Intent(SignUpActivity.this, Dialog.class);
                                pwCheckIntent.putExtra("message", "회원가입이 완료되었습니다. 로그인 해주세요");
                                startActivity(pwCheckIntent);
                                finish();
                            }
                            else{
                                Intent signUpCancel = new Intent(SignUpActivity.this,Dialog.class);
                                signUpCancel.putExtra("message","정보를 입력해주세요");
                                startActivity(signUpCancel);
                            }
                        }
                        else {
                            // 비밀번호랑 비밀번호 확인이랑 다름
                            Intent pwCheckIntent = new Intent(SignUpActivity.this,Dialog.class);
                            pwCheckIntent.putExtra("message","비밀번호를 확인해주세요");
                            startActivity(pwCheckIntent);
                        }
                    }
                    else{
                        /*
                        회원가입 불가능
                         */
                        Intent idOverlapIntent = new Intent(SignUpActivity.this,Dialog.class);
                        idOverlapIntent.putExtra("message","아이디를 중복확인 해주세요");
                        startActivity(idOverlapIntent);
                        overlapBool=false;
                    }
                }
                else{
                    Intent signUpCancel = new Intent(SignUpActivity.this,Dialog.class);
                    signUpCancel.putExtra("message","정보를 입력해주세요");
                    startActivity(signUpCancel);
                }
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
                overlapBool=true;
                for(int i=0;i<jarray.length();i++){ // json데이터를 뽑기 위함
                    JSONObject jObject = jarray.getJSONObject(i);
                    String id = jObject.getString("id");

                    Log.e("signup","id("+i+") : "+id);
                    if(id.equals(SignUpActivity.this.id)){
                        overlapBool=false;
                    }

                }
                Log.e("signup","id : "+SignUpActivity.this.id);
                if(overlapBool==true) {
                    Intent overlapOK = new Intent(SignUpActivity.this,Dialog.class);
                    overlapOK.putExtra("message","사용가능한 아이디입니다");
                    startActivity(overlapOK);
                }
                else {
                    Intent overlapCancel = new Intent(SignUpActivity.this,Dialog.class);
                    overlapCancel.putExtra("message","다른 아이디를 사용해주세요");
                    startActivity(overlapCancel);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    public class SendPostJSON extends AsyncTask<String, String, String> { // register coupon
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                String name = name_edit.getText().toString();
                String phone = phone_edit.getText().toString();
//                String pw = pw_edit.getText().toString();


                JSONObject jsonObject = new JSONObject();
//                Log.e("윤성",coupon1_content);
                jsonObject.accumulate("id", SignUpActivity.this.id);
                jsonObject.accumulate("pw", SignUpActivity.this.pw);
                jsonObject.accumulate("phone", phone);
                jsonObject.accumulate("name", name);
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

                    con.setRequestMethod("POST");//POST방식으로 보냄
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
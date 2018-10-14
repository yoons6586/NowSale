package com.example.yoonsung.nowsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    private Button loginBtn;
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















/*
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

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}

*/
//        intent.putExtra("ID",id_edit.getText().toString());
//        intent.putExtra("PW",pw_edit.getText().toString());

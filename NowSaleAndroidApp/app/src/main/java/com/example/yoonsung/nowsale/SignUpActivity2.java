package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity2 extends AppCompatActivity implements TextView.OnEditorActionListener{ // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private ClientInfoData clientInfoData;
    private EditText edit1,edit2,edit3;
    private Button btn;
    private int edit_num=1;
    private Boolean overlapBool;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up2);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        btn = findViewById(R.id.btn);

        next_intent = new Intent(this,SignUpActivity3.class);
        get_intent=getIntent();
        clientInfoData = (ClientInfoData)get_intent.getSerializableExtra("ClientInfoData");

//        edit1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edit1.addTextChangedListener(textWatcherInput1);
        edit2.addTextChangedListener(textWatcherInput2);
        edit3.addTextChangedListener(textWatcherInput3);
        edit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (isValidPassword(edit2.getText().toString())==false && edit2.getText().toString().equals("") == false) {
                    edit2.setText("");
                    edit2.setHint("비밀번호를 확인해주세요");
                    edit2.setHintTextColor(Color.RED);
                }
                if (edit2.getText().toString().equals(edit3.getText().toString())==false && edit3.getText().toString().equals("") == false) {
                    edit3.setText("");
                    edit3.setHint("비밀번호를 확인해주세요");
                    edit3.setHintTextColor(Color.RED);
                }
                if(edit_num!=1) {
                    edit1.setText("");
                    edit1.setHint("이메일");
                    edit1.setHintTextColor(getResources().getColor(R.color.cursorColor));
                }
                edit_num=1;
                Log.i("edit1","Test");
            }
        });
        edit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.i("edit2","Test");

                if (isValidEmail(edit1.getText().toString())==false && edit1.getText().toString().equals("") == false) {
                    edit1.setText("");
                    edit1.setHint("잘못된 이메일 형식입니다");
                    edit1.setHintTextColor(Color.RED);
                }
                else{
                    /*
                    중복확인
                     */
                    id = edit1.getText().toString();
                    new SignUpActivity2.JSONTask().execute(Config.url+"/overlapID?id="+id);
                }
                if (edit2.getText().toString().equals(edit3.getText().toString())==false && edit3.getText().toString().equals("") == false) {
                    edit3.setText("");
                    edit3.setHint("비밀번호를 확인해주세요");
                    edit3.setHintTextColor(Color.RED);
                }
                if(edit_num!=2){
                    edit2.setText("");
                    edit2.setHint("비밀번호 영어+숫자 8자 이상");
                    edit2.setHintTextColor(getResources().getColor(R.color.cursorColor));
                }
                edit_num=2;

            }
        });
        edit3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.i("edit2","Test");

                if (isValidEmail(edit1.getText().toString())==false && edit1.getText().toString().equals("") == false) {
                    edit1.setText("");
                    edit1.setHint("잘못된 이메일 형식입니다");
                    edit1.setHintTextColor(Color.RED);
                }
                else{
                    /*
                    중복확인
                     */
                    id = edit1.getText().toString();
                    new SignUpActivity2.JSONTask().execute(Config.url+"/overlapID?id="+id);
                }
                if (isValidPassword(edit2.getText().toString())==false && edit2.getText().toString().equals("") == false) {
                    edit2.setText("");
                    edit2.setHint("비밀번호를 확인해주세요");
                    edit2.setHintTextColor(Color.RED);
                }

                if(edit_num!=3){
                    edit3.setText("");
                    edit3.setHint("비밀번호 확인");
                    edit3.setHintTextColor(getResources().getColor(R.color.cursorColor));
                }
                edit_num=3;

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmail(edit1.getText().toString())){
                    if(edit2.getText().toString().equals("")==false){
                        if(edit2.getText().toString().equals(edit3.getText().toString())){
                            clientInfoData.setId(edit1.getText().toString());
                            clientInfoData.setPassword(edit2.getText().toString());
                            next_intent.putExtra("ClientInfoData",clientInfoData);
                            startActivity(next_intent);
                        }

                    }
                }
            }
        });
    }
    private boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
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

    @Override
    public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
        /*switch(v.getId())
        {
            case R.id.edit1:
            {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    Log.i("TEST", "TEST");
                }
                break;
            }
        }*/
        return false;
    }
    TextWatcher textWatcherInput1 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(edit2.getText().toString().equals("")==false && edit3.getText().toString().equals("")==false){
                if(isValidEmail(s.toString())){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_yellow));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                    }
                    btn.setTextColor(Color.BLACK);
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
            else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_gray));
                } else {
                    btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                }
                btn.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.i("afterTextChanged", s.toString());
        }
    };
    TextWatcher textWatcherInput2 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(edit1.getText().toString().equals("")==false && edit3.getText().toString().equals("")==false){
                if(edit3.getText().toString().equals(s.toString())){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_yellow));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                    }
                    btn.setTextColor(Color.BLACK);
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
            else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_gray));
                } else {
                    btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                }
                btn.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.i("afterTextChanged", s.toString());
        }
    };
    TextWatcher textWatcherInput3 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(edit1.getText().toString().equals("")==false && edit2.getText().toString().equals("")==false){
                if(edit2.getText().toString().equals(s.toString())){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_yellow));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                    }
                    btn.setTextColor(Color.BLACK);
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
            else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_gray));
                } else {
                    btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                }
                btn.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            Log.i("afterTextChanged", s.toString());
        }
    };
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
                    if(id.equals(SignUpActivity2.this.id)){
                        overlapBool=false;
                    }

                }
                Log.e("signup","id : "+SignUpActivity2.this.id);
                if(overlapBool==false) {
                    edit1.setText("");
                    edit1.setHint("중복되는 아이디입니다.");
                    edit1.setHintTextColor(Color.RED);
                }

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
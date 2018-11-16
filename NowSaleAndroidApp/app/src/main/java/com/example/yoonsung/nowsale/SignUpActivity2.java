package com.example.yoonsung.nowsale;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.http.AllService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity2 extends AppCompatActivity implements TextView.OnEditorActionListener{ // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private ClientVO clientVO;
    private EditText edit1,edit2,edit3;
    private TextView year_txt;
    private ImageView yearSelect;
    private Button btn;
    private int edit_num=1;
    private Boolean overlapBool;
    private String id;
    private DatePickerDialog.OnDateSetListener d;
    private CheckBox check_male,check_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up2);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }


        year_txt = findViewById(R.id.year_txt);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        btn = findViewById(R.id.btn);
        yearSelect = findViewById(R.id.select_year);
        check_male = findViewById(R.id.check_male);
        check_female = findViewById(R.id.check_female);

        d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
                year_txt.setText(""+year);
                if(isValidEmail(edit1.getText().toString())){
                    if(edit2.getText().toString().equals("")==false){
                        if(edit2.getText().toString().equals(edit3.getText().toString())){
                            if((check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)) {
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this,R.drawable.round_button_yellow));
                                } else {
                                    btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                                }
                                btn.setTextColor(Color.BLACK);
                            }
                        }

                    }
                }
            }
        };

        next_intent = new Intent(this,SignUpActivity3.class);
        get_intent=getIntent();
        clientVO = (ClientVO) get_intent.getSerializableExtra("ClientVO");

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
                    AllService allService = Config.retrofit.create(AllService.class);
                    Call<String> allOverlapRequest = allService.checkAllOverlap(id);
                    allOverlapRequest.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.code()== HttpStatus.SC_OK){

                            }
                            else if(response.code()== HttpStatus.SC_NO_CONTENT){
                                edit1.setText("");
                                edit1.setHint("중복되는 아이디입니다.");
                                edit1.setHintTextColor(Color.RED);
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }

                    });
//                    new SignUpActivity2.JSONTask().execute(Config.url+"/overlapID?id="+id);
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
                    AllService allService = Config.retrofit.create(AllService.class);
                    Call<String> allOverlapRequest = allService.checkAllOverlap(id);
                    allOverlapRequest.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.code()== HttpStatus.SC_OK){

                            }
                            else if(response.code()== HttpStatus.SC_NO_CONTENT){
                                edit1.setText("");
                                edit1.setHint("중복되는 아이디입니다.");
                                edit1.setHintTextColor(Color.RED);
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }

                    });
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

        yearSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyYearMonthPickerDialog pd = new MyYearMonthPickerDialog();
                pd.setListener(d);
                pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmail(edit1.getText().toString())){
                    if(edit2.getText().toString().equals("")==false){
                        if(edit2.getText().toString().equals(edit3.getText().toString())){
                            if((check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)) {
                                clientVO.setId(edit1.getText().toString());
                                clientVO.setPw(edit2.getText().toString());
                                next_intent.putExtra("ClientVO", clientVO);
                                startActivity(next_intent);
                            }
                        }

                    }
                }
            }
        });
        check_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_male.isChecked()){
                    check_female.setChecked(false);
                }
                else
                    check_female.setChecked(true);
                try {
                    if (isValidEmail(edit1.getText().toString())) {
                        if (edit2.getText().toString().equals("") == false) {
                            if (edit2.getText().toString().equals(edit3.getText().toString())) {
                                if ((check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                                    } else {
                                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                                    }
                                    btn.setTextColor(Color.BLACK);
                                }
                            }

                        }
                    }
                } catch (NumberFormatException e){
                    Log.e("fail","연도땜에 안돼");
                }
            }
        });
        check_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_female.isChecked())
                    check_male.setChecked(false);
                else
                    check_male.setChecked(true);
                try {
                    if (isValidEmail(edit1.getText().toString())) {
                        if (edit2.getText().toString().equals("") == false) {
                            if (edit2.getText().toString().equals(edit3.getText().toString())) {
                                if ((check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                                    } else {
                                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                                    }
                                    btn.setTextColor(Color.BLACK);
                                }
                            }

                        }
                    }
                } catch (NumberFormatException e){
                    Log.e("fail","연도땜에 안돼");
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
            try{
                if(edit2.getText().toString().equals("")==false && edit3.getText().toString().equals("")==false){
                    if(isValidEmail(s.toString()) && (check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)){
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
            catch(NumberFormatException e){
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
            try{
                if(edit1.getText().toString().equals("")==false && edit3.getText().toString().equals("")==false){
                    if(edit3.getText().toString().equals(s.toString())  && (check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)){
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
            catch(NumberFormatException e){
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
            try {
                if (edit1.getText().toString().equals("") == false && edit2.getText().toString().equals("") == false) {
                    if (edit2.getText().toString().equals(s.toString()) && (check_male.isChecked() || check_female.isChecked()) && (Integer.parseInt(year_txt.getText().toString()) >= 1940 && Integer.parseInt(year_txt.getText().toString()) <= 3000)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                        } else {
                            btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_yellow));
                        }
                        btn.setTextColor(Color.BLACK);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                        } else {
                            btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                        }
                        btn.setTextColor(Color.GRAY);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(SignUpActivity2.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            } catch(NumberFormatException e){
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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
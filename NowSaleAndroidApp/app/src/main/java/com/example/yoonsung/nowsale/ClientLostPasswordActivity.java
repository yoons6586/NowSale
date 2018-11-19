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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.LoginVO;
import com.example.yoonsung.nowsale.http.ClientService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientLostPasswordActivity extends AppCompatActivity implements TextView.OnEditorActionListener{ // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,next_intent;
    private ClientVO clientVO;
    private EditText edit1,edit2,edit3,edit4;
    private TextView year_txt,btn;
    private ImageView yearSelect,back;
    private int edit_num=1;
    private Boolean overlapBool;
    private String id;
    private DatePickerDialog.OnDateSetListener d;
    private CheckBox check_male,check_female;

    private RelativeLayout layout;
    private LinearLayout start_layout;
    private ImageView harin_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lost_pw);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }
        edit1 = findViewById(R.id.edit1);

        btn = findViewById(R.id.btn);

        back = findViewById(R.id.back);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        edit1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edit1.addTextChangedListener(textWatcherInput1);

        /*edit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edit_num!=1) {
                    edit1.setText("");
                    edit1.setHint("이메일");
                    edit1.setHintTextColor(getResources().getColor(R.color.cursorColor));
                }
                edit_num=1;
                Log.i("edit1","Test");
                if (isValidEmail(edit1.getText().toString())==false && edit1.getText().toString().equals("") == false) {
                    edit1.setText("");
                    edit1.setHint("잘못된 이메일 형식입니다");
                    edit1.setHintTextColor(Color.RED);
                }
            }
        });*/

        btn.setTextColor(Color.GRAY);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmail(edit1.getText().toString())){
                    LoadingAnimationApplication.getInstance().progressON(ClientLostPasswordActivity.this, Config.loadingContext);

                    ClientService clientService = Config.retrofit.create(ClientService.class);
                    LoginVO clientVO = new LoginVO(edit1.getText().toString(),"0");
                    Call<Void> request = clientService.findPassword(clientVO);
                    request.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == HttpStatus.SC_OK){
                                LoadingAnimationApplication.getInstance().progressOFF();
                                setResult(RESULT_OK);
                                finish();
                            }
                            else if(response.code() == HttpStatus.SC_NOT_FOUND) {
                                Toast.makeText(getApplicationContext(), "등록되지 않은 이메일입니다.", Toast.LENGTH_SHORT).show();
                                LoadingAnimationApplication.getInstance().progressOFF();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "이메일 전송이 제대로 되지 않았습니다", Toast.LENGTH_SHORT).show();
                                LoadingAnimationApplication.getInstance().progressOFF();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });


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
                if (isValidEmail(edit1.getText().toString())==true) {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(ClientLostPasswordActivity.this,R.drawable.yellow_btn_selector));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(ClientLostPasswordActivity.this, R.drawable.yellow_btn_selector));
                    }
                    btn.setTextColor(Color.BLACK);

                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn.setBackground(ContextCompat.getDrawable(ClientLostPasswordActivity.this,R.drawable.round_button_gray));
                    } else {
                        btn.setBackgroundDrawable(ContextCompat.getDrawable(ClientLostPasswordActivity.this, R.drawable.round_button_gray));
                    }
                    btn.setTextColor(Color.GRAY);
                }
            }
            catch(NumberFormatException e){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn.setBackground(ContextCompat.getDrawable(ClientLostPasswordActivity.this,R.drawable.round_button_gray));
                } else {
                    btn.setBackgroundDrawable(ContextCompat.getDrawable(ClientLostPasswordActivity.this, R.drawable.round_button_gray));
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
    protected void onDestroy() {
        super.onDestroy();
        LoadingAnimationApplication.getInstance().progressOFF();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
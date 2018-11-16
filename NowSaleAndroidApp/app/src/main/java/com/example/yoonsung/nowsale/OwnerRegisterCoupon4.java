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
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.SaleVO;
import com.example.yoonsung.nowsale.http.OwnerService;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.msebera.android.httpclient.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OwnerRegisterCoupon4 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private Intent get_intent,popUpIntent;
    private EditText edit1,edit2;
    private TextView txt1,txt2,txtStep,txtContent;
    private TextView next;
    private ImageView back;
    private int mYear, mMonth, mDay, mHour, mMinute,choose;
    private CouponVO couponVO;
    private SaleVO saleVO;
    private CheckBox check;

    private RelativeLayout layout;
    private LinearLayout start_layout;
    private ImageView harin_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register_coupon4);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunpenBold.otf"));

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }

         popUpIntent= new Intent(this,ClientCouponListPopupActivity.class);
        get_intent = getIntent();

        couponVO = (CouponVO) get_intent.getSerializableExtra("CouponVO");
        saleVO = (SaleVO) get_intent.getSerializableExtra("SaleVO");
        choose = get_intent.getIntExtra("choose",0);

        check  = findViewById(R.id.check);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        txtStep = findViewById(R.id.txt_step);
//        txtTitle = findViewById(R.id.txt_title);
        txtContent = findViewById(R.id.txt_content);

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


        if(choose==2){
            txtStep.setText("STEP 2");
            txtContent.setText("행사기간을 지정하시겠어요?");
            check.setVisibility(View.VISIBLE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.yellow_btn_selector));
            } else {
                next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.yellow_btn_selector));
            }
        }


        edit1.setClickable(false);
        edit1.setFocusable(false);
        edit2.setClickable(false);
        edit2.setFocusable(false);

        edit1.addTextChangedListener(textWatcherInput);
        edit2.addTextChangedListener(textWatcherInput);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.getVisibility()==View.GONE || check.isChecked()) {
                    new DatePickerDialog(OwnerRegisterCoupon4.this, mDateSetListener1, mYear,
                            mMonth, mDay).show();
                }
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.getVisibility()==View.GONE || check.isChecked()) {
                    new DatePickerDialog(OwnerRegisterCoupon4.this, mDateSetListener2, mYear,
                            mMonth, mDay).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (choose){
                    case 1: // 쿠폰 등록

//                        Log.e("ownerCouponData",""+couponVO.getRemain_count());
//                        Log.e("ownerCouponData",couponVO.getStart_date());
//                        Log.e("ownerCouponData",couponVO.getExpire_date());
//                        Log.e("ownerCouponData",couponVO.getQualification());
//                        Log.e("ownerCouponData",ownerCouponData.getRange());
                        if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                            /*
                            서버로 쿠폰 발급 보내기~
                             */
                            couponVO.setStart_date(edit1.getText().toString());
                            couponVO.setExpire_date(edit2.getText().toString());

                            popUpIntent.putExtra("what",2);
                            popUpIntent.putExtra("couponVO",couponVO);
                            startActivityForResult(popUpIntent,2);
                        }
                        else if(!check.isChecked()){
                            couponVO.setStart_date(null);
                            couponVO.setExpire_date(null);
                            popUpIntent.putExtra("what",2);
                            popUpIntent.putExtra("couponVO",couponVO);
                            startActivityForResult(popUpIntent,2);
                        }
                        break;
                    case 2: // 할인 등록

                        if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                            /*
                            서버로 쿠폰 발급 보내기~
                             */
                            couponVO.setStart_date(edit1.getText().toString());
                            couponVO.setExpire_date(edit2.getText().toString());

                            popUpIntent.putExtra("what",3);
                            popUpIntent.putExtra("saleVO",saleVO);
                            startActivityForResult(popUpIntent,3);
                        } else if(!check.isChecked()){
                            saleVO.setStart_date(null);
                            saleVO.setExpire_date(null);
                            popUpIntent.putExtra("what",3);
                            popUpIntent.putExtra("saleVO",saleVO);
                            startActivityForResult(popUpIntent,3);
                        }
                        break;
                }




            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_gray));
                    } else {
                        next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_gray));
                    }
                    next.setTextColor(Color.GRAY);

                    edit1.setText("");
                    edit1.setHint("");
                    edit2.setText("");
                    edit2.setHint("");
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.yellow_btn_selector));
                    } else {
                        next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.yellow_btn_selector));
                    }
                    next.setTextColor(Color.BLACK);

                    edit1.setText("");
                    edit1.setHint("");
                    edit2.setText("");
                    edit2.setHint("");
                }
            }
        });
    }
    TextWatcher textWatcherInput = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if(!edit1.getText().toString().equals("") && !edit2.getText().toString().equals("")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.yellow_btn_selector));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.yellow_btn_selector));
                }
                next.setTextColor(Color.BLACK);
            }
            else if(check.getVisibility()==View.GONE || check.isChecked()){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(OwnerRegisterCoupon4.this,R.drawable.round_button_gray));
                } else {
                    next.setBackgroundDrawable(ContextCompat.getDrawable(OwnerRegisterCoupon4.this, R.drawable.round_button_gray));
                }
                next.setTextColor(Color.GRAY);
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
    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //텍스트뷰의 값을 업데이트함
            edit1.setText(String.format(String.format("%d-%d-%d", mYear,mMonth + 1, mDay)));
        }
    };
    DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //텍스트뷰의 값을 업데이트함
            edit2.setText(String.format(String.format("%d-%d-%d", mYear,mMonth + 1, mDay)));
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2){ // 쿠폰 발급 팝업
            if(resultCode==RESULT_OK){ // client

                //데이터 받기
                OwnerService service = Config.retrofit.create(OwnerService.class);
                Call<Void> request = service.registerCoupon(couponVO, Config.ownerVO.getOwner_key());
                request.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e("responseCode",""+response.code());
                        if (response.code() == HttpStatus.SC_OK) {
                            Log.e("registerCoupon", "쿠폰이 발급되었습니다");
                            setResult(RESULT_OK);
                            finish();
                        } else if (response.code() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                            Toast.makeText(getApplicationContext(),"쿠폰 발급 실패 -> 네트워크 연결상태 확인하세요",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("responseCode","failure");
                        Toast.makeText(getApplicationContext(),"쿠폰 발급 실패 -> 네트워크 연결상태 확인하세요",Toast.LENGTH_SHORT).show();
//                        setResult(RESULT_OK);
//                        finish();
                    }


                });


            }
        }
        else if(requestCode==3){ // 할인 발급 팝업
            if(resultCode==RESULT_OK){
                OwnerService service = Config.retrofit.create(OwnerService.class);
                Call<Void> request = service.registerSale(saleVO, Config.ownerVO.getOwner_key());
                request.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e("responseCode",""+response.code());
                        if (response.code() == HttpStatus.SC_OK) {
                            Log.e("registerCoupon", "할인정보가 등록되었습니다");
                            setResult(3);
                            finish();

                        } else if (response.code() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                            Toast.makeText(getApplicationContext(),"할인 발급 실패 -> 네트워크 연결상태 확인하세요",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("responseCode","failure");
                        Toast.makeText(getApplicationContext(),"할인 발급 실패 -> 네트워크 연결상태 확인하세요",Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
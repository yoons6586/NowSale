package com.chapchapbrothers.nowsale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chapchapbrothers.nowsale.VO.CouponVO;
import com.chapchapbrothers.nowsale.VO.SaleVO;

public class ClientCouponListPopupActivity extends Activity {
    private Intent getIntent,loginIntent,resultIntent;
    private TextView okText,cancelText,contentText,titleText,qualficationText,dateText,couponContentText;
    private LinearLayout okBtn,cancelBtn;
    private int position,what;
    private CouponVO couponVO;
    private SaleVO saleVO;
    private int isOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_use_cancel);

        okText = findViewById(R.id.okText);
        cancelText = findViewById(R.id.cancelText);
        titleText = findViewById(R.id.textTitle);
        contentText = findViewById(R.id.textContent);
        okBtn = findViewById(R.id.okBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        qualficationText=findViewById(R.id.qualification_text);
        dateText=findViewById(R.id.date);
        couponContentText=findViewById(R.id.content);


        getIntent = getIntent();
        position = getIntent.getIntExtra("position",-1);
        what = getIntent.getIntExtra("what",-1);
        resultIntent = new Intent();
        resultIntent.putExtra("position",position);

        if(what!=3){
            couponVO = (CouponVO) getIntent.getSerializableExtra("couponVO");
            setAllText(couponVO);
        }
        if(what==11){ // client 쿠폰 사용
            okText.setText("사용하기");
            titleText.setText("쿠폰 사용 처리를 하시겠습니까?");
            contentText.setText("사용 처리 후에는\n쿠폰을 더 이상 쓸 수 없고\n쿠폰에 따라 재발급이 불가능할 수 있습니다.");
        }
        else if(what==12){  // client 쿠폰 삭제

            okText.setText("삭제하기");
            titleText.setText("쿠폰을 삭제하시겠습니까?");
            contentText.setText("삭제 후에는\n쿠폰이 지갑에서 삭제되고\n쿠폰 사용을 위해서는 재발급을 받아야 합니다.");
        }
        else if(what==2){ // 점주 쿠폰 등록
            okText.setText("발급하기");
            titleText.setText("쿠폰을 정말 발급하시겠습니까?");
            contentText.setText("쿠폰 발급 후 취소를 할 경우\n고객에게 이미 발급된\n쿠폰은 취소할 수 없습니다");
        }
        else if(what==3){
            saleVO = (SaleVO) getIntent.getSerializableExtra("saleVO");
            couponContentText.setText(saleVO.getContent());
            qualficationText.setText(saleVO.getQualification());

            try {
                Log.e("ownerCouponData",saleVO.getStart_date());
                Log.e("ownerCouponData",saleVO.getExpire_date());

                dateText.setText(saleVO.getStart_date()+" ~ "+saleVO.getExpire_date());
            } catch (NullPointerException e){
                dateText.setVisibility(View.GONE);
            }

            okText.setText("등록하기");
            titleText.setText("할인행사를 정말 등록하시겠습니까?");
            contentText.setText("");
        }
        else if(what==13){ // client 할인 삭제

            okText.setText("삭제하기");
            titleText.setText("할인소식을 삭제 하시겠습니까?");
            contentText.setText("삭제 후에는 해당 소식이 리스트에서 삭제됩니다.");
        } else if (what == 51) { // 점주 쿠폰 삭제

            okText.setText("삭제하기");
            titleText.setText("쿠폰을 삭제하시겠습니까?");
            contentText.setText("삭제 후에는 해당 쿠폰이 리스트에서 삭제되지만\n이미 고객에게 발급된 쿠폰은 삭제되지 않습니다.");
        }
        else if(what==52){ // 점주 할인 삭제

            okText.setText("삭제하기");
            titleText.setText("할인소식을 삭제 하시겠습니까?");
            contentText.setText("삭제 후에는 해당 소식이 리스트에서 삭제됩니다.");
        }
//        getIntent = getIntent();
        /*isOwner = getIntent.getIntExtra("isOwner",0);
        if(isOwner == 1){ // 점주입니다.
            contentText.setText("고객으로 로그인 해주세요!");
            loginBtn.setVisibility(View.INVISIBLE);
        }*/

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void setAllText(CouponVO couponVO){
        couponContentText.setText(couponVO.getContent());
        qualficationText.setText(couponVO.getQualification());

        try {
            Log.e("ownerCouponData",couponVO.getStart_date());
            Log.e("ownerCouponData",couponVO.getExpire_date());

            dateText.setText(couponVO.getStart_date()+" ~ "+couponVO.getExpire_date());
        } catch (NullPointerException e){
            dateText.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }






}

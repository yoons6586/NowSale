package com.example.yoonsung.nowsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OwnerRegisterCoupon1 extends AppCompatActivity { // 관리자와 사용자 로그인이 둘이 서로 달라야 함
    private LinearLayout sale,service,other;
    private ImageView back;
    private Intent intent_step2,get_intent;
    private OwnerCouponData ownerCouponData;
    private TextView txt1,txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register_coupon1);
        ownerCouponData = new OwnerCouponData();
        get_intent = getIntent();
        ownerCouponData = (OwnerCouponData) get_intent.getSerializableExtra("OwnerCouponData");
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        if(ownerCouponData.getChoose().equals("coupon")){

        }
        else{
            txt1.setText("할인행사 등록");
            txt2.setText("어떤 유형의 할인행사를\n등록하시겠어요?");
        }
        sale = findViewById(R.id.sale);
        service = findViewById(R.id.service);
        other = findViewById(R.id.other);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intent_step2 = new Intent(this,OwnerRegisterCoupon2.class);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent_step2.putExtra("what","sale");
                intent_step2.putExtra("OwnerCouponData",ownerCouponData);
                startActivity(intent_step2);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_step2.putExtra("OwnerCouponData",ownerCouponData);
                intent_step2.putExtra("what","service");
                startActivity(intent_step2);
            }
        });
    }
}
package com.example.demo.all.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerCouponShowVO {
    private int coupon_key;
    private String market_name,coupon_content,on_off;
}

package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerCouponShowVO {
    private String market_name,coupon_content,on_off;
    private int coupon_key;
}

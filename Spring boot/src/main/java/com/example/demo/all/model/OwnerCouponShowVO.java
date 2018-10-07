package com.example.demo.all.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerCouponShowVO {
    private int owner_key,coupon_key;
    private String category,address,phone,logo_img,market_name,market_introduce,location;
}

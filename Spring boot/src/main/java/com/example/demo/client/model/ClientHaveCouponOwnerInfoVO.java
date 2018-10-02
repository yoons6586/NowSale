package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientHaveCouponOwnerInfoVO {
    private int coupon_key;
    private String market_name,coupon_content,on_off;
}

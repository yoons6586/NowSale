package com.example.demo.owner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerRegisterCouponVO {
    private int owner_key,start_count,coupon_key,remain_count;
    private String expire_date,start_date,qualification,content,on_off;
}

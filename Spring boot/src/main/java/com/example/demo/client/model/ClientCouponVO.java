package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientCouponVO {
    private int owner_key,coupon_key,start_count,remain_count;
    private String start_date,expire_date,qualification,content,address,phone,logo_img,market_name,market_introduce,category,location,on_off;


}

package com.example.yoonsung.nowsale.VO;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponVO implements Serializable{
    private int owner_key,client_key,sale_key,coupon_key,start_count,remain_count,dangol_count;
    private String who_key,address,phone,logo_img,market_name,market_introduce,category,owner_img,location,start_date,expire_date,qualification,content,working_day,working_time;
    private double longitude,latitude;
}

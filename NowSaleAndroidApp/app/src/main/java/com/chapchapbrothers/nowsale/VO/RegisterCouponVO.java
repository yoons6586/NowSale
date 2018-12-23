package com.chapchapbrothers.nowsale.VO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */
@Getter
@Setter
public class RegisterCouponVO implements Serializable{
    private int owner_key,start_count,coupon_key,remain_count;
    private String expire_date,start_date,qualification,content,on_off;

    public RegisterCouponVO(int owner_key, int start_count, int coupon_key, int remain_count, String expire_date, String start_date, String qualification, String content, String on_off) {
        this.owner_key = owner_key;
        this.start_count = start_count;
        this.coupon_key = coupon_key;
        this.remain_count = remain_count;
        this.expire_date = expire_date;
        this.start_date = start_date;
        this.qualification = qualification;
        this.content = content;
        this.on_off = on_off;
    }
}

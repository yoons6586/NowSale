package com.example.yoonsung.nowsale.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
public class OwnerCouponVO {
    private int owner_key,coupon_key;

    public OwnerCouponVO(int owner_key, int coupon_key){
        this.owner_key = owner_key;
        this.coupon_key = coupon_key;
    }

}

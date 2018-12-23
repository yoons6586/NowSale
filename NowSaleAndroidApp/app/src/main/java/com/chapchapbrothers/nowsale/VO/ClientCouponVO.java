package com.chapchapbrothers.nowsale.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
public class ClientCouponVO {
    private int client_key,coupon_key;

    public ClientCouponVO(int client_key,int coupon_key){
        this.client_key = client_key;
        this.coupon_key = coupon_key;
    }

}

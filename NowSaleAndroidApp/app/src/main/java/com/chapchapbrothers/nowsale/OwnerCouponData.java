package com.chapchapbrothers.nowsale;

import java.io.Serializable;

/**
 * Created by yoonsung on 2018. 8. 31..
 */

public class OwnerCouponData implements Serializable{
    private String range,gift,couponCnt,start,expire,choose;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getCouponCnt() {
        return couponCnt;
    }

    public void setCouponCnt(String couponCnt) {
        this.couponCnt = couponCnt;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }
}

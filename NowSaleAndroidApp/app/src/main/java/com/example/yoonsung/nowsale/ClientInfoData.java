package com.example.yoonsung.nowsale;

import java.io.Serializable;

/**
 * Created by yoonsung on 2018. 8. 31..
 */

public class ClientInfoData implements Serializable{
    private boolean alarm_push,alarm_mail,alarm_SMS;
    private String id,password,phoneNum,nickName,who_key;
    private int coupon1,coupon2,coupon3,user_key;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isAlarm_mail() {
        return alarm_mail;
    }

    public void setAlarm_mail(boolean alarm_mail) {
        this.alarm_mail = alarm_mail;
    }

    public boolean isAlarm_SMS() {
        return alarm_SMS;
    }

    public void setAlarm_SMS(boolean alarm_SMS) {
        this.alarm_SMS = alarm_SMS;
    }

    public boolean isAlarm_push() {
        return alarm_push;
    }

    public void setAlarm_push(boolean alarm_push) {
        this.alarm_push = alarm_push;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCoupon3() {
        return coupon3;
    }

    public void setCoupon3(int coupon3) {
        this.coupon3 = coupon3;
    }

    public int getCoupon2() {
        return coupon2;
    }

    public void setCoupon2(int coupon2) {
        this.coupon2 = coupon2;
    }

    public int getCoupon1() {
        return coupon1;
    }

    public void setCoupon1(int coupon1) {
        this.coupon1 = coupon1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUser_key() {
        return user_key;
    }

    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    public String getWho_key() {
        return who_key;
    }

    public void setWho_key(String who_key) {
        this.who_key = who_key;
    }
}

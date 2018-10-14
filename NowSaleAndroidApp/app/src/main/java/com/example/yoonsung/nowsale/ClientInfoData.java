package com.example.yoonsung.nowsale;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 8. 31..
 */

@Getter
@Setter
@NoArgsConstructor
public class ClientInfoData implements Serializable{
    private boolean alarm_push,alarm_mail,alarm_SMS;
    private String id,password,phoneNum,nickName,who_key;
    private int coupon1,coupon2,coupon3,user_key;


}

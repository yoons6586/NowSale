package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientVO {
    private String nickName,who_key,id,pw,phone,alarm_push,alarm_mail,alarm_SMS,sex,birth;
    private int user_key,coupon1,coupon2,coupon3,coupon4,coupon5;

}

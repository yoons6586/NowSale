package com.example.demo.owner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerVO {
    private int owner_key;
    private String who_key,id,pw,address,nickName,phone,logo_img,market_name,market_introduce,category,owner_img,location,alarm_push,alarm_mail,alarm_SMS;

}

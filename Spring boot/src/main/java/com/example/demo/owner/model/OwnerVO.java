package com.example.demo.owner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OwnerVO {
    private int owner_key;
    private String who_key,id,pw,address,nickName,phone,logo_img,market_name,market_introduce,category,owner_img,location,alarm_push,alarm_mail,alarm_SMS;
    private String market_img_cnt;
    private String longitude;
    private String latitude;
    private String working_day;
    private String working_time;

}

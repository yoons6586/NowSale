package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientVO {
    private String nickName,who_key,id,pw,phone,alarm_push,alarm_mail,alarm_SMS,gender,birth,client_img;
    private int client_key;
    private String refresh_token;
    private String provider_type;

}

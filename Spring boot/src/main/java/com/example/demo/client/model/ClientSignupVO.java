package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientSignupVO {
    private String nickName,id,pw,phone,alarm_push,alarm_mail,alarm_SMS,gender,birth;
}

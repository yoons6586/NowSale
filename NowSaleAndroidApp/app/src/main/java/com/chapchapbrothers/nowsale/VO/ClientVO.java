package com.chapchapbrothers.nowsale.VO;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
@NoArgsConstructor
public class ClientVO implements Serializable{
    private String nickName,who_key,id,pw,phone,alarm_push,alarm_mail,alarm_SMS,gender,birth,client_img;
    private int client_key;

}

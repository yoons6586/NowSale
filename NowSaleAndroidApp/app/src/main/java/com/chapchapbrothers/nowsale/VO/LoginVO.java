package com.chapchapbrothers.nowsale.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter

public class LoginVO {
    private String id,pw;

    public LoginVO(String id,String pw){
        this.id=id;
        this.pw=pw;
    }
}

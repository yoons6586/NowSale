package com.example.yoonsung.nowsale.VO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
@NoArgsConstructor
public class OwnerVO {
    private int owner_key;
    private String who_key,id,pw,address,nickName,phone,logo_img,market_name,market_introduce,category,owner_img,location;

}

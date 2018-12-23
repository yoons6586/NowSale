package com.chapchapbrothers.nowsale.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
public class AllOwnerClientKeyVO {
    private int client_key,owner_key;
    public AllOwnerClientKeyVO(int owner_key,int client_key){
        this.client_key=client_key;
        this.owner_key=owner_key;
    }
}

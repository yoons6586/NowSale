package com.example.yoonsung.nowsale.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
public class ClientSaleVO {
    private int client_key,sale_key;

    public ClientSaleVO(int client_key, int sale_key){
        this.client_key = client_key;
        this.sale_key = sale_key;
    }

}

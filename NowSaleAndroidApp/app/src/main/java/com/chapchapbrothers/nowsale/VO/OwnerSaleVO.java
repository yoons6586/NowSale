package com.chapchapbrothers.nowsale.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
public class OwnerSaleVO {
    private int owner_key,sale_key;

    public OwnerSaleVO(int owner_key, int sale_key){
        this.owner_key = owner_key;
        this.sale_key = sale_key;
    }

}

package com.example.yoonsung.nowsale.VO;

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
public class IsFavoriteGetCountVO implements Serializable{
    private int dangol_count;
    private boolean dangol;
    private int market_img_cnt,menu_img_cnt;
}

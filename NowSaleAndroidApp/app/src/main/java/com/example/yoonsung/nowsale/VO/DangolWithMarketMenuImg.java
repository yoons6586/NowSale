package com.example.yoonsung.nowsale.VO;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

@Getter
@Setter
@NoArgsConstructor
public class DangolWithMarketMenuImg implements Serializable{
    private Integer dangol_count;
    private Boolean dangol;
    private List<MenuVO> menuVOList;
    private List<MarketImgVO> marketImgVOList;
}

package com.example.demo.all.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DangolWithMarketMenuImg {
    private int dangol_count;
    private boolean dangol;
    private List<MenuVO> menuVOList;
    private List<MarketImgVO> marketImgVOList;

}

package com.example.demo.all.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IsFavoriteGetCountVO {
    private int dangol_count;
    private boolean dangol;
    private int market_img_cnt,menu_img_cnt;
}

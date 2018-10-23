package com.example.demo.all.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerSaleShowVO {
    private int owner_key,sale_key;
    private String who_key,address,phone,logo_img,market_name,market_introduce,category,owner_img,location,start_date,expire_date,qualification,content,on_off;
}

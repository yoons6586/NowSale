package com.example.demo.all.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarketVO {
    private int owner_key;
    private String address,market_name,phone,logo_img,market_introduce,category,location,owner_img;
}

package com.example.demo.all.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MarketVO {
    private int owner_key;
    private String address,market_name,phone,logo_img,market_introduce,category,location,owner_img,longitude,latitude,working_day,working_time;
    private List<String> market_img;
}

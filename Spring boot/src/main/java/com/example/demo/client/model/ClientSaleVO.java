package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientSaleVO {
    private int owner_key,sale_key;
    private String start_date,expire_date,qualification,content,address,phone,logo_img,market_name,market_introduce,category,location,on_off,working_day,working_time;
    private double longitude,latitude;

}

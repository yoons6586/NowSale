package com.example.demo.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientFavoriteMarketVO {
    private int client_key,owner_key;
    private String address,market_name,phone,logo_img,market_introduce,category,location,owner_img,working_day,working_time;
    private double longitude,latitude;
}

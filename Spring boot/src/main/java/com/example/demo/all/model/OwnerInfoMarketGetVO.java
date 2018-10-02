package com.example.demo.all.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerInfoMarketGetVO {
    private String market_name,address,introduce,phone;
    private int user_key;

}

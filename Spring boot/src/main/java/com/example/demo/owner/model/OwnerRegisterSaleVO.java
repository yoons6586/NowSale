package com.example.demo.owner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerRegisterSaleVO {
    private int owner_key,sale_key;
    private String expire_date,start_date,qualification,content,on_off;
}

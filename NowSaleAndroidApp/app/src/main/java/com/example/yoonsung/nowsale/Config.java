package com.example.yoonsung.nowsale;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    public static String url="http://192.168.35.120:8080";
    public static ClientVO clientVO = new ClientVO();
    public static OwnerVO ownerVO = new OwnerVO();
    public static String who_key;
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
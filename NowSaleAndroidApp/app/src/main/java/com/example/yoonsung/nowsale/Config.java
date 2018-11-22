package com.example.yoonsung.nowsale;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Config {
    public static String url="http://ec2-52-78-188-139.ap-northeast-2.compute.amazonaws.com:8080";
    public static ClientVO clientVO = new ClientVO();
    public static OwnerVO ownerVO = new OwnerVO();
    public static String who_key;
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static String loadingContext="Loading...";
    public static String naverMapClientID= "jXpEGi6SmZR_dAOl54_S";
}
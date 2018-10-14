package com.example.yoonsung.nowsale.http;

import com.example.yoonsung.nowsale.VO.ClientVO;
import com.example.yoonsung.nowsale.VO.CouponVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

public interface AllService {
    @GET("/client/all")
    Call<List<ClientVO>> getUserRepositories(/*@Path("user") String userName*/);

    @GET("/all/category/coupon/get/{category}")
    Call<List<CouponVO>> getCategoryCoupon(@Path("category") String category);

    @GET("/all/favorite/get/count/{owner_key}")
    Call<Integer> getFavoriteCount(@Path("owner_key") int owner_key);

    @GET("/all/favorite/is/{owner_key}/{client_key}")
    Call<String> isFavorite(@Path("owner_key") int owner_key,@Path("client_key") int client_key);

}

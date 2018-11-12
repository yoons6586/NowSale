package com.example.yoonsung.nowsale.http;

import com.example.yoonsung.nowsale.VO.CouponVO;
import com.example.yoonsung.nowsale.VO.LoginVO;
import com.example.yoonsung.nowsale.VO.OwnerCouponVO;
import com.example.yoonsung.nowsale.VO.OwnerSaleVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;
import com.example.yoonsung.nowsale.VO.SaleVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

public interface OwnerService {
    @POST("/owner/login")
    Call<List<OwnerVO>> isLogin(@Body LoginVO loginVO);

    @GET("/owner/coupon/count/{owner_key}")
    Call<Integer> couponCount(@Path("owner_key")int owner_key);

    @GET("/owner/sale/count/{owner_key}")
    Call<Integer> saleCount(@Path("owner_key")int owner_key);

    @POST("/owner/coupon/update/{owner_key}")
    Call<String> registerCoupon(@Body CouponVO couponVO, @Path("owner_key")int owner_key);

    @POST("/owner/sale/insert/{owner_key}")
    Call<String> registerSale(@Body SaleVO saleVO, @Path("owner_key")int owner_key);

    @GET("/owner/coupon/get/{owner_key}")
    Call<List<CouponVO>> getRegisteredCoupon(@Path("owner_key")int owner_key);

    @GET("/owner/sale/get/{owner_key}")
    Call<List<CouponVO>> getRegisteredSale(@Path("owner_key")int owner_key);

    @HTTP(method = "DELETE",path="/owner/coupon/delete",hasBody=true)
    Call<OwnerCouponVO> deleteOwnerCouponList(@Body OwnerCouponVO ownerCouponVO);

    @HTTP(method = "DELETE",path="/owner/sale/delete",hasBody=true)
    Call<OwnerSaleVO> deleteOwnerSaleList(@Body OwnerSaleVO ownerSaleVO);
}

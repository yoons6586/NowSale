package com.chapchapbrothers.nowsale.http;

import com.chapchapbrothers.nowsale.VO.CouponVO;
import com.chapchapbrothers.nowsale.VO.LoginVO;
import com.chapchapbrothers.nowsale.VO.OwnerCouponVO;
import com.chapchapbrothers.nowsale.VO.OwnerSaleVO;
import com.chapchapbrothers.nowsale.VO.OwnerVO;
import com.chapchapbrothers.nowsale.VO.SaleVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<Void> registerCoupon(@Body CouponVO couponVO, @Path("owner_key")int owner_key);

    @POST("/owner/sale/insert/{owner_key}")
    Call<Void> registerSale(@Body SaleVO saleVO, @Path("owner_key")int owner_key);

    @GET("/owner/coupon/get/{owner_key}")
    Call<List<CouponVO>> getRegisteredCoupon(@Path("owner_key")int owner_key);

    @GET("/owner/sale/get/{owner_key}")
    Call<List<CouponVO>> getRegisteredSale(@Path("owner_key")int owner_key);

    @HTTP(method = "DELETE",path="/owner/coupon/delete",hasBody=true)
    Call<OwnerCouponVO> deleteOwnerCouponList(@Body OwnerCouponVO ownerCouponVO);

    @HTTP(method = "DELETE",path="/owner/sale/delete",hasBody=true)
    Call<OwnerSaleVO> deleteOwnerSaleList(@Body OwnerSaleVO ownerSaleVO);

    @PUT("/owner/info/update/{owner_key}")
    Call<Void> updateOwnerInfo(@Path("owner_key")int owner_key, @Body OwnerVO ownerVO);
}

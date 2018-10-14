package com.example.yoonsung.nowsale.http;

import com.example.yoonsung.nowsale.VO.LoginVO;
import com.example.yoonsung.nowsale.VO.OwnerVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yoonsung on 2018. 10. 8..
 */

public interface OwnerService {
    @POST("/owner/login")
    Call<List<OwnerVO>> isLogin(@Body LoginVO loginVO);
}

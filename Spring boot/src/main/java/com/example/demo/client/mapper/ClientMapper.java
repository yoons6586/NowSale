package com.example.demo.client.mapper;

import com.example.demo.client.model.ClientCouponVO;
import com.example.demo.client.model.ClientVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.example.demo.client.mapper.ClientMapper")
@Mapper
public interface ClientMapper {
    @Select("select * from client_list")
    List<ClientVO> findAllClient();

    @Select("select coupon1,coupon2,coupon3,coupon4,coupon5 from client_list WHERE user_key = #{user_key}")
    List<ClientCouponVO> clientCouponGet(int user_key);

//    @Delete("DELETE FROM client_list WHERE user_key=#{user_key}")
}


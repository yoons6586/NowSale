package com.example.demo.all.mapper;

import com.example.demo.client.model.ClientCouponVO;
import com.example.demo.client.model.ClientVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.example.demo.all.mapper.AllMapper")
@Mapper
public interface AllMapper {
//    @Delete("DELETE FROM client_list WHERE user_key=#{user_key}")
    @Select("SELECT id FROM show_id_pw WHERE id=#{id}")
    List<String> overlapID(String id);
}


package com.example.demo.client.mapper;

import com.example.demo.client.model.ClientCouponVO;
import com.example.demo.client.model.ClientLoginVO;
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

//    @Delete("DELETE FROM client_list WHERE user_key=#{user_key}")
    @Select("SELECT id FROM show_id_pw WHERE id=#{id}")
    List<Integer> overlapID(int id);

    @Select("SELECT * FROM client_list ORDER BY client_key DESC limit 1")
    int getUserKey();

    @Select("SELECT id,pw FROM client_list WHERE id=#{id} and pw=#{pw}")
    List<ClientLoginVO> loginClient(ClientLoginVO clientLoginVO);

    @Select("SELECT id FROM client_list WHERE id=#{id}")
    String overlapIdClient(String id);
}


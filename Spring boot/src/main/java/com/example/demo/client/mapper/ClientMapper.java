package com.example.demo.client.mapper;

import com.example.demo.client.model.ClientVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.example.demo.client.mapper.ClientMapper")
@Mapper
public interface ClientMapper {
    @Select("select * from client_list")
    List<ClientVO> findAllClient();
}

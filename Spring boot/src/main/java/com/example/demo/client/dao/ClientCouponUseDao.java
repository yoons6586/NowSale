package com.example.demo.client.dao;

import com.example.demo.client.model.ClientHaveCouponVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class ClientCouponUseDao {
    private SqlSessionFactory sqlSessionFactory;


    private ClientHaveCouponVO clientHaveCouponVO;
    public ClientCouponUseDao(ClientHaveCouponVO clientHaveCouponVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

            this.clientHaveCouponVO = clientHaveCouponVO;
//            sqlSessionFactoryBuilder은 만듦과 동시에 builder함수만 쓰고 갖다버림


//            .build(is)하자마자 바로 뒤짐


//            언제든지 세션을 open하기 위해서는 sqlSessionFactory는 계속 있어야됨

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public ResponseEntity<ClientHaveCouponVO> clientCouponUse(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {

            sqlSession.delete("dao.mybatisMapper.deleteClientCoupon",clientHaveCouponVO);

            sqlSession.commit();
            return new ResponseEntity<ClientHaveCouponVO>(clientHaveCouponVO, HttpStatus.OK);

        }
        finally {
            sqlSession.close();
        }
    }
}

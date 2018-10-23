package com.example.demo.client.dao;

import com.example.demo.client.model.ClientCouponCountVO;
import com.example.demo.client.model.ClientHaveCouponVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ClientCouponInsertDao {
    private SqlSessionFactory sqlSessionFactory;
    private List<Integer> remainCountList;

    private ClientHaveCouponVO clientHaveCouponVO;
    public ClientCouponInsertDao(ClientHaveCouponVO clientHaveCouponVO){
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
    public ResponseEntity<ClientHaveCouponVO> clientCouponInsert(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {

            sqlSession.insert("dao.mybatisMapper.insertClientCoupon",clientHaveCouponVO);
            remainCountList = sqlSession.selectList("dao.mybatisMapper.selectRemainCountCoupon",clientHaveCouponVO);
            sqlSession.update("dao.mybatisMapper.updateCouponRemainCount", new ClientCouponCountVO(clientHaveCouponVO.getCoupon_key(),remainCountList.get(0)-1));
            sqlSession.commit();
            return new ResponseEntity<ClientHaveCouponVO>(clientHaveCouponVO, HttpStatus.OK);

        }
        finally {
            sqlSession.close();
        }
    }
}

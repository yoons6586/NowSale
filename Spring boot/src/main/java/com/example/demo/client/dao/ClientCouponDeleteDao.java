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

//TODO 보통 Dao 클래스를 여러개 만들지 않고 하나의 Class안에서 CRUD 를 처리하면 된다...
//TODO 같은 Table은 보통 CRUD처리를 한 Class내에서 한다.
public class ClientCouponDeleteDao {
    private SqlSessionFactory sqlSessionFactory;


    private ClientHaveCouponVO clientHaveCouponVO;
    public ClientCouponDeleteDao(ClientHaveCouponVO clientHaveCouponVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "myBatisConfiguration.xml");
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
    public ResponseEntity<ClientHaveCouponVO> clientCouponDelete(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //TODO 지워졌는지 안지워졌는지 update됐는지 안됏는지 확인해야됨
            //TODO delete,update return 값 뭐 오는지 확인해보기 ->
            sqlSession.delete("dao.mybatisMapper.deleteClientCoupon",clientHaveCouponVO);
            sqlSession.update("dao.mybatisMapper.updateClientCouponCount",clientHaveCouponVO.getCoupon_key());

            sqlSession.commit();
            return new ResponseEntity<ClientHaveCouponVO>(clientHaveCouponVO, HttpStatus.OK);

        }
        finally {
            sqlSession.close();
        }
    }
}

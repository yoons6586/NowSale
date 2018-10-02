package com.example.demo.client.dao;

import com.example.demo.client.model.ClientCouponVO;
import com.example.demo.client.model.ClientVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class ClientSignupDao {
    private SqlSessionFactory sqlSessionFactory;

    ClientVO clientVO;
    public ClientSignupDao(ClientVO clientVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

            this.clientVO = clientVO;

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
    public ResponseEntity<String> clientSignup(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.insert("dao.mybatisMapper.clientSignup",clientVO);
            sqlSession.commit();
            //commit이 있기에 트랜잭션 관리할 수 있다.

            return new ResponseEntity<>("client signup successfully", HttpStatus.OK);
//            return sqlSession.selectList("dao.mybatisClientMapper.selectAll");
        }
        finally {
            sqlSession.close();
        }
    }
}

package com.example.demo.client.dao;

import com.example.demo.client.model.ClientEmailVO;
import com.example.demo.client.model.ClientLoginVO;
import com.example.demo.client.model.ClientVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class ClientLostPasswordDao {
    private SqlSessionFactory sqlSessionFactory;

    private ClientLoginVO clientLoginVO;

    public ClientLostPasswordDao(ClientLoginVO clientLoginVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

            this.clientLoginVO = clientLoginVO;

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
    public void clientLostPassword(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update("dao.mybatisMapper.updateClientPassword", clientLoginVO);
            sqlSession.commit();
            //commit이 있기에 트랜잭션 관리할 수 있다.


//            return sqlSession.selectList("dao.mybatisClientMapper.selectAll");
        }
        finally {
            sqlSession.close();
        }
    }
}

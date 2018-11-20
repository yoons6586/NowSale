package com.example.demo.all.dao;

import com.example.demo.all.model.AllLoginInfoVO;
import com.example.demo.client.model.ClientLoginVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class AllLostPasswordDao {
    private SqlSessionFactory sqlSessionFactory;

    private AllLoginInfoVO allLoginInfoVO;
    private int clientOwner;

    public AllLostPasswordDao(AllLoginInfoVO allLoginInfoVO,int clientOwner){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

            this.allLoginInfoVO = allLoginInfoVO;
            this.clientOwner = clientOwner;

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
    public void allLostPassword(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            if(clientOwner==1) {
                sqlSession.update("dao.mybatisMapper.updateClientPassword", allLoginInfoVO);
            } else
                sqlSession.update("dao.mybatisMapper.updateOwnerPassword", allLoginInfoVO);

            sqlSession.commit();
            //commit이 있기에 트랜잭션 관리할 수 있다.
//            return sqlSession.selectList("dao.mybatisClientMapper.selectAll");
        }
        finally {
            sqlSession.close();
        }
    }
}

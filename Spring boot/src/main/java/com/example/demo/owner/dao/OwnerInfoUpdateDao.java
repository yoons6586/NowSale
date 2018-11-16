package com.example.demo.owner.dao;

import com.example.demo.client.model.ClientVO;
import com.example.demo.owner.model.OwnerVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class OwnerInfoUpdateDao {
    private SqlSessionFactory sqlSessionFactory;
    private OwnerVO ownerVO;
    public OwnerInfoUpdateDao(OwnerVO ownerVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            this.ownerVO = ownerVO;

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
    public ResponseEntity ownerInfoUpdate(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update("dao.mybatisMapper.updateOwnerInfo",ownerVO);
            sqlSession.commit();
            return new ResponseEntity(HttpStatus.OK);
//            return sqlSession.selectList("dao.mybatisClientMapper.selectAll");
        }
        finally {
            sqlSession.close();
        }
    }
}

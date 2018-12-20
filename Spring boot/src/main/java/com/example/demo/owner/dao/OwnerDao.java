package com.example.demo.owner.dao;

import com.example.demo.owner.model.OwnerVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class OwnerDao {
    private SqlSessionFactory sqlSessionFactory;

    public OwnerDao(){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "myBatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

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
    public ResponseEntity signUpOwner(OwnerVO ownerVO){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            System.out.println("owner_key : "+ownerVO.getOwner_key());
            sqlSession.insert("dao.mybatisMapper.insertSignUpOwner",ownerVO);
            sqlSession.commit();

            return  new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        finally {
            sqlSession.close();
        }
    }
}

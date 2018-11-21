package com.example.demo.owner.dao;

import com.example.demo.owner.model.OwnerHaveSaleVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class OwnerSaleDeleteDao {
    private SqlSessionFactory sqlSessionFactory;
    private OwnerHaveSaleVO ownerHaveSaleVO;
    public OwnerSaleDeleteDao(OwnerHaveSaleVO ownerHaveSaleVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "myBatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            this.ownerHaveSaleVO = ownerHaveSaleVO;

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
    public ResponseEntity<String> ownerSaleDelete(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update("dao.mybatisMapper.changeOnOffSale",ownerHaveSaleVO.getSale_key());
            sqlSession.commit();
            return new ResponseEntity<String>("update successfully", HttpStatus.OK);
//            return sqlSession.selectList("dao.mybatisClientMapper.selectAll");
        }
        finally {
            sqlSession.close();
        }
    }
}

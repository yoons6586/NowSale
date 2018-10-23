package com.example.demo.owner.dao;

import com.example.demo.owner.model.OwnerRegisterCouponVO;
import com.example.demo.owner.model.OwnerRegisterSaleVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

public class OwnerSaleInsertDao {
    private SqlSessionFactory sqlSessionFactory;
    private OwnerRegisterSaleVO ownerRegisterSaleVO;
    public OwnerSaleInsertDao(OwnerRegisterSaleVO ownerRegisterSaleVO){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            this.ownerRegisterSaleVO=ownerRegisterSaleVO;

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
    public ResponseEntity<String> ownerSaleInsert(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.insert("dao.mybatisMapper.insertSaleList",ownerRegisterSaleVO);
            sqlSession.commit();
            return new ResponseEntity<String>("insert sale successfully", HttpStatus.OK);
//            return sqlSession.selectList("dao.mybatisClientMapper.selectAll");
        }
        finally {
            sqlSession.close();
        }
    }
}

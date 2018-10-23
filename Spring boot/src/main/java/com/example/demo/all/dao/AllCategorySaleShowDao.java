package com.example.demo.all.dao;

import com.example.demo.all.model.OwnerCouponShowVO;
import com.example.demo.all.model.OwnerSaleShowVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AllCategorySaleShowDao {
    private SqlSessionFactory sqlSessionFactory;

    private String category;
    public AllCategorySaleShowDao(String category){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "MybatisConfiguration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
//            this.clientHaveCouponOwnerInfoVO = clientHaveCouponOwnerInfoVO;
            this.category=category;
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
    public List<OwnerSaleShowVO> selectCategoryShow(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.selectList("dao.mybatisMapper.selectCategorySaleShow",category);

        }
        finally {
            sqlSession.close();
        }
    }
}

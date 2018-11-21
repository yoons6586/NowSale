package com.example.demo.all.dao;

import com.example.demo.all.model.MarketVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AllCategoryMarketShowDao {
    private SqlSessionFactory sqlSessionFactory;

    private String category;
    public AllCategoryMarketShowDao(String category){
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(
                    "myBatisConfiguration.xml");
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
    public List<MarketVO> selectCategoryMarketShow(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.selectList("dao.mybatisMapper.selectCategoryMarketShow",category);

        }
        finally {
            sqlSession.close();
        }
    }
}

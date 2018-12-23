package com.example.demo.all.dao;

import com.example.demo.all.model.OAuthLoginVO;
import com.example.demo.client.model.ClientVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class OAuthLoginDao {
    private SqlSessionFactory sqlSessionFactory;
    private ClientVO clientVO;
    public OAuthLoginDao(){
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

    public ClientVO selectOAuthLogin(OAuthLoginVO oAuthLoginVO){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.clientVO = new ClientVO();
        try {
            switch(oAuthLoginVO.getProvider_type()){
                case "kakao" :
                    this.clientVO = (ClientVO) sqlSession.selectOne("dao.mybatisMapper.selectClientOAuthKakaoLogin",oAuthLoginVO);
                    break;
                case "naver" :
                    this.clientVO = (ClientVO) sqlSession.selectOne("dao.mybatisMapper.selectClientOAuthNaverLogin",oAuthLoginVO);

                    break;
            }
            sqlSession.commit();
        }
        finally {
            sqlSession.close();
        }
        return this.clientVO;
    }

    public void insertOAuthSignUp(ClientVO clientVO){
        System.out.println("inserOAuthSignUp client_key : "+clientVO.getClient_key());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.clientVO = new ClientVO();
        try {
            sqlSession.insert("dao.mybatisMapper.clientSignup", clientVO);
            sqlSession.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlSession.close();
        }
    }
}

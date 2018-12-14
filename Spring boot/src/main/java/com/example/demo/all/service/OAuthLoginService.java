package com.example.demo.all.service;

import com.example.demo.all.dao.OAuthLoginDao;
import com.example.demo.all.model.OAuthLoginVO;
import com.example.demo.client.model.ClientVO;
import org.springframework.stereotype.Service;

@Service
public class OAuthLoginService {
	OAuthLoginDao oAuthLoginDao;
	ClientVO clientVO;
	public ClientVO oAuthLogin(OAuthLoginVO oAuthLoginVO){
		oAuthLoginDao = new OAuthLoginDao();
		this.clientVO = oAuthLoginDao.selectOAuthLogin(oAuthLoginVO);

		return this.clientVO;

	}

	public ClientVO oAuthSignUp(ClientVO clientVO){
		oAuthLoginDao = new OAuthLoginDao();
		System.out.println("oAuthSignUp client_key : "+clientVO.getClient_key());
		oAuthLoginDao.insertOAuthSignUp(clientVO);

		OAuthLoginVO oAuthLoginVO = new OAuthLoginVO();
		oAuthLoginVO.setId(clientVO.getId());
		oAuthLoginVO.setProvider_type(clientVO.getProvider_type());
		oAuthLoginVO.setRefresh_token(clientVO.getRefresh_token());

		this.clientVO = oAuthLoginDao.selectOAuthLogin(oAuthLoginVO);

		System.out.println("client_key : "+this.clientVO.getClient_key());


		return this.clientVO;

	}
}

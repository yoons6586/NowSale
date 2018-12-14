package com.example.demo.all.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.ResultHandler;

@Getter
@Setter
public class OAuthLoginVO{
	private String provider_type;
	private String id;
	private String refresh_token;
}

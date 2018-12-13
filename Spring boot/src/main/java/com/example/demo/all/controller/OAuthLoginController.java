package com.example.demo.all.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/all")
public class OAuthLoginController {

	@GetMapping("/login/naver")
	public String naverLogin(){
		return "naverLoginApi";
	}




}

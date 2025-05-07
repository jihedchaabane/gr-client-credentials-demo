package com.chj.gr.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateUserController {


	@RequestMapping("/validateUser")
	public Principal user(Principal user) {
		
		System.out.println("security.oauth2.client.resource.userInfoUri: http://localhost:8764/validateUser");
		return user;
	}
}

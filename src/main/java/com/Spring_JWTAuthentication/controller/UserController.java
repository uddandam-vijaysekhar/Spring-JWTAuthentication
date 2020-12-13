package com.Spring_JWTAuthentication.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class UserController {
	
	@GetMapping(value="/user")
	public String getUser() {
		return "Yes User Controller";
	}

}

package com.Spring_JWTAuthentication.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String userName) {
		if("vijaysekhar".equals(userName)) {
			return new User("vijaysekhar","$2a$10$pYW6Im5IBdiI/9o.OVSk7e6Rx23Gx9uFI8N1SShwrPbv54xoQr6yG", new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User Name Not Found");
		}
	}


}

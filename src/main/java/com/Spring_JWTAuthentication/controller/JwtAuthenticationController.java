package com.Spring_JWTAuthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Spring_JWTAuthentication.config.JwtTokenUtil;
import com.Spring_JWTAuthentication.model.JwtRequest;
import com.Spring_JWTAuthentication.model.JwtResponse;
import com.Spring_JWTAuthentication.service.JwtUserDetailsService;

@CrossOrigin
@RestController
public class JwtAuthenticationController  {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		authenticate(jwtRequest.getUserName(), jwtRequest.getPassword());
		
		final UserDetails userDetails =jwtUserDetailsService.loadUserByUsername(jwtRequest.getUserName());
		
		final String token =jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	
	public void authenticate(String userName, String password) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		}catch(DisabledException e) {
			throw new Exception("USER_DISABLED",e);
		}catch(BadCredentialsException e) {
			throw new Exception("BAD_CREDENTIALS",e);
		}
		
		
	}

}

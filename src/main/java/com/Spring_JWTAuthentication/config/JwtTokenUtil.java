package com.Spring_JWTAuthentication.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Value("${jwt.secret}")
	public String secret;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000;
	
	//Retreive User name from Token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
		
	}
	//Retreive Expiration Date from Token
	public Date getExpirationDateFromTokne(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	private <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
		
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	//For retrive any information from token we need seccret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//check if Token is Expired
	public Boolean isTokenExpired(String token) {
		final Date expiration =getExpirationDateFromTokne(token);
		return expiration.before(new Date());
	}
	
	//Generate Token For the User
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims = new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
		
		
	}
	
	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
}
	
	//Validate Token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName= getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
}

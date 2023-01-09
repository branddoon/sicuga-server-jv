package com.sicuga.sicugaserver.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication){
		String email = authentication.getName();
		Date actualDate = new Date();
		Date expiredDate = new Date(actualDate.getTime()+jwtExpirationInMs);
		String token = Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		return token;
	}
	
	public String getEmailByJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public String renewToken(String email){
		Date actualDate = new Date();
		Date expiredDate = new Date(actualDate.getTime()+jwtExpirationInMs);
		String token = Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		return token;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch(SignatureException ex) {
			throw new RuntimeException("JWT signature is not valid.");
		}catch(MalformedJwtException ex) {
			throw new RuntimeException("JWT token is not valid.");
		}catch(ExpiredJwtException ex) {
			throw new RuntimeException("JWT token has expired.");
		}catch(UnsupportedJwtException ex) {
			throw new RuntimeException("JWT token is not supported.");
		}catch(IllegalArgumentException ex) {
			throw new RuntimeException("JWT claims is empty.");
		}
	} 
}

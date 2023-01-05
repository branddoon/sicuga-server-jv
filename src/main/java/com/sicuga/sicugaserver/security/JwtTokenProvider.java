package com.sicuga.sicugaserver.security;

import com.sicuga.sicugaserver.exception.GlobalException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
			throw new GlobalException("JWT signature is not valid.",HttpStatus.BAD_REQUEST);
		}catch(MalformedJwtException ex) {
			throw new GlobalException("JWT token is not valid.",HttpStatus.BAD_REQUEST);
		}catch(ExpiredJwtException ex) {
			throw new GlobalException("JWT token has expired",HttpStatus.BAD_REQUEST);
		}catch(UnsupportedJwtException ex) {
			throw new GlobalException("JWT token is not supported.",HttpStatus.BAD_REQUEST);
		}catch(IllegalArgumentException ex) {
			throw new GlobalException("JWT claims is empty.",HttpStatus.BAD_REQUEST);
		}
	} 
}

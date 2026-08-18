package com.dileep.ecommerce.ms.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dileep.ecommerce.ms.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

//	private final String SECRET = "mysecretkeymysecretkeymysecretkey";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(UserEntity user) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration);
		return Jwts.builder().subject(user.getEmail()).claim("roleId", user.getRole().getId()).claim("role",user.getRole())
//								.claim("tokenVersion", user.getTokenVersion())
								.issuedAt(now).expiration(expiryDate).signWith(getSigningKey())
				.compact();
	}

//	private Key getSigningKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(secret);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
	
	 private SecretKey getSigningKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(secret);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    
    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
    }
}
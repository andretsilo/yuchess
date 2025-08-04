package com.yuchess.users.business.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {

	@Value("${yuchess.jwtsecret}")
	private String SECRET;

	@Value("${yuchess.expire}")
	private Long EXPIRETIME;

	public String generateToken(String username) {
		return JWT.create().withSubject(username).withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis())).sign(Algorithm.HMAC256(SECRET));
	}

	public String extractUsername(String token) {
		return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).getSubject();
	}

}

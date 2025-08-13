package com.yuchess.matchmaking.business.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableScheduling
public class BeanFactory {

	@Value("${yuchess.jwtsecret}")
	String secretKey;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/matchmaking/**").permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt());

		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(key).build();
	}

}

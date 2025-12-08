package com.yuchess.users.business.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yuchess.users.business.impl.CustomUserDetailsService;
import com.yuchess.users.business.security.JWTAuthenticationFilter;
import com.yuchess.users.business.security.JWTAuthorizationFilter;
import com.yuchess.users.business.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    @Value("${yuchess.jwt.secret}")
    private String secret;

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	AuthenticationManager authManager = authenticationManager(http);

	JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter(authManager, jwtUtil);
	JWTAuthorizationFilter authorizationFilter = new JWTAuthorizationFilter(authManager, jwtUtil,
		userDetailsService);

	return http.csrf(csrf -> csrf.disable())
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/login", "/api/auth/signup",
			"/swagger-ui/**", "/v3/api-docs/**", "/api/auth/user").permitAll().anyRequest().authenticated())
		.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults())).authenticationManager(authManager)
		.addFilter(authFilter).addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
		.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
	SecretKey key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	return NimbusJwtDecoder.withSecretKey(key).build();
    }

}

package com.yuchess.users.business.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuchess.users.business.util.JwtUtil;
import com.yuchess.users.server.dto.LoginDto;
import com.yuchess.users.server.response.JwtResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/api/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		try {
			LoginDto authRequest = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(), authRequest.getPassword());

			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException {

		String username = ((UserDetails) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);

		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), new JwtResponse(token));
	}

}

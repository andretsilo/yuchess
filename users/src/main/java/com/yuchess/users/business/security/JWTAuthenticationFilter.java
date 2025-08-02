package com.yuchess.users.business.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuchess.users.business.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	this.authenticationManager = authenticationManager;

	setFilterProcessesUrl("/api/services/controller/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
	    throws AuthenticationException {
	try {
	    User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);

	    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
		    creds.getPassword(), new ArrayList<>()));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
	    Authentication auth) throws IOException {
	String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
		.withExpiresAt(new Date(System.currentTimeMillis() /* TODO: get config time from properties */))
		.sign(Algorithm.HMAC512("hello" /* TODO: get secret from properties */));

	String body = ((User) auth.getPrincipal()).getUsername() + " " + token;

	res.getWriter().write(body);
	res.getWriter().flush();
    }

}

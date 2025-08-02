package com.yuchess.users.business.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
	super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
	    throws IOException, ServletException {
	String header = req.getHeader("hello" /* TODO: add token header from properties */);

	if (header == null || !header.startsWith(" " /* TODO: add token prexif */)) {
	    chain.doFilter(req, res);
	    return;
	}

	UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

	SecurityContextHolder.getContext().setAuthentication(authentication);
	chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate
    // the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	String token = request.getHeader(" " /* TODO: add token header */);

	if (token != null) {
	    // parse the token.
	    String user = JWT.require(Algorithm.HMAC512(" " /* TODO: add secret */)).build()
		    .verify(token.replace(" " /* TODO: add token header */, "")).getSubject();

	    if (user != null) {
		// new arraylist means authorities
		return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	    }

	    return null;
	}

	return null;
    }

}

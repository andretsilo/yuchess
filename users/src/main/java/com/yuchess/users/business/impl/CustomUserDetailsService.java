package com.yuchess.users.business.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuchess.users.business.entity.User;
import com.yuchess.users.business.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = repository.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found."));

	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
		Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
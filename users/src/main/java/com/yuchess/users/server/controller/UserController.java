package com.yuchess.users.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuchess.users.business.entity.User;

@RequestMapping("/auth")
@RestController
public class UserController {

	@PostMapping("/signup")
	public ResponseEntity<User> signUpUser() {
		return ResponseEntity.ok(new User());
	}

	@PostMapping("/login")
	public void loginUser() {

	}

}

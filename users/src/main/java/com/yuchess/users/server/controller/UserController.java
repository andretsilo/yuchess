package com.yuchess.users.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuchess.users.business.IUserService;
import com.yuchess.users.server.dto.UserDto;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	IUserService userSvc;

	@PostMapping("/signup")
	public ResponseEntity<String> signUpUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(userSvc.saveUser(user));
	}

}

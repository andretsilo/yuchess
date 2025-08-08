package com.yuchess.users.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuchess.users.business.IUserService;
import com.yuchess.users.server.dto.UserDto;
import com.yuchess.users.server.response.PlayerResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api")
@RestController
@Tag(name = "Authentication", description = "User login and signup operations")
public class UserController {

	@Autowired
	IUserService userSvc;

	@PostMapping("/signup")
	@Operation(summary = "Sign up user", description = "Creates a new user account", parameters = {
			@Parameter(name = "user", required = true) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User saved correctly"),
			@ApiResponse(responseCode = "40x", description = "Error while saving the user") })
	public ResponseEntity<String> signUpUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(userSvc.saveUser(user));
	}

	@GetMapping("/{username}")
	@Operation(summary = "Get user by username", description = "Returns a user given his username", parameters = {
			@Parameter(name = "username", required = true) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User retrieved correctly"),
			@ApiResponse(responseCode = "40x", description = "Error while retrieveing the user") })
	public ResponseEntity<PlayerResponse> getUser(@PathVariable String username) {
		return ResponseEntity.ok(userSvc.getUser(username));
	}

}

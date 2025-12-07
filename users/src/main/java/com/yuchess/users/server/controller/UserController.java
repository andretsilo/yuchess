package com.yuchess.users.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuchess.users.business.IUserService;
import com.yuchess.users.server.dto.UserDto;

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
	String out = userSvc.saveUser(user);
	if (out != "-1") {
	    return new ResponseEntity<String>(out, HttpStatus.CREATED);
	}
	return new ResponseEntity<String>("Item already exists", HttpStatus.CONFLICT);

    }

}

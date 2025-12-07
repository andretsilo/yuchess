package com.yuchess.matchmaking.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/matchmaking")
@RestController
public class MatchmakingController {

    @PostMapping("/join")
    public ResponseEntity<String> joinGame(@AuthenticationPrincipal Jwt jwt) {

	String username = "";

	if (jwt != null) {
	    username = jwt.getSubject();

	} else {

	}

	return ResponseEntity.ok(username);

    }

}

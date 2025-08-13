package com.yuchess.matchmaking.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuchess.matchmaking.business.IMatchmakingService;
import com.yuchess.matchmaking.business.enums.Mode;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/matchmaking")
@Slf4j
public class MatchmakingController {

	@Autowired
	IMatchmakingService svc;

	@PostMapping("/join/{mode}")
	public ResponseEntity<String> join(@AuthenticationPrincipal Jwt jwt, @RequestParam Mode mode) {

		if (jwt != null) {
			String username = jwt.getSubject();
			svc.joinQueue(username, mode);
		} else {

		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/status")
	public ResponseEntity<Void> status() {
		return ResponseEntity.ok().build();
	}

}

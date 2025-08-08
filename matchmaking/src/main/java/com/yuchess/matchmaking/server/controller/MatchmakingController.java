package com.yuchess.matchmaking.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matchmaking")
public class MatchmakingController {

	@PostMapping("/join")
	public ResponseEntity<Void> join() {
		return ResponseEntity.ok().build();
	}

	@GetMapping("/status")
	public ResponseEntity<Void> status() {
		return ResponseEntity.ok().build();
	}

}

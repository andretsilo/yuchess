package com.yuchess.matchmaking.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuchess.matchmaking.business.MatchmakingService;
import com.yuchess.matchmaking.server.dto.ModeDto;

@RequestMapping("/api/matchmaking")
@RestController
public class MatchmakingController {

    @Autowired
    MatchmakingService svc;

    @PostMapping("/join")
    public ResponseEntity<String> joinGame(@AuthenticationPrincipal Jwt jwt, @RequestBody ModeDto mode) {
	String username = jwt.getSubject();
	svc.joinQueue(jwt, mode.getMode());
	return ResponseEntity.ok().build();
    }

}

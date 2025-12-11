package com.yuchess.matchmaking.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import com.yuchess.matchmaking.business.MatchmakingService;
import com.yuchess.matchmaking.business.enums.Status;
import com.yuchess.matchmaking.server.dto.GameDto;
import com.yuchess.matchmaking.server.dto.ModeDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MatchmakingController {

    @Autowired
    MatchmakingService svc;

    @MessageMapping("/join")
    @SendTo("/topic/matchmaking")
    public GameDto joinGame(ModeDto dto, WebSocketSession session) {
	String playerId = (String) session.getAttributes().get("playerId");
	log.info("Message from player {}: {}", playerId, dto.toString());
	svc.joinQueue(dto.getToken(), dto.getMode());
	return new GameDto(Status.MATCH_FOUND, "me", "you");
    }

}

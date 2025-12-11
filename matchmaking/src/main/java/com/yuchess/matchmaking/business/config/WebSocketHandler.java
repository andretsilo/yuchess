package com.yuchess.matchmaking.business.config;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.yuchess.matchmaking.business.MatchmakingService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    private MatchmakingService service;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
	String playerId = UUID.randomUUID().toString();
	session.getAttributes().put("playerId", playerId);
	sessions.put(playerId, session);
	service.register(playerId, session);
	log.info("Player connected: {}", playerId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
	String playerId = (String) session.getAttributes().get("playerId");
	service.unregister(playerId);
    }
}

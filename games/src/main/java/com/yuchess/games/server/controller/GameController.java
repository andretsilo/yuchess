package com.yuchess.games.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.yuchess.games.business.GameService;
import com.yuchess.games.server.message.GameUpdateMessage;
import com.yuchess.games.server.message.MoveMessage;

@Controller
public class GameController {

    @Autowired
    GameService svc;

    @MessageMapping("/move")
    @SendTo("/topic/game")
    public GameUpdateMessage handleMove(MoveMessage moveMessage) {
	return svc.processMove(moveMessage);
    }
}

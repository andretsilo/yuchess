package com.yuchess.games.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.yuchess.games.business.GameService;
import com.yuchess.games.server.message.GameCreateMessage;
import com.yuchess.games.server.message.GameJoinMessage;
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

    @MessageMapping("/createGame")
    @SendTo("/topic/game")
    public GameUpdateMessage createGame(GameCreateMessage createMessage) {
	return svc.createGame(createMessage);
    }

    @MessageMapping("/joinGame")
    @SendTo("/topic/game")
    public GameUpdateMessage joinGame(GameJoinMessage joinMessage) {
	return svc.joinGame(joinMessage);
    }
}

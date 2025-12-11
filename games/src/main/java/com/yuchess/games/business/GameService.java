package com.yuchess.games.business;

import org.springframework.stereotype.Service;

import com.yuchess.games.server.message.GameUpdateMessage;
import com.yuchess.games.server.message.MoveMessage;

@Service
public class GameService {

    public GameUpdateMessage processMove(MoveMessage moveMessage) {
	return null;
    }
}

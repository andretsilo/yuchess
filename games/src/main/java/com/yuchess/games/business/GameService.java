package com.yuchess.games.business;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.yuchess.games.business.engine.Board;
import com.yuchess.games.business.engine.Game;
import com.yuchess.games.business.enums.GameStatus;
import com.yuchess.games.server.message.GameCreateMessage;
import com.yuchess.games.server.message.GameJoinMessage;
import com.yuchess.games.server.message.GameUpdateMessage;
import com.yuchess.games.server.message.MoveMessage;

@Service
public class GameService {

    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public GameUpdateMessage createGame(GameCreateMessage createMessage) {
	Game game = new Game(UUID.randomUUID().toString(), createMessage.getWhitePlayer(),
		createMessage.getBlackPlayer(), GameStatus.ONGOING, createMessage.getWhitePlayer(), Boolean.FALSE,
		Boolean.FALSE, new Board());
	games.put(game.getId(), game);
	return new GameUpdateMessage(game.getId(), new Board(), GameStatus.ONGOING, game.getWhitePlayer(), null);

    }

    public GameUpdateMessage joinGame(GameJoinMessage message) {
	String black = games.get(message.getGameId()).getBlackPlayer();
	String white = games.get(message.getGameId()).getWhitePlayer();

	if (message.getPlayerId().equals(white)) {
	    games.get(message.getGameId()).setWhiteJoined(Boolean.TRUE);
	} else if (message.getPlayerId().equals(black)) {
	    games.get(message.getGameId()).setBlackJoined(Boolean.TRUE);
	}

	return new GameUpdateMessage(message.getGameId(), games.get(message.getGameId()).getBoard(), GameStatus.ONGOING,
		black, white);
    }

    public GameUpdateMessage processMove(MoveMessage moveMessage) {
	Game game = games.get(moveMessage.getPlayerId());

	if (!moveMessage.getPlayerId().equals(game.getTurn())) {
	    throw new RuntimeException("Not your turn!");
	}

	return null;
    }

}

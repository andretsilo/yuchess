package com.yuchess.games.server.message;

import com.yuchess.games.business.engine.Board;
import com.yuchess.games.business.enums.GameStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameUpdateMessage {
    private String gameId;
    private Board board;
    private GameStatus status;
    private String currentTurn;
    private String winner;
}

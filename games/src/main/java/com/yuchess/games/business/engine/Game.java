package com.yuchess.games.business.engine;

import com.yuchess.games.business.enums.GameStatus;

import lombok.Data;

@Data
public class Game {
    private String id;
    private String whitePlayer;
    private String blackPlayer;
    private GameStatus status;
    private String turn;
}

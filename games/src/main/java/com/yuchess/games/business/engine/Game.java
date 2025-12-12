package com.yuchess.games.business.engine;

import com.yuchess.games.business.enums.GameStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Game {
    private String id;
    private String whitePlayer;
    private String blackPlayer;
    private GameStatus status;
    private String turn;
    private Boolean whiteJoined;
    private Boolean blackJoined;
    private Board board;
}

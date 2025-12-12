package com.yuchess.games.server.message;

import lombok.Data;

@Data
public class GameCreateMessage {
    private String whitePlayer;
    private String blackPlayer;
}

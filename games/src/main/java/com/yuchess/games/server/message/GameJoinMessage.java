package com.yuchess.games.server.message;

import lombok.Data;

@Data
public class GameJoinMessage {
    private String gameId;
    private String playerId;
}

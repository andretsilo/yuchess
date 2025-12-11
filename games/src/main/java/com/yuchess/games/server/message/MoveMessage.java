package com.yuchess.games.server.message;

import lombok.Data;

@Data
public class MoveMessage {
    private String gameId;
    private String playerId;
    private String from;
    private String to;
}

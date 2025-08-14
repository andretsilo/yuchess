package com.yuchess.matchmaking.server.dto;

import java.io.Serializable;

import com.yuchess.matchmaking.business.enums.Mode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameRequest implements Serializable {

    private static final long serialVersionUID = 6699834713118923666L;

    String gameId;
    PlayerDto playerOne;
    PlayerDto playerTwo;
    Mode mode;
    String white;
}

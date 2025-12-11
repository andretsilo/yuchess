package com.yuchess.matchmaking.server.dto;

import com.yuchess.matchmaking.business.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private Status gameStatus;
    private String black;
    private String white;
}

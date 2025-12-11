package com.yuchess.matchmaking.server.dto;

import com.yuchess.matchmaking.business.enums.QueueType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchmakingDto {
    private UserDto black;
    private UserDto white;
    private QueueType mode;
}

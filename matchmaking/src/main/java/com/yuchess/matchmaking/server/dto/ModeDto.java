package com.yuchess.matchmaking.server.dto;

import com.yuchess.matchmaking.business.enums.QueueType;

import lombok.Data;

@Data
public class ModeDto {
    QueueType mode;
}

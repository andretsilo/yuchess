package com.yuchess.matchmaking.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueueType {
    BULLET("Bullet"), RAPID("Rapid"), CLASSIC("Classic");

    private String type;
}

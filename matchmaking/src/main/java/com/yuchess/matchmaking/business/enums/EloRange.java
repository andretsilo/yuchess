package com.yuchess.matchmaking.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EloRange {
    LOW(800), MID(1400), HIGH(2000);

    @Getter
    Integer range;
}

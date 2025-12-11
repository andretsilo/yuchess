package com.yuchess.matchmaking.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    WAITING("Waiting"), MATCH_FOUND("Match found");

    String status;
}

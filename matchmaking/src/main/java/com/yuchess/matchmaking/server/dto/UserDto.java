package com.yuchess.matchmaking.server.dto;

import java.io.Serializable;

import com.yuchess.matchmaking.business.enums.Country;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -5227848248779137280L;

    private Long id;
    private String username;
    private String password;
    private Country country;
    private Long eloBullet;
    private Long eloRapid;
    private Long eloClassic;

}

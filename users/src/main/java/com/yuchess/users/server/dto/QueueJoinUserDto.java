package com.yuchess.users.server.dto;

import lombok.Data;

@Data
public class QueueJoinUserDto {

    private Long id;
    private String username;
    private String password;
    private String country;
    private Long eloBullet;
    private Long eloRapid;
    private Long eloClassic;

}

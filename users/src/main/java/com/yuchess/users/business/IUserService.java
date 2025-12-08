package com.yuchess.users.business;

import com.yuchess.users.server.dto.QueueJoinUserDto;
import com.yuchess.users.server.dto.UserDto;

public interface IUserService {
    String saveUser(UserDto dto);

    QueueJoinUserDto getUser(String username);
}

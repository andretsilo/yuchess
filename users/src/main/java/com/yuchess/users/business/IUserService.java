package com.yuchess.users.business;

import com.yuchess.users.server.dto.UserDto;

public interface IUserService {
    String saveUser(UserDto dto);
}

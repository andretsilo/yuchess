package com.yuchess.users.business;

import com.yuchess.users.server.dto.UserDto;
import com.yuchess.users.server.response.PlayerResponse;

public interface IUserService {
	String saveUser(UserDto dto);

	PlayerResponse getUser(String username);
}

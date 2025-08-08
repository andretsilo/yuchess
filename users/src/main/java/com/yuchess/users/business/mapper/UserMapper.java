package com.yuchess.users.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yuchess.users.business.entity.User;
import com.yuchess.users.server.dto.UserDto;
import com.yuchess.users.server.response.PlayerResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "eloBullet", constant = "800L")
	@Mapping(target = "eloRapid", constant = "800L")
	@Mapping(target = "eloClassic", constant = "800L")
	User toEntity(UserDto source);

	PlayerResponse toResponse(User user);

}

package com.yuchess.users.business.mapper;

import org.mapstruct.Mapper;

import com.yuchess.users.business.entity.User;
import com.yuchess.users.server.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    User toEntity(UserDto source);

}

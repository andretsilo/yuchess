package com.yuchess.users.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yuchess.users.business.IUserService;
import com.yuchess.users.business.mapper.UserMapper;
import com.yuchess.users.business.repository.IUserRepository;
import com.yuchess.users.server.dto.UserDto;

import jakarta.transaction.Transactional;

public class UserServiceImpl implements IUserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserMapper mapper;

    @Autowired
    IUserRepository repository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String saveUser(UserDto dto) {
	dto.setPassword(passwordEncoder.encode(dto.getPassword()));
	return repository.save(mapper.toEntity(dto)).getId().toString();
    }

}

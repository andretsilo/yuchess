package com.yuchess.users.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuchess.users.business.IUserService;
import com.yuchess.users.business.mapper.UserMapper;
import com.yuchess.users.business.repository.UserRepository;
import com.yuchess.users.server.dto.QueueJoinUserDto;
import com.yuchess.users.server.dto.UserDto;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserMapper mapper;

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public String saveUser(UserDto dto) {
	dto.setPassword(passwordEncoder.encode(dto.getPassword()));
	log.info("Created entity: {}", mapper.toEntity(dto).getId().toString());
	return repository.save(mapper.toEntity(dto)).getId().toString();
    }

    @Override
    public QueueJoinUserDto getUser(String username) {
	return mapper.toDto(repository.findByUsername(username).get());
    }

}

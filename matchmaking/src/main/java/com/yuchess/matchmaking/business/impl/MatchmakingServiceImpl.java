package com.yuchess.matchmaking.business.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuchess.matchmaking.business.IMatchmakingService;
import com.yuchess.matchmaking.server.dto.PlayerDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchmakingServiceImpl implements IMatchmakingService {

	@Value("${yuchess.users.url}")
	String BASE_URL;

	@Override
	public Long joinQueue(String username) {
		RestTemplate restTemplate = new RestTemplate();
		PlayerDto dto = restTemplate.getForObject(BASE_URL.concat("/").concat(username), PlayerDto.class);
		log.info("retrieved: {}", dto.toString());

		return null;
	}

}

package com.yuchess.matchmaking.business;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuchess.matchmaking.business.enums.EloRange;
import com.yuchess.matchmaking.business.enums.QueueType;
import com.yuchess.matchmaking.server.dto.UserDto;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchmakingService {

    @Value("${yuchess.uri.users}")
    private String AUTH_SERVICE_URI;

    private ConcurrentHashMap<QueueType, ConcurrentHashMap<EloRange, ConcurrentLinkedQueue<UserDto>>> queues;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init() {
	queues = new ConcurrentHashMap<>();
	for (QueueType type : QueueType.values()) {
	    queues.put(type, new ConcurrentHashMap<>());
	    Arrays.stream(EloRange.values())
		    .forEach(range -> queues.get(type).put(range, new ConcurrentLinkedQueue<>()));
	}
    }

    public void joinQueue(Jwt jwt, QueueType mode) {
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Bearer " + jwt.getTokenValue());
	HttpEntity<String> entity = new HttpEntity<String>(headers);
	ResponseEntity<UserDto> userResponse = restTemplate.exchange(AUTH_SERVICE_URI, HttpMethod.GET, entity,
		UserDto.class);
	log.info("Response from {}: {}", AUTH_SERVICE_URI, userResponse);
	Long elo = null;

	if (userResponse.getStatusCode() == HttpStatus.OK) {
	    UserDto user = userResponse.getBody();

	    switch (mode) {
	    case BULLET:
		elo = user.getEloBullet();
		break;
	    case RAPID:
		elo = user.getEloRapid();
		break;
	    case CLASSIC:
		elo = user.getEloClassic();
		break;
	    default:
		throw new IllegalArgumentException("Unexpected value: " + mode);
	    }

	    if (elo <= EloRange.LOW.getRange()) {
		queues.get(mode).get(EloRange.LOW).add(user);
		log.info("User: {} joined low elo queue", user.getUsername());
	    } else if (elo <= EloRange.MID.getRange()) {
		queues.get(mode).get(EloRange.MID).add(user);
		log.info("User: {} joined mid elo queue", user.getUsername());
	    } else {
		queues.get(mode).get(EloRange.HIGH).add(user);
		log.info("User: {} joined high elo queue", user.getUsername());
	    }
	} else {
	    log.info("Request to {} failed with code: {}", AUTH_SERVICE_URI, userResponse.getStatusCode());
	}
    }

}

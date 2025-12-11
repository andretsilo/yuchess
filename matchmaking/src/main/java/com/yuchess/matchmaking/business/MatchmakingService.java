package com.yuchess.matchmaking.business;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.yuchess.matchmaking.business.enums.EloRange;
import com.yuchess.matchmaking.business.enums.QueueType;
import com.yuchess.matchmaking.business.enums.Status;
import com.yuchess.matchmaking.server.dto.GameDto;
import com.yuchess.matchmaking.server.dto.UserDto;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchmakingService {

    @Value("${yuchess.uri.users}")
    private String AUTH_SERVICE_URI;

    private ConcurrentHashMap<QueueType, ConcurrentHashMap<EloRange, ConcurrentLinkedQueue<UserDto>>> queues;

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void register(String playerId, WebSocketSession session) {
	sessions.put(playerId, session);
    }

    public void unregister(String playerId) {
	sessions.remove(playerId);
    }

    public WebSocketSession getSession(String playerId) {
	return sessions.get(playerId);
    }

    public boolean isConnected(String playerId) {
	return sessions.containsKey(playerId);
    }

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

    public GameDto joinQueue(String jwt, QueueType mode) {
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", "Bearer " + jwt);
	HttpEntity<String> entity = new HttpEntity<String>(headers);
	ResponseEntity<UserDto> userResponse = restTemplate.exchange(AUTH_SERVICE_URI, HttpMethod.GET, entity,
		UserDto.class);
	log.info("Response from {}: {}", AUTH_SERVICE_URI, userResponse);
	GameDto game = new GameDto();
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
		game = queueJoin(mode, EloRange.LOW, user);
		log.info("User: {} joined low elo queue", user.getUsername());
	    } else if (elo <= EloRange.MID.getRange()) {
		game = queueJoin(mode, EloRange.MID, user);
		log.info("User: {} joined mid elo queue", user.getUsername());
	    } else {
		game = queueJoin(mode, EloRange.HIGH, user);
		log.info("User: {} joined high elo queue", user.getUsername());
	    }

	} else {
	    log.info("Request to {} failed with code: {}", AUTH_SERVICE_URI, userResponse.getStatusCode());
	}

	return game;
    }

    private GameDto queueJoin(QueueType mode, EloRange range, UserDto user) {
	ConcurrentLinkedQueue<UserDto> queue = queues.get(mode).get(range);
	GameDto tempDto = new GameDto();
	UserDto opponent = queue.poll();

	if (opponent == null) {
	    queue.add(user);
	} else {
	    tempDto.setGameStatus(Status.MATCH_FOUND);
	    tempDto.setBlack(user.getUsername());
	    tempDto.setWhite(opponent.getUsername());
	}
	return tempDto;
    }

    @Scheduled(fixedRate = 1000)
    public void matchmakingLoop() {
	queues.keySet().stream().forEach(type -> {
	    queues.get(type).keySet().stream().forEach(range -> {
		ConcurrentLinkedQueue<UserDto> queue = queues.get(type).get(range);
		while (queue.size() >= 2) {
		    UserDto black = queue.poll();
		    UserDto white = queue.poll();
		    GameDto gameDto = new GameDto(Status.MATCH_FOUND, black.getUsername(), white.getUsername());
		    try {
			sessions.get(black.getUsername()).sendMessage(new TextMessage(gameDto.toString()));
		    } catch (IOException e) {
			log.info("Error: {}", e.getMessage());
		    }
		}
	    });
	});
    }

}

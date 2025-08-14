package com.yuchess.matchmaking.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuchess.matchmaking.business.IMatchmakingService;
import com.yuchess.matchmaking.business.enums.EloRange;
import com.yuchess.matchmaking.business.enums.Mode;
import com.yuchess.matchmaking.server.dto.GameRequest;
import com.yuchess.matchmaking.server.dto.PlayerDto;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchmakingServiceImpl implements IMatchmakingService {
    /*
     * 1. Create thread to join queue (producer) 2. Create thread to poll queue 3.
     * Create request to ws server
     */

    @Value("${yuchess.users.url}")
    String BASE_URL;

    Map<EloRange, Queue<PlayerDto>> queuesMap = new ConcurrentHashMap<EloRange, Queue<PlayerDto>>();

    @PostConstruct
    public void init() {
	Arrays.stream(EloRange.values()).forEach(o -> queuesMap.put(o, new ConcurrentLinkedQueue<PlayerDto>()));
    }

    @Override
    public void joinQueue(String username, Mode mode) {
	RestTemplate restTemplate = new RestTemplate();
	PlayerDto dto = restTemplate.getForObject(BASE_URL.concat("/").concat(username), PlayerDto.class);
	dto.setMode(mode);
	log.info("retrieved: {}", dto.toString());

	Thread thread = new Thread(getJoinQueueThread(dto, mode));
	thread.start();
	log.info("Added player in matching queue: {}", dto.getUsername());
    }

    @Scheduled(fixedRate = 1000)
    public void attemptMatchmaking() {

	List<GameRequest> gameRequests = new ArrayList<GameRequest>();

	Arrays.stream(EloRange.values()).forEach(range -> {
	    PlayerDto playerOne = queuesMap.get(range).poll();
	    PlayerDto playerTwo = queuesMap.get(range).stream().filter(o -> playerOne.getMode().equals(o.getMode()))
		    .findFirst().orElse(null);
	    GameRequest gameRequest = new GameRequest(UUID.randomUUID().toString(), playerOne, playerTwo,
		    playerOne.getMode(), playerOne.getUsername());
	    gameRequests.add(gameRequest);
	});

	gameRequests.stream().filter(o -> o.getPlayerOne() != null && o.getPlayerTwo() != null)
		.collect(Collectors.toList()).forEach(request -> {
		    // TODO: send ws request
		});
	;

    }

    private Runnable getJoinQueueThread(PlayerDto player, Mode mode) {
	Queue<PlayerDto> targetQueue = queuesMap.get(getPlayerEloRange(player, mode));
	Runnable producer = () -> {
	    if (!targetQueue.contains(player))
		targetQueue.offer(player);
	};

	return producer;
    }

    private EloRange getPlayerEloRange(PlayerDto player, Mode mode) {

	Long elo;

	switch (mode) {
	case BULLET:
	    elo = player.getEloBullet();
	    break;
	case RAPID:
	    elo = player.getEloRapid();
	    break;
	case CLASSIC:
	    elo = player.getEloClassic();
	    break;
	default:
	    elo = 0L;
	}

	return computeEloRange(elo);

    }

    private EloRange computeEloRange(Long elo) {

	if (elo < 1000)
	    return EloRange.LOW;
	if (elo < 2000)
	    return EloRange.MID;
	return EloRange.HIGH;

    }

}

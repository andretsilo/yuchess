package com.yuchess.matchmaking.business;

import com.yuchess.matchmaking.business.enums.Mode;

public interface IMatchmakingService {
	public void joinQueue(String username, Mode mode);
}

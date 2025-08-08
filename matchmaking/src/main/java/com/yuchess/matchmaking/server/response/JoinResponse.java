package com.yuchess.matchmaking.server.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 286542354024961053L;

	Long position;

}

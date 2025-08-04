package com.yuchess.users.server.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3370856023292337334L;

	String token;
}

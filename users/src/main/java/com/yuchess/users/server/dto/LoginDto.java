package com.yuchess.users.server.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5338453337538031910L;

	String username;
	String password;

}

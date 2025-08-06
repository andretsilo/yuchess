package com.yuchess.users.server.dto;

import java.io.Serializable;

import com.yuchess.users.business.enums.Country;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {

	private static final long serialVersionUID = 6091436545899858821L;

	String username;
	String password;
	Country country;

}

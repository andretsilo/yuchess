package com.yuchess.users.server.response;

import java.io.Serializable;

import com.yuchess.users.business.enums.Country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponse implements Serializable {

	private static final long serialVersionUID = 3998400062454675733L;

	String username;
	Country country;
	Long eloBullet;
	Long eloRapid;
	Long eloClassic;

}

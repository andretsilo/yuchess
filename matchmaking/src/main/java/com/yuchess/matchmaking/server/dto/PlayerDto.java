package com.yuchess.matchmaking.server.dto;

import java.io.Serializable;

import com.yuchess.matchmaking.business.enums.Country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604797131356432550L;

	String username;
	Country country;
	Long eloBullet;
	Long eloRapid;
	Long eloClassic;

}

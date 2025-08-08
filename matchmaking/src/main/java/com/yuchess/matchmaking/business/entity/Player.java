package com.yuchess.matchmaking.business.entity;

import java.io.Serializable;

import com.yuchess.matchmaking.business.enums.Country;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "USERS")
@Entity
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4531122780156322583L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Country country;

	@Column(nullable = false, name = "ELO_BULLET")
	private Long eloBullet;

	@Column(nullable = false, name = "ELO_RAPID")
	private Long eloRapid;

	@Column(nullable = false, name = "ELO_CLASSIC")
	private Long eloClassic;

}

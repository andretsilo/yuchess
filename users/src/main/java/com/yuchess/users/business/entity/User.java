package com.yuchess.users.business.entity;

import java.io.Serializable;

import com.yuchess.users.business.enums.Country;

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
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7709256387252608539L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, name = "USER_NAME", unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = false, name = "ELO_BULLET")
    private Long eloBullet;

    @Column(nullable = false, name = "ELO_RAPID")
    private Long eloRapid;

    @Column(nullable = false, name = "ELO_CLASSIC")
    private Long eloClassic;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getCountry() {
	return country.name();
    }

    public void setCountry(Country country) {
	this.country = country;
    }

    public Long getEloBullet() {
	return eloBullet;
    }

    public void setEloBullet(Long eloBullet) {
	this.eloBullet = eloBullet;
    }

    public Long getEloRapid() {
	return eloRapid;
    }

    public void setEloRapid(Long eloRapid) {
	this.eloRapid = eloRapid;
    }

    public Long getEloClassic() {
	return eloClassic;
    }

    public void setEloClassic(Long eloClassic) {
	this.eloClassic = eloClassic;
    }

}

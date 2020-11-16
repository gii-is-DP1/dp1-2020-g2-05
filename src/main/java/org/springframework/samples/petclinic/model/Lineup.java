package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "lineup")
public class Lineup extends BaseEntity {

    @Enumerated(EnumType.STRING)
	@Column(name = "category")
	@NotNull
	private Category category;

	@ManyToOne
	@JoinColumn(name = "recruit1_id")
	@NotNull
	private Recruit recruit1;

	@ManyToOne
	@JoinColumn(name = "recruit2_id")
	@NotNull
	private Recruit recruit2;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	@NotNull
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "league_id")
	@NotNull
	private League league;
	
	@ManyToOne
	@JoinColumn(name = "gp_id")
	@NotNull
	private GranPremio gp;

	public Category getCategory() {
		return category;
	} 

	public void setCategory(Category category) {
		this.category = category;
	}

	public Recruit getRecruit1() {
		return recruit1;
	}

	public void setRecruit1(Recruit recruit1) {
		this.recruit1 = recruit1;
	}

	public Recruit getRecruit2() {
		return recruit2;
	}

	public void setRecruit2(Recruit recruit2) {
		this.recruit2 = recruit2;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public GranPremio getGp() {
		return gp;
	}

	public void setGp(GranPremio gp) {
		this.gp = gp;
	}

	@Override
	public String toString() {
		return "Lineup [category=" + category + ", recruit1=" + recruit1 + ", recruit2=" + recruit2 + ", team=" + team
				+ ", league=" + league + ", gp=" + gp + "]";
	}
}

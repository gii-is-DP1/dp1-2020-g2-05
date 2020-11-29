package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "team")
public class Team extends BaseEntity implements Comparable<Team> {
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	
	@Column(name = "points")
	@NotEmpty
	private String points;
	
	@Column(name = "money")
	@NotEmpty
	private String money;
	
	@ManyToOne()
	@JoinColumn(name = "league_id")
	private League league;
	
	@ManyToOne()
	@JoinColumn(name = "username")
	private User user;
	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Lineup> lineups;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Offer> offer;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Recruit> recruit;
	
	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}
	
	@Override
	public String toString() {
		return "Team [name =" + name + ", points=" + points + ", money=" + money + " username =" + user + "]";
	}

	@Override
	public int compareTo(Team o) {
		return Integer.valueOf(this.getPoints()).compareTo(Integer.valueOf(o.getPoints()));
	}
	

}

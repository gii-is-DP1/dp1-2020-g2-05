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
public class Team extends NamedEntity {
	
	@Column(name = "points")
	@NotEmpty
	private String points;
	
	@Column(name = "money")
	@NotEmpty
	private String money;
	
	@ManyToOne()
	@JoinColumn(name = "League_id")
	private League league;

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
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
		return "Team [points=" + points + ", money=" + money + "]";
	}
	

}

package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.util.Status;

@Entity
@Table(name = "offer")
public class Offer extends BaseEntity{

	@Column(name = "price")
	@NotNull
	@Min(0)
	private Integer price;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "pilot_id")
	private Pilot pilot;
	
	@ManyToOne()
	@JoinColumn(name = "recruit_id")
	private Recruit recruit;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Recruit getRecruit() {
		return recruit;
	}

	public void setRecruit(Recruit recruit) {
		this.recruit = recruit;
	}
	
}

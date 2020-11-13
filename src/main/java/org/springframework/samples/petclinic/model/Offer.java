package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.util.Status;

@Entity
@Table(name = "offer")
public class Offer extends BaseEntity{

	@Column(name = "price")
	@NotEmpty
	private String price;
	
	@Column(name = "status")
	private Status status;
	
//	@ManyToOne
//	@JoinColumn(name = "team_id")
//	private Team team;
//	
	@ManyToOne
	@JoinColumn(name = "pilot_id")
	private Pilot pilot;

//	public Team getTeam() {
//		return team;
//	}
//
//	public void setTeam(Team team) {
//		this.team = team;
//	}
//
	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}

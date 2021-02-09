package org.springframework.samples.dreamgp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "offer")
public class Offer extends BaseEntity {

	@Column(name = "price")
	@NotNull
	@Min(0)
	private Integer price;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status")
	private Status status;
	
	@Version
	private Integer version;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne()
	@JoinColumn(name = "recruit_id")
	private Recruit recruit;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

	@Override
	public String toString() {
		return "Offer [price=" + price + ", status=" + status + ", team=" + team + ", recruit=" + recruit + "]";
	}

}

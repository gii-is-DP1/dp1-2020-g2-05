package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "recruit")
public class Recruit extends BaseEntity {

	@NotNull
	@Column(name = "for_sale")
	private Boolean forSale;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recruit1")
	private Set<Lineup> lineup1;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recruit2")
	private Set<Lineup> lineup2;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recruit")
	private Set<Offer> offer;

	@ManyToOne
	@JoinColumn(name = "pilot_id")
	private Pilot pilot;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	public Boolean getForSale() {
		return forSale;
	}

	public void setForSale(Boolean forSale) {
		this.forSale = forSale;
	}

	public Set<Lineup> getLineup1() {
		return lineup1;
	}

	public void setLineup1(Set<Lineup> lineup1) {
		this.lineup1 = lineup1;
	}

	public Set<Lineup> getLineup2() {
		return lineup2;
	}

	public void setLineup2(Set<Lineup> lineup2) {
		this.lineup2 = lineup2;
	}

	public Set<Offer> getOffer() {
		return offer;
	}

	public void setOffer(Set<Offer> offer) {
		this.offer = offer;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Recruit ["
//				+ "lineup1=" + lineup1 + ", lineup2=" + lineup2 + ","
//				+ " offer=" + offer + ", "
				+ "pilot=" + pilot
				+ ", team=" + team + "]";
	}

}

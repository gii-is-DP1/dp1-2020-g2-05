package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "result")
public class Result extends BaseEntity {
	
	@Column(name = "position")
	@NotEmpty
	private Integer position;


	@Column(name = "pole")
	@NotEmpty
	private Integer pole; //0-> false , 1-> true
	
	@Column(name = "fastestLapOnRace")
	@NotEmpty
	private Integer fastestLapOnRace; //0-> false , 1-> true
	
	
	
	@ManyToOne
	@JoinColumn(name = "pilot_id")
	private Pilot pilot;
	
	@ManyToOne
	@JoinColumn(name = "gp_id")
	private GP gp;

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPole() {
		return pole;
	}

	public void setPole(Integer pole) {
		this.pole = pole;
	}

	public Integer getFastestLapOnRace() {
		return fastestLapOnRace;
	}

	public void setFastestLapOnRace(Integer fastestLapOnRace) {
		this.fastestLapOnRace = fastestLapOnRace;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public GP getGp() {
		return gp;
	}

	public void setGp(GP gp) {
		this.gp = gp;
	}

	
}

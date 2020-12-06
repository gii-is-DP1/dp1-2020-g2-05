package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "results")
public class Result extends BaseEntity implements Comparable<Result> {
	
	@Column(name = "position")
	@NotNull
	private Integer position;


	@Column(name = "pole")
	@NotNull
	private Boolean pole; //0-> false , 1-> true
	
	@Column(name = "lap")
	@NotNull
	private Boolean lap; //0-> false , 1-> true
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pilot_id")
	private Pilot pilot;
	
	
	@ManyToOne
	@JoinColumn(name = "granpremio_id")
	private GranPremio gp;

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getPole() {
		return pole;
	}

	public void setPole(Boolean pole) {
		this.pole = pole;
	}

	public Boolean getLap() {
		return lap;
	}

	public void setLap(Boolean fastestLapOnRace) {
		this.lap = fastestLapOnRace;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public GranPremio getGp() {
		return gp;
	}

	public void setGp(GranPremio gp) {
		this.gp = gp;
	}

	@Override
	public String toString() {
		return "[Position=" + position +  ", Location=" + gp.getSite() + ", Rider=" + pilot.getName() + " " + pilot.getLastName()  + ", Pole=" + pole + "]";
	}

	@Override
	public int compareTo(Result o) {
		if (!(gp==null) && !(gp.getDate0()==null) && !this.gp.getDate0().equalsIgnoreCase(o.gp.getDate0()))
			return this.gp.getDate0().compareTo(o.gp.getDate0());
		if (!(gp==null) && !(gp.getSite()==null) && !this.gp.getSite().equalsIgnoreCase(o.gp.getSite()))
			return this.gp.getSite().compareTo(o.gp.getSite());
		if (!this.position.equals(o.position))
			return this.position.compareTo(o.position);
		return this.pilot.compareTo(o.pilot);
	}
	
}

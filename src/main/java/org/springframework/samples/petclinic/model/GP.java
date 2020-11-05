package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import motogpAPI.RaceCode;



@Entity
@Table(name = "gp")
public class GP extends BaseEntity {

	@Column(name = "site")
	@NotEmpty
	private String site;
	
	@Column(name = "date")
	@NotEmpty
	private String date; // formato de la fecha dd-mm-yyyy
	
	@Column(name = "circuit")
	@NotEmpty
	private String circuit;
	
	@Column(name = "raceCode")
	@NotEmpty
	private RaceCode raceCode;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="gp")
	Set<Result> results;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCircuit() {
		return circuit;
	}

	public void setCircuit(String circuit) {
		this.circuit = circuit;
	}

	public RaceCode getRaceCode() {
		return raceCode;
	}

	public void setRaceCode(RaceCode raceCode) {
		this.raceCode = raceCode;
	}

	public Set<Result> getResults() {
		return results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}
	
	


}

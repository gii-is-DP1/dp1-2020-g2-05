package org.springframework.samples.petclinic.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;

import motogpAPI.Pais;
import motogpAPI.RaceCode;

@Entity
@Table(name = "granpremio")
public class GranPremio extends BaseEntity {

	@Column(name = "site")
	@NotEmpty
	private String site;
	
	@Column(name = "date0")
	@NotEmpty
	private String date0; // formato de la fecha dd-MM-yyyy
	
	@Column(name = "circuit")
	@NotEmpty
	private String circuit;
	
	@Column(name = "racecode")
	@NotEmpty
	private String raceCode;
	
	@Column(name = "has_been_run")
	private Boolean hasBeenRun;
	
	@Column(name = "calendar")
	private Boolean calendar;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id")
	private Record record;
	
	
	public Boolean getCalendar() {
		return calendar;
	}

	public void setCalendar(Boolean calendar) {
		this.calendar = calendar;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy="gp")
	private Set<Result> results;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gp")
	private Set<Lineup> lineups;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	public void setHasBeenRun(Boolean hasBeenRun) {
		this.hasBeenRun = hasBeenRun;
	}
	
	public Boolean getHasBeenRun() {
		return hasBeenRun;
	}
	
	public String getDate0() {
		return date0;
	}

	public void setDate0(String date0) {
		this.date0 = date0;
	}

	public String getCircuit() {
		return circuit;
	}

	public void setCircuit(String circuit) {
		this.circuit = circuit;
	}

	public String getRaceCode() {
		return raceCode;
	}

	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}

	public Set<Result> getResults() {
		return results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}
	
	public Set<Lineup> getLineups() {
		return lineups;
	}

	public void setLineups(Set<Lineup> lineups) {
		this.lineups = lineups;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}


	@Override
	public String toString() {
		return "GranPremio [site=" + site + ", date0=" + date0 + ", circuit=" + circuit + ", raceCode=" + raceCode
				+ ", results=" + results + "]";
	}
}

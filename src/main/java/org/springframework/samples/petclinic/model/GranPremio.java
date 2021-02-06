package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "granpremio")
public class GranPremio extends BaseEntity {

	@Column(name = "site")
	@NotEmpty
    @Size(min = 3, max = 50)
	private String site;
	
	@Column(name = "date0")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private LocalDate date0;
	
	@Column(name = "circuit")
	@NotEmpty
    @Size(min = 3, max = 50)
	private String circuit;
	
	@Column(name = "imagen")
	private String imagen;
	
	@Column(name = "racecode")
	@NotEmpty
    @Size(min = 3, max = 3)
	private String raceCode;
	
	@Column(name = "has_been_run") 
	private Boolean hasBeenRun;
	
	@Column(name = "calendar")
	private Boolean calendar;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recordMotoGp_id")
	private Record recordMotoGP;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recordMoto2_id")
	private Record recordMoto2;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recordMoto3_id")
	private Record recordMoto3;
	
	
    
    
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Boolean getCalendar() {
		return calendar;
	}

	public void setCalendar(Boolean calendar) {
		this.calendar = calendar;
	}
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="gp")
	private Set<Result> results;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
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
	
	public LocalDate getDate0() {
		return date0;
	}

	public void setDate0(LocalDate date0) {
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

	public Record getRecordMotoGP() {
		return recordMotoGP;
	}

	public void setRecordMotoGP(Record recordMotoGP) {
		this.recordMotoGP = recordMotoGP;
	}

	public Record getRecordMoto2() {
		return recordMoto2;
	}

	public void setRecordMoto2(Record recordMoto2) {
		this.recordMoto2 = recordMoto2;
	}

	public Record getRecordMoto3() {
		return recordMoto3;
	}

	public void setRecordMoto3(Record recordMoto3) {
		this.recordMoto3 = recordMoto3;
	}
	
	public List<String> getPilotsWithRecords() {
		Record recordGP = this.getRecordMotoGP();
		Record record2 = this.getRecordMoto2();
		Record record3 = this.getRecordMoto3();
		
		List<String> res = new ArrayList<String>();
		
		res.addAll(recordGP.getPilotsWithRecords());
		res.addAll(record2.getPilotsWithRecords());
		res.addAll(record3.getPilotsWithRecords());
		
		return res;
	}

	@Override
	public String toString() {
		return "GranPremio [site=" + site + ", date0=" + date0 + ", circuit=" + circuit + ", raceCode=" + raceCode
				+ "]";
	}

	public Record getRecord(Category category) {
		Record res = new Record();
		switch (category) {
		case MOTOGP:
			 res = this.getRecordMotoGP();
			 break;
		case MOTO2:
			 res = this.getRecordMoto2();
			 break;
		case MOTO3:
			 res = this.getRecordMoto3();
			 break;
		}
		return res;
	}
}

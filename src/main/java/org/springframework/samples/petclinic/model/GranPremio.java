package org.springframework.samples.petclinic.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import motogpAPI.Pais;
import motogpAPI.RaceCode;
import motogpAPI.Record;

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
	
	@Column(name = "racecode")
	@NotEmpty
    @Size(min = 3, max = 3)
	private String raceCode;
	
	
	@Column(name = "has_been_run")
	private Boolean hasBeenRun;
	
	@Column(name = "calendar")
	private Boolean calendar;
	
	
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


//	public Pais parseRaceCodeToPais(RaceCode raceCode) {
//		Pais pais = Pais.NOTFOUND;
//
//		switch (raceCode) {
//		case ESP:
//			pais = Pais.SPA;
//			break;
//		case DEU:
//			pais = Pais.GER;
//			break;
//		case IND:
//			pais = Pais.INP;
//			break;
//		case PRT:
//			pais = Pais.POR;
//			break;
//		case SMR:
//			pais = Pais.RSM;
//			break;
//		default:
//			try {
//				pais = Pais.valueOf(raceCode.toString());
//			} catch (Exception e) {
//				System.out.println(raceCode.toString() + " code not found in the list of country codes!");
//			}
//			break;
//		}
//
//		return pais;
//	}
//
//	
//	public static Record obtieneRecords(Integer anyo, Pais pais, Category categoria) throws IOException {
//		Record res = new Record();
//		if (!pais.equals(Pais.NOTFOUND) && anyo > 2004 && anyo < 2021) {
//			String urlBuilder = "https://www.motogp.com/es/ajax/results/parse/" + anyo + "/" + pais + "/" + categoria + "/";
//			res = new Record(urlBuilder);
//		}
//		return res;
//	}

	@Override
	public String toString() {
		return "GranPremio [site=" + site + ", date0=" + date0 + ", circuit=" + circuit + ", raceCode=" + raceCode
				+ ", results=" + results + "]";
	}
}

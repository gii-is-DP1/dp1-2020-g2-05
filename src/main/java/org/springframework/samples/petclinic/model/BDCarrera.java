package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import motogpAPI.Category;
import motogpAPI.RaceCode;
import motogpAPI.Session;

@Entity
@Table(name = "BDCarrera")

public class BDCarrera extends BaseEntity{

	@Column(name = "year")
	@NotEmpty
	@NotNull
	private Integer year;
	
	@Column(name = "racecode")
	@NotEmpty
	@NotNull
	private RaceCode racecode;
	
	@Column(name = "Category")
	@NotEmpty
	@NotNull
	private Category Category;
	
	@Column(name = "session")
	@NotEmpty
	@NotNull
	private Session session;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public RaceCode getRacecode() {
		return racecode;
	}

	public void setRacecode(RaceCode racecode) {
		this.racecode = racecode;
	}

	public Category getCategory() {
		return Category;
	}

	public void setCategory(Category category) {
		Category = category;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "BDCarrera [year =" + year + ", Session=" + session + ", Category=" + Category + ", RaceCode =" + racecode +  "]";
	}

}

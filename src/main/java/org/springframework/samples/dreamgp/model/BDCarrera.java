package org.springframework.samples.dreamgp.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import motogpApiV2.RaceCode;
import motogpApiV2.Session;



public class BDCarrera {
	
	Calendar fecha = new GregorianCalendar();

	@Min(2015)
	@NotNull
	private Integer year;
	
	@NotNull
	private RaceCode racecode;

	@NotNull
	private Category Category;
	
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

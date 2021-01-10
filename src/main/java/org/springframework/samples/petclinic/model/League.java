/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "league")
public class League extends NamedEntity {

	@Column(name = "league_date")        
//	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	@NotEmpty
	@NotBlank
	private String leagueDate;

	@Column(name = "league_code", unique=true)       
	@Size(min = 10, max = 10)
	@NotNull
	private String leagueCode;

//	@Column(name = "active_category")       
//	@NotNull
//	private Category activeCategory;
//
//	@Column(name = "races_completed")        
//	private Integer racesCompleted;
	
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="league")
	Set<Team> team;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "league")
//	private Set<Lineup> lineups;

	
	public String getLeagueDate() {
		return leagueDate;
	}

	public void setLeagueDate(String leagueDate) {
		this.leagueDate = leagueDate;
	}



//	public Integer getRacesCompleted() {
//		return racesCompleted;
//	}
//
//	public void setRacesCompleted(Integer racesCompleted) {
//		this.racesCompleted = racesCompleted;
//	}

	public Set<Team> getTeam() {
		if(this.team==null) {
			this.team=new HashSet<Team>();
		}
		return team;
	}

	public void setTeam(Set<Team> team) {
		this.team = team;
	}

	public String getLeagueCode() {
		return leagueCode;
	}


	public String setLeagueCode(String leagueCode1) {
		return this.leagueCode=leagueCode1;
	}
	
	@Override
	public String toString() {
		return "League ["+ "name="+ getName()+", leagueDate=" + leagueDate +", leagueCode="+ leagueCode 
				
//				+ ", racesCompleted=" + racesCompleted +", currentCategory="+activeCategory 
				+"]";
	}
//
//	public Category getActiveCategory() {
//		return activeCategory;
//	}
//
//	public void setActiveCategory(Category activeCategory) {
//		this.activeCategory = activeCategory;
//	}

	public void addTeam(Team team) {
		this.getTeam().add(team);
	//	team.setLeague(this);
	}



}

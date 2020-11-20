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
import javax.validation.constraints.Size;

@Entity
@Table(name = "league")
public class League extends NamedEntity {

	@Column(name = "league_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String leagueDate;

	@Column(name = "league_code")        
    @Size(min = 10, max = 10)
	private String leagueCode;
	
	@Column(name = "motogp_active")        
	private boolean motogpActive;
	
	@Column(name = "moto2_active")        
	private boolean moto2Active;
	
	@Column(name = "moto3_active")        
	private boolean moto3Active;
	
	@Column(name = "races_completed")        
	private Integer racesCompleted;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="league")
	Set<Team> team;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "league")
	private Set<Lineup> lineups;

	public String getLeagueDate() {
		return leagueDate;
	}

	public void setLeagueDate(String leagueDate) {
		this.leagueDate = leagueDate;
	}

	public boolean isMotogpActive() {
		return motogpActive;
	}

	public void setMotogpActive(boolean motogpActive) {
		this.motogpActive = motogpActive;
	}

	public boolean isMoto2Active() {
		return moto2Active;
	}

	public void setMoto2Active(boolean moto2Active) {
		this.moto2Active = moto2Active;
	}

	public boolean isMoto3Active() {
		return moto3Active;
	}

	public void setMoto3Active(boolean moto3Active) {
		this.moto3Active = moto3Active;
	}

	public Integer getRacesCompleted() {
		return racesCompleted;
	}

	public void setRacesCompleted(Integer racesCompleted) {
		this.racesCompleted = racesCompleted;
	}

	public Set<Team> getTeam() {
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
		return "League [leagueDate=" + leagueDate +", leagueaCode="+ leagueCode +", motogpActive=" + motogpActive + ", moto2Active=" + moto2Active
				+ ", moto3Active=" + moto3Active + ", racesCompleted=" + racesCompleted + "]";
	}

	public void addTeam(Team team) {
		getTeam().add(team);
		team.setLeague(this);
	}
	
	
	
}

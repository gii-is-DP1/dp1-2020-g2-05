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
package org.springframework.samples.dreamgp.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tabla_consultas")
public class TablaConsultas extends BaseEntity{

	@Column(name = "actual_race")      
	@Min(1)
	@Max(20)
	@NotNull
	private Integer actualRace;

	@Column(name = "races_completed")        
	@Min(0)
	@Max(20)
	@NotNull
	private Integer racesCompleted;
	
	@Column(name = "races_validated")        
	@Min(0)
	@Max(20)
	private Integer gpsValidated; 
	

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria_actual")        
	@NotNull
	private Category currentCategory;

	@Column(name = "time_message")        
	private String timeMessage;
	
	@Column(name = "num_users")      
	@NotNull
	private Integer numUsers;
	
	@Column(name = "num_equipos")      
	@NotNull
	private Integer numEquipos;
	
	@Column(name = "num_ligas")      
	@NotNull
	private Integer numLigas;
	
	@Column(name = "fecha_sistema")      
	private LocalDate fechaSistema;
	

	public Integer getGpsValidated() {
		return gpsValidated;
	}

	public void setGpsValidated(Integer gpsValidated) {
		this.gpsValidated = gpsValidated;
	}


	public LocalDate getFechaSistema() {
		return fechaSistema;
	}

	public void setFechaSistema(LocalDate fechaSistema) {
		this.fechaSistema = fechaSistema;
	}

	public Integer getActualRace() {
		return actualRace;
	}

	public void setActualRace(Integer actualRace) {
		this.actualRace = actualRace;
	}

	public Integer getRacesCompleted() {
		return racesCompleted;
	}

	public void setRacesCompleted(Integer racesCompleted) {
		this.racesCompleted = racesCompleted;
	}

	public Category getCurrentCategory() {
		return currentCategory;
	}

	public void setCurrentCategory(Category currentCategory) {
		this.currentCategory = currentCategory;
	}

	public String getTimeMessage() {
		return timeMessage;
	}

	public void setTimeMessage(String timeMessage) {
		this.timeMessage = timeMessage;
	}

	public Integer getNumUsers() {
		return numUsers;
	}

	public void setNumUsers(Integer numUsers) {
		this.numUsers = numUsers;
	}

	public Integer getNumEquipos() {
		return numEquipos;
	}

	public void setNumEquipos(Integer numEquipos) {
		this.numEquipos = numEquipos;
	}

	public Integer getNumLigas() {
		return numLigas;
	}

	public void setNumLigas(Integer numLigas) {
		this.numLigas = numLigas;
	}

	@Override
	public String toString() {
		return "TablaConsultas [actualRace=" + actualRace + ", racesCompleted=" + racesCompleted + ", currentCategory="
				+ currentCategory + ", timeMessage=" + timeMessage + "]";
	}
	

	



}

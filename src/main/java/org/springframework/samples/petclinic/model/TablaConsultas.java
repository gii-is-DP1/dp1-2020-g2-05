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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "categoria_actual")        
	@NotNull
	private Category currentCategory;

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

	@Override
	public String toString() {
		return "tablaConsultas [actualRace=" + actualRace + ", racesCompleted=" + racesCompleted + ", currentCategory="
				+ currentCategory + "]";
	}

	



}

package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import motogpAPI.Category;

@Entity
@Table(name = "pilot")
public class Pilot extends BaseEntity {
	
	


	@Column(name = "name")
	@NotEmpty
	private String name;
	
	
	@Column(name = "lastname")
	@NotEmpty
	private String lastName;
	

	@Column(name = "nationality")
	@NotEmpty
	private String nationality;

	@Column(name = "dorsal")
	@NotEmpty
	private String dorsal;
	
	@Column(name = "category")
	@NotEmpty
	private String category;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
	private Set<Result> results;

	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDorsal() {
		return dorsal;
	}

	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Result> getResults() {
		return results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

	public void addResult(Result result) {
		this.getResults().add(result);
	}
	
	protected Set<Result> getResultsInternal() {
		if (this.results == null) {
			this.results = new HashSet<>();
		}
		return this.results;
	}
	
}


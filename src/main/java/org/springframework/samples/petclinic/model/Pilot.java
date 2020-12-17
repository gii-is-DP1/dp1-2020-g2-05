package org.springframework.samples.petclinic.model;

import java.text.Collator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.Category;


@Entity
@Table(name = "pilot")
public class Pilot extends BaseEntity implements Comparable<Pilot> {

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
	@NotNull
	private Category category;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
	private Set<Result> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
	private Set<Offer> offers;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
	private Set<Recruit> recruit;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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
	
	

	@Override
	public String toString() {
		return "Pilot [name=" + name + ", lastName=" + lastName + ", nationality=" + nationality + ", dorsal=" + dorsal
				+ ", category=" + category + ", results=" + results + "]";
	}

	@Override
	public int compareTo(Pilot o) {
		
//		 final Collator sinAcentos = Collator.getInstance();
//		 sinAcentos.setStrength(Collator.PRIMARY); //Comparador para ignorar acentos
		    
		if (!this.category.equals(o.category))
			return this.category.compareTo(o.category);
		if (!this.name.equalsIgnoreCase(o.name))
			return this.name.compareTo(o.name);
//		if (sinAcentos.compare(this.name, o.name)!=0)
//			return sinAcentos.compare(this.name, o.name);
		if (!this.lastName.equalsIgnoreCase(o.lastName))
			return this.lastName.compareTo(o.lastName);
//		if (sinAcentos.compare(this.lastName, o.lastName)!=0)
//			return sinAcentos.compare(this.lastName, o.lastName);
		if (!this.dorsal.equalsIgnoreCase(o.dorsal))
			return this.dorsal.compareTo(o.dorsal);
		return this.nationality.compareTo(o.nationality);
	}


}


package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "alineacion")
public class Lineup extends BaseEntity {


	@Column(name = "category")
	@NotEmpty
	private String category;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
	private Set<Result> results;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pilot")
	private Set<Offer> offers;
}

package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recruit")
public class Recruit extends BaseEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="recruit")
	private Set<Lineup> lineups;

}

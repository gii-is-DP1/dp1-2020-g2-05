package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "recruit")
public class Recruit extends BaseEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="recruit1")
	private Set<Lineup> lineup1;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="recruit2")
	private Set<Lineup> lineup2;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="recruit")
	Set<Offer> offer;
	
	@ManyToOne
	@JoinColumn(name = "pilot_id")
	@NotEmpty
	private Pilot pilot;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	@NotEmpty
	private Team team;

}

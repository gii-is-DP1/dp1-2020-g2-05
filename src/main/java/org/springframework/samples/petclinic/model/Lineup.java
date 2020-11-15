package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "lineup")
public class Lineup extends BaseEntity {


	@Column(name = "category")
	@NotEmpty
	private Category category;

	@ManyToOne
	@JoinColumn(name = "recruit1_id")
	@NotEmpty
	private Recruit recruit1;

	@ManyToOne
	@JoinColumn(name = "recruit2_id")
	@NotEmpty
	private Recruit recruit2;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	@NotEmpty
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "league_id")
	@NotEmpty
	private League league;
	
	@ManyToOne
	@JoinColumn(name = "gp_id")
	@NotEmpty
	private GranPremio gp;
}

package org.springframework.samples.dreamgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dreamgp.model.Lineup;

public interface LineupRepository extends CrudRepository<Lineup, Integer>{
	
	@Query("SELECT l FROM Lineup l WHERE l.team.id = ?1")
	public List<Lineup> findByTeam(int teamId);
	
	@Query("SELECT l FROM Lineup l WHERE l.gp.id = ?1")
	public List<Lineup> findByGp(int gpId);
	
	@Query("SELECT l FROM Lineup l WHERE l.recruit1.id = ?1 OR l.recruit2.id = ?1")
	public List<Lineup> findByRecruit(int recruitId);
	
}

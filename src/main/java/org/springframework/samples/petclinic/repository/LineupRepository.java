package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Lineup;

public interface LineupRepository extends CrudRepository<Lineup, Integer>{

	
//	@Query("SELECT pilot FROM Pilot pilot left join fetch pilot.results WHERE pilot.id =:id")
//	@Query("SELECT * FROM PILOT p, RECRUIT r, TEAM t, WHERE p.id == r.pilot_id AND t.id == r.team_id")
//	public Pilot findById(int id);
	
	

	@Query(value="SELECT * FROM LINEUP AS l WHERE l.team_id = ?1", nativeQuery = true)
	public List<Lineup> findByTeam(int team_id);
	
	@Query("SELECT l FROM Lineup l WHERE l.gp.id = ?1")
	public List<Lineup> findByGp(int gpId);
	
}

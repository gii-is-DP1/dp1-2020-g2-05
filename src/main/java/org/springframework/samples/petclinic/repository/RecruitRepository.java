package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Recruit;

public interface RecruitRepository extends CrudRepository<Recruit, Integer> {
	
	@Query(value="SELECT r.* FROM RECRUIT AS r JOIN PILOT AS p JOIN TEAM t WHERE r.pilot_id = ?1 and  t.league_id = ?2 and t.id = r.team_id and p.id = r.pilot_id", nativeQuery = true)
	Optional<Recruit> findRecruitByPilotId(int pilotId, int leagueId);
	
	@Query(value="SELECT r.* FROM RECRUIT AS r WHERE r.team_id = ?1", nativeQuery = true)
	List<Recruit> findAllRecruits(int teamID);

}

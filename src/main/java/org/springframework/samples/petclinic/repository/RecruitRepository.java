package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;

public interface RecruitRepository extends CrudRepository<Recruit, Integer> {
	
	@Query(value="SELECT r.* FROM RECRUIT AS r NATURAL JOIN PILOT AS p WHERE r.pilot_id = ?1", nativeQuery = true)
	Optional<Recruit> findRecruitByPilotId(int pilotId);
	
	@Query(value="SELECT r.* FROM RECRUIT AS r WHERE r.team_id = ?1", nativeQuery = true)
	List<Recruit> findAllRecruits(int teamID);

}

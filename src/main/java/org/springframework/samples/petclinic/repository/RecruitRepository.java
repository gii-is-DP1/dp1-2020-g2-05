package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Recruit;

@Repository
public interface RecruitRepository extends Repository<Recruit, Integer>{
	
	@Query("SELECT p.* FROM PILOT AS p, RECRUIT AS r, TEAM AS t WHERE p.id = r.pilot_id AND t.id = :teamID%")
	List<Recruit> listTeamRecruits(@Param("teamID") Long teamID) throws DataAccessException;
}

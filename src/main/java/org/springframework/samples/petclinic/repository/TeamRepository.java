package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Visit;

public interface TeamRepository extends CrudRepository<Team, Integer> {

	@Query(value="SELECT * FROM TEAM WHERE TEAM.USERNAME = ?1", nativeQuery = true)
	public List<Team> findTeamByUsername(String username);
	
	@Query(value = "SELECT * FROM TEAM WHERE TEAM.USERNAME = ?1 AND TEAM.LEAGUE_ID = ?2", nativeQuery = true)
	public Team findTeamByUsernameAndLeagueId(String username, Integer id);
	
	@Query(value = "SELECT * FROM TEAM WHERE TEAM.LEAGUE_ID = ?1", nativeQuery = true)
	public List<Team> findTeamByLeagueId(Integer id);
	
}

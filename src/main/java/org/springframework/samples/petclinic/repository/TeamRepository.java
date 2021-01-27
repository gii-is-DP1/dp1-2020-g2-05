package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Team;

public interface TeamRepository extends CrudRepository<Team, Integer> {

	@Query(value="SELECT * FROM TEAM WHERE TEAM.USERNAME = ?1", nativeQuery = true)
	public List<Team> findTeamByUsername(String username);
	
	@Query(value = "SELECT * FROM TEAM WHERE TEAM.USERNAME = ?1 AND TEAM.LEAGUE_ID = ?2", nativeQuery = true)
	public Optional<Team> findTeamByUsernameAndLeagueId(String username, Integer id);
	
	@Query(value = "SELECT * FROM TEAM WHERE TEAM.LEAGUE_ID = ?1", nativeQuery = true)
	public List<Team> findTeamByLeagueId(Integer id);
	
}

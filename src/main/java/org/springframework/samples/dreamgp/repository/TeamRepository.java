package org.springframework.samples.dreamgp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dreamgp.model.Team;

public interface TeamRepository extends CrudRepository<Team, Integer> {

	@Query(value="SELECT t FROM Team t WHERE t.user.username = :teamUsername")
	public List<Team> findTeamByUsername(@Param("teamUsername") String teamUsername);
	
	@Query(value = "SELECT t FROM Team t WHERE t.user.username = :teamUsername AND t.league.id = :teamLeague")
	public Optional<Team> findTeamByUsernameAndLeagueId(@Param("teamUsername") String teamUsername, @Param("teamLeague") Integer teamLeague);
	
	@Query(value = "SELECT t FROM Team t  WHERE t.league.id = :teamLeague")
	public List<Team> findTeamByLeagueId(@Param("teamLeague") Integer teamLeague);
	
	@Query("SELECT COUNT(t) FROM Team t WHERE t.league.id = :id")
	public Integer findTeamsByLeagueId(@Param("id") Integer id);
	
	@Query("SELECT team.league.id FROM Team team WHERE team.user.username LIKE :username")
	public List<Integer> findTeamsByUsername(@Param("username") String username);
	
}

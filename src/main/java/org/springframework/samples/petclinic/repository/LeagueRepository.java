package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface LeagueRepository extends Repository<League, Integer>{

	@Query("SELECT team.league.id FROM Team team WHERE team.user.username LIKE :username")
	public List<Integer> findTeamsByUsername(@Param("username") String username);
	
	@Query("SELECT COUNT(t) FROM Team t WHERE t.league.id = :id")
	public Integer findTeamsByLeagueId(@Param("id") Integer id);
	
	@Query("SELECT user FROM User user WHERE user.username LIKE :username") //METER EN USER REPOSITORY
	public Optional<User> findUserByUsername(@Param("username") String username);
	
	@Query("SELECT COUNT(t)  FROM Team t WHERE t.user.username like :username") 
	public Integer findLeaguesByUsername(@Param("username") String username);
	
	@Query("SELECT aut.authority FROM Authorities aut WHERE aut.user.username LIKE :username") //METER EN USER REPOSITORY
	public String findAuthoritiesByUsername(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query("UPDATE League l SET l.name = :name WHERE l.id = :id")	
	public void updateLeagueName(@Param("id") int id,@Param("name") String name);

	Optional<League> findByLeagueCode(String leagueCode);
	
	Optional<League>  findById(Integer leagueId);
	
	Iterable<League> findAll();
	
	@Transactional
	void delete(League league);
	
	@Transactional
	void save(League league);
	
}
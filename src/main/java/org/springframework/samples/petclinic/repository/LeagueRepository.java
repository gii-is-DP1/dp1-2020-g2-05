package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

public interface LeagueRepository extends CrudRepository<League, Integer>{

	
//	@Transactional
//	@Modifying
//	@Query("UPDATE League l SET l.racesCompleted = :racesCompleted WHERE l.id = :id")	
//	public void increaseRacesCompleted(@Param("id") int id,@Param("racesCompleted") Integer racesCompleted);

//	@Transactional
//	@Modifying
//	@Query("UPDATE League l SET l.activeCategory = :idCategory WHERE l.id = :id")	
//	public void activeCategory(@Param("id") int id,@Param("idCategory") Category idCategory);
//	

//	@Query("SELECT l FROM League l WHERE l.activeCategory = :idCategory")	
//	public List<League> findAllLeaguesByCategory(@Param("idCategory") Category idCategory);
	
	@Query("SELECT league FROM League league WHERE league.leagueCode LIKE :leagueCode")
	public Optional<League> findLeagueByLeagueCode(@Param("leagueCode") String leagueCode);
	
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
	
	
	
//	@Query(value ="UPDATE LEAGUE SET racesCompleted=(SELECT racesCompleted FROM LEAGUE WHERE LEAGUE.id=leagueId)+1 WHERE LEAGUE.id=leagueId", nativeQuery=true)	
//	public Optional<League> incrementarCarrerasLiga(@Param("leagueId") Integer leagueId);

	
}
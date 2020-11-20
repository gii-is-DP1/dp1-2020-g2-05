package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

public interface LeagueRepository extends CrudRepository<League, Integer>{

//	@Transactional
//	@Modifying
//	@Query("UPDATE League l SET l.moto3Active=true,l.moto2Active=false,l.motogpActive=false WHERE l.id = id")	
//	public void activeMoto3(@Param("id") int id);	
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE League l SET l.moto3Active=false,l.moto2Active=true,l.motogpActive=false WHERE l.id = id")	
//	public void activeMoto2(@Param("id") int id);	
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE League l SET l.moto3Active=false,l.moto2Active=false,l.motogpActive=true WHERE l.id = id")	
//	public void activeMotogp(@Param("id") int id);	
	
	@Query("SELECT league FROM League league WHERE league.leagueCode LIKE :leagueCode")
	public Optional<League> findLeagueByLeagueCode(@Param("leagueCode") String leagueCode);
	
	@Query("SELECT team.league.id FROM Team team WHERE team.user.username LIKE :username")
	public Collection<Integer> findTeamsByUsername(@Param("username") String username);
	
	@Query("SELECT user FROM User user WHERE user.username LIKE :username") //METER EN USER REPOSITORY
	public Optional<User> findUserByUsername(@Param("username") String username);
	
	@Query("SELECT COUNT(t)  FROM Team t WHERE t.user.username like :username") 
	public Integer findLeaguesByUsername(@Param("username") String username);
	
//	@Query(value ="UPDATE LEAGUE SET racesCompleted=(SELECT racesCompleted FROM LEAGUE WHERE LEAGUE.id=leagueId)+1 WHERE LEAGUE.id=leagueId", nativeQuery=true)	
//	public Optional<League> incrementarCarrerasLiga(@Param("leagueId") Integer leagueId);

	
}
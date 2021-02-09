package org.springframework.samples.dreamgp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface LeagueRepository extends Repository<League, Integer>{
	
	@Query("SELECT COUNT(t)  FROM Team t WHERE t.user.username like :username") 
	public Integer findLeaguesByUsername(@Param("username") String username);

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
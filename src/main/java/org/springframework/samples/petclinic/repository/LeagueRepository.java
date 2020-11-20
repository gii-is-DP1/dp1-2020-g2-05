package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public interface LeagueRepository extends CrudRepository<League, Integer>{
//Iremos añadiendo poco a poco mas metodos
//	@Query("UPDATE League league SET league.racesCompleted=(SELECT leag.racesCompleted FROM League leag WHERE leag.id=leagueId)+1 WHERE league.id=leagueId")
//	public League incrementarCarrerasLiga(@Param("leagueId") Integer leagueId);
}

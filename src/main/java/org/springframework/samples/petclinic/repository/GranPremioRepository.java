package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.GranPremio;

public interface GranPremioRepository  extends  CrudRepository<GranPremio, Integer>{

	
	@Query("SELECT gp FROM GranPremio gp WHERE gp.date0 >= :year AND gp.calendar = true")
	public List<GranPremio> findAllActualYear(@Param("year") LocalDate year);
	
	@Query("SELECT gp FROM GranPremio gp WHERE gp.hasBeenRun=true ORDER BY gp.id DESC")
	public List<GranPremio> findUltimoGpSinValidar() ;
	
	@Query("SELECT gp FROM GranPremio gp WHERE gp.date0=:fecha")
	public GranPremio ultimoGpCompletado(@Param("fecha")LocalDate fecha);
}

package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;

public interface GranPremioRepository  extends  CrudRepository<GranPremio, Integer>{

	
	@Query("SELECT gp FROM GranPremio gp WHERE gp.date0 >= :year")
	public List<GranPremio> findAllActualYear(@Param("year") String year);
}

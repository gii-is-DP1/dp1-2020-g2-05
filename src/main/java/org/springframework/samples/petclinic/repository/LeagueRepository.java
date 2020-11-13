package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.League;

public interface LeagueRepository extends CrudRepository<League, Integer>{
//Iremos añadiendo poco a poco mas metodos
}

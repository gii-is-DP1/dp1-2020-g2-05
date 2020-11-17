package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public interface LeagueRepository extends CrudRepository<League, Integer>{
//Iremos añadiendo poco a poco mas metodos

}

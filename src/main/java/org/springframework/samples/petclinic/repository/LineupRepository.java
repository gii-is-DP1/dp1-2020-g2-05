package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Lineup;

public interface LineupRepository extends CrudRepository<Lineup, Integer>{

}

package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Disease;
import org.springframework.data.repository.CrudRepository;

public interface DiseasesRepository extends CrudRepository<Disease,Integer>{

}

package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.BDCarrera;

public interface BDCarreraRepository extends CrudRepository<BDCarrera, Integer> {

}

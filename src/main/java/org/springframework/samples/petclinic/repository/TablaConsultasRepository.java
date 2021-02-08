package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TablaConsultas;

public interface TablaConsultasRepository extends CrudRepository<TablaConsultas,Integer> {

}

package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Authorities;



public interface AuthoritiesRepository extends CrudRepository<Authorities, String>{
	
	public List<Authorities> findByAuthority(String authority);
}

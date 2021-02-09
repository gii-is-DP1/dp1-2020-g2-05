package org.springframework.samples.dreamgp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dreamgp.model.Authorities;



public interface AuthoritiesRepository extends CrudRepository<Authorities, String>{
	
	public List<Authorities> findByAuthority(String authority);
}

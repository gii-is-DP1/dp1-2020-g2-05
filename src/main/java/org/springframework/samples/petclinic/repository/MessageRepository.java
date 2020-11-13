package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Message;


public interface MessageRepository extends  CrudRepository<Message, Integer>{
	
}

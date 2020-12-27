package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Message;


public interface MessageRepository extends  CrudRepository<Message, Integer>{
	
	@Query("SELECT message FROM Message message WHERE message.usernamereceive.username LIKE :usernamereceive")
	public List<Message> findByUsernameReceive(@Param("usernamereceive") String usernamereceive);
	
	@Query("SELECT message FROM Message message WHERE message.usernamesend.username LIKE :usernamesend")
	public Collection<Message> findByUsernameSend(@Param("usernamesend") String usernamesend);
}

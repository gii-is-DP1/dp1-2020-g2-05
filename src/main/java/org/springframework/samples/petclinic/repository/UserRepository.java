package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
	@Query("SELECT user.friends FROM User user  WHERE user.username LIKE :username")
	public List<User> findFriendByUser(@Param("username") String username);
	

	

}
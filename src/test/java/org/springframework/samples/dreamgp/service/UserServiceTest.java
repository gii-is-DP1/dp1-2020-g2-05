package org.springframework.samples.dreamgp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.service.exceptions.UserEmailEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserPasswordEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserUsernameEmptyOrNullException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {
	 
	 @Autowired
	 	protected UserService userService;
	 
	 @Test
		void shouldFindUserByName() {
			Optional<User> user = this.userService.findUser("serrojjim");
			assertThat(user.isPresent()).isTrue();
			
			
			Optional<User> user_fail = this.userService.findUser("pruebafail");
			assertThat(user_fail.isPresent()).isFalse();
		}
	 

	 
	 @Test
	 @Transactional
	 void shouldInsertUser() throws DataAccessException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {
		Iterable<User> user = this.userService.findAll();
		List<User> found = new ArrayList<User>();
	    user.forEach(found::add);
		Integer found1=found.size();
		
		User newUser = new User();
		newUser.setUsername("Prueba");
		newUser.setPassword("prueba");
		newUser.setEmail("prueba@gmail.com");
		newUser.setImgperfil("probando");
		newUser.setEnabled(true);
		this.userService.saveUser(newUser);
		assertThat(newUser.getUsername()).isNotEqualTo(null);
		user = this.userService.findAll();
		found=new ArrayList<User>();
	    user.forEach(found::add);

		assertThat(found.size()).isEqualTo(found1 + 1);
	 }
	 
	 @Test
	 @Transactional
	 void shouldDeleteUser() throws DataAccessException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {
		 Iterable<User> user = this.userService.findAll();
			List<User> found = new ArrayList<User>();
		    user.forEach(found::add);
			Integer found1=found.size();
		
			
			User newUser = new User();
			newUser.setUsername("Prueba");
			newUser.setPassword("prueba");
			newUser.setEmail("prueba@gmail.com");
			newUser.setImgperfil("probando");
			newUser.setEnabled(true);
		
			this.userService.saveUser(newUser);
		this.userService.delete(newUser);
		user = this.userService.findAll();
		found=new ArrayList<User>();
	    user.forEach(found::add);
	    
		assertThat(found.size()).isEqualTo(found1);
	 }
	 
	 @Test
		void shouldFindFriendsByUser() throws DataAccessException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {
		 	List<User> friends1 = userService.findUser("serrojjim").get().getFriends();
		 	Optional<User> friend = 	userService.findUser("martorsan13");
			friends1.add(friend.get());
	
			userService.findUser("serrojjim").get().setFriends(friends1);
			
			userService.saveUser(userService.findUser("serrojjim").get());
		 
			List<User> friends = this.userService.findFriendByUser("serrojjim");
			assertThat(friends.contains(friend.get())).isTrue();
			assertThat(friends.contains(userService.findUser("admin1").get())).isFalse();
		
		}
	 


}

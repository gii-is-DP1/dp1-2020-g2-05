package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
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
	 
//	 @Test
//	   void shouldFindMessagesByUsernameReceive() {
//		 	Iterable<Message> num_messages = this.messageService.findAllUsernameReceive("martorsan13");
//			 List<Message> messages = new ArrayList<Message>();
//			 num_messages.forEach(messages::add);
//				System.out.println("El numero de mensajes recibidos por mariano es " + messages.size());
//
//			assertThat(messages.size()!=0).isTrue();
//			
//			
//			Iterable<Message> num_messages_zero = this.messageService.findAllUsernameReceive("serrojjim");
//			 List<Message> messages_fail = new ArrayList<Message>();
//			 num_messages_zero.forEach(messages_fail::add);
//				System.out.println("El numero de mensajes recibido  por sergio es " + messages_fail.size());
//
//			assertThat(messages_fail.size()==0).isTrue();
//	 }
//	 
//	 @Test
//	   void shouldFindMessagesByUsernameSend() {
//		 	Iterable<Message> num_messages = this.messageService.findAllUsernameSend("serrojjim");
//			List<Message> messages = new ArrayList<Message>();
//			num_messages.forEach(messages::add);
//			System.out.println("El numero de mensajes enviado es antcammar4" + + messages.size());
//			assertThat(messages.size()!=0).isTrue();
//						
//			
//			Iterable<Message> num_messages_zero = this.messageService.findAllUsernameSend("antcammar4");
//			List<Message> messages_fail = new ArrayList<Message>();
//			 num_messages_zero.forEach(messages_fail::add);
//				System.out.println("El numero de mensajes enviado  por antcama es " + messages_fail.size());
//
//			assertThat(messages_fail.size()==0).isTrue();
//	 }
	 
	 @Test
	 @Transactional
	 void shouldInsertUser() {
		Iterable<User> user = this.userService.findAll();
		List<User> found = new ArrayList<User>();
	    user.forEach(found::add);
		Integer found1=found.size();
		
		User newUser = new User();
		newUser.setUsername("Prueba");
		newUser.setPassword("prueba");
		newUser.setEmail("prueba@gmail.com");
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
	 void shouldDeleteUser() {
		 Iterable<User> user = this.userService.findAll();
			List<User> found = new ArrayList<User>();
		    user.forEach(found::add);
			Integer found1=found.size();
		
			
			User newUser = new User();
			newUser.setUsername("Prueba");
			newUser.setPassword("prueba");
			newUser.setEmail("prueba@gmail.com");
			newUser.setEnabled(true);
		
			this.userService.saveUser(newUser);
		this.userService.delete(newUser);
		user = this.userService.findAll();
		found=new ArrayList<User>();
	    user.forEach(found::add);
	    
		assertThat(found.size()).isEqualTo(found1);
	 }

}

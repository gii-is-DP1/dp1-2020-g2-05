package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MessageServiceTest {
	 @Autowired
		protected MessageService messageService;
	 
	 @Autowired
	 	protected UserService userService;
	 
	 @Test
		void shouldFindMessageById() {
			Optional<Message> message = this.messageService.findMessageById(1);
			assertThat(message.isPresent()).isTrue();
			
			
			Optional<Message> message_fail = this.messageService.findMessageById(8);
			assertThat(message_fail.isPresent()).isFalse();
		}
	 
	 @Test
	   void shouldFindMessagesByUsernameReceive() {
		 	Iterable<Message> num_messages = this.messageService.findAllUsernameReceive("martorsan13");
			 List<Message> messages = new ArrayList<Message>();
			 num_messages.forEach(messages::add);
				System.out.println("El numero de mensajes recibidos por mariano es " + messages.size());

			assertThat(messages.size()!=0).isTrue();
			
			
			Iterable<Message> num_messages_zero = this.messageService.findAllUsernameReceive("serrojjim");
			 List<Message> messages_fail = new ArrayList<Message>();
			 num_messages_zero.forEach(messages_fail::add);
				System.out.println("El numero de mensajes recibido  por sergio es " + messages_fail.size());

			assertThat(messages_fail.size()==0).isTrue();
	 }
	 
	 @Test
	   void shouldFindMessagesByUsernameSend() {
		 	Iterable<Message> num_messages = this.messageService.findAllUsernameSend("serrojjim");
			List<Message> messages = new ArrayList<Message>();
			num_messages.forEach(messages::add);
			System.out.println("El numero de mensajes enviado es antcammar4" + + messages.size());
			assertThat(messages.size()!=0).isTrue();
						
			
			Iterable<Message> num_messages_zero = this.messageService.findAllUsernameSend("antcammar4");
			List<Message> messages_fail = new ArrayList<Message>();
			 num_messages_zero.forEach(messages_fail::add);
				System.out.println("El numero de mensajes enviado  por antcama es " + messages_fail.size());

			assertThat(messages_fail.size()==0).isTrue();
	 }
	 
	 @Test
	 @Transactional
	 void shouldInsertMessage() {
		Iterable<Message> message = this.messageService.findAll();
		List<Message> found = new ArrayList<Message>();
	    message.forEach(found::add);
		Integer found1=found.size();
		
		Message newMessage = new Message();
		newMessage.setAsunto("PRUEBA");
		newMessage.setCuerpo("Cuerpo prueba");
		newMessage.setUsernamesend(userService.findUser("serrojjim").get());
		newMessage.setUsernamereceive(userService.findUser("martorsan13").get());
		newMessage.setVisto(0);
		this.messageService.saveMessage(newMessage);
		assertThat(newMessage.getId().longValue()).isNotEqualTo(0);

		message = this.messageService.findAll();
		found=new ArrayList<Message>();
	    message.forEach(found::add);

		assertThat(found.size()).isEqualTo(found1 + 1);
	 }
	 
	 @Test
	 @Transactional
	 void shouldDeleteMessage() {
		 Iterable<Message> message = this.messageService.findAll();
		 List<Message> found = new ArrayList<Message>();
		 message.forEach(found::add);
		 Integer found1=found.size();
		
		 Message newMessage = new Message();
			newMessage.setAsunto("PRUEBA");
			newMessage.setCuerpo("Cuerpo prueba");
			newMessage.setUsernamesend(userService.findUser("serrojjim").get());
			newMessage.setUsernamereceive(userService.findUser("martorsan13").get());
			newMessage.setVisto(0);
		
			this.messageService.saveMessage(newMessage);
		this.messageService.delete(newMessage);
		message = this.messageService.findAll();
		found=new ArrayList<Message>();
	    message.forEach(found::add);
	    
		assertThat(found.size()).isEqualTo(found1);
	 }

}

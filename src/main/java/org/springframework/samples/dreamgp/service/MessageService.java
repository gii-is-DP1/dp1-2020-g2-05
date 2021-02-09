/*
UserRepository.java * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dreamgp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Message;
import org.springframework.samples.dreamgp.repository.MessageRepository;
import org.springframework.samples.dreamgp.service.exceptions.EmptyAsuntoCuerpoMessageException;
import org.springframework.samples.dreamgp.service.exceptions.EmptyUserMessageException;
import org.springframework.samples.dreamgp.service.exceptions.ReceiveEqualSendMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

	private MessageRepository messageRepository;


	@Autowired
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Transactional
	public int messageCount() {
		return (int) messageRepository.count();
	}

	@Transactional
	public List<Message> findAll(){

		List<Message> result = new ArrayList<Message>();
		messageRepository.findAll().forEach(result::add);
		return result;
	}

	@Transactional
	public List<Message> findAllUsernameReceive(String usernamereceive){
		return messageRepository.findByUsernameReceive(usernamereceive);
	}

	@Transactional
	public Iterable<Message> findAllUsernameSend(String usernamesend){
		return messageRepository.findByUsernameSend(usernamesend);
	}

	public void delete(Message messageId) {
		messageRepository.delete(messageId);
	}	

	@Transactional
	public Optional<Message> findMessageById(int messageId) {
		return messageRepository.findById(messageId);
	}

	@Transactional
	public void saveMessage(@Valid Message message) throws DataAccessException, ReceiveEqualSendMessageException, EmptyAsuntoCuerpoMessageException, EmptyUserMessageException {
		if(message.getUsernamereceive().equals(message.getUsernamesend())) {
			throw new ReceiveEqualSendMessageException("El usuario que envía el mensaje no puede ser igual al que lo recibe");
		}else if(message.getAsunto().isEmpty() || message.getCuerpo().isEmpty()){
			throw new EmptyAsuntoCuerpoMessageException("El cuerpo o el asunto no puede estar vacío");

		}else if((message.getUsernamereceive()==null  )|| message.getUsernamesend()==null){
			throw new EmptyUserMessageException("El usuario que envia o recibe no puede ser null");
		}
		messageRepository.save(message);		
	}		

}




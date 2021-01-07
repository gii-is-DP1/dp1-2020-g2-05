/*
 * Copyright 2002-2013 the original author or authors.
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
package org.springframework.samples.petclinic.service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public Iterable<User> findAll(){
		return userRepository.findAll();
	}
	
	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		
		userRepository.save(user);
		

	}
	
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}
	
	public User getUserSession() {
		User usuario = new User();  
		try {
			  Optional<User> user = findUser(SecurityContextHolder.getContext().getAuthentication().getName());
			  usuario =  user.get();
		  }catch (Exception e) {	
		  }
		return usuario;
	}

	public Object findFriendByUser(String username) {
		return userRepository.findFriendByUser(username);
		
	}
	
	
	
	public void delete(User user) {
		userRepository.delete(user);
		
	}	

}

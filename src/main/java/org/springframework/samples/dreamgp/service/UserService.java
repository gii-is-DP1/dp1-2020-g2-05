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
package org.springframework.samples.dreamgp.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Authorities;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.repository.UserRepository;
import org.springframework.samples.dreamgp.service.exceptions.UserEmailEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserPasswordEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserUsernameEmptyOrNullException;
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
	public List<User> findAll(){
		
		List<User> lista = (List<User>) userRepository.findAll();
		return lista;
	}
	
	@Transactional
	public void saveUser(User user) throws DataAccessException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {
		if(user.getEmail().trim().isEmpty() || user.getEmail()== null) {
			throw new UserEmailEmptyOrNullException("El email del usuario no puede ser null ni esta vacío");
		}else if(user.getPassword().trim().isEmpty() || user.getPassword()==null){
			throw new UserPasswordEmptyOrNullException("El password del usuario no puede ser null ni esta vacío");
		}else if(user.getUsername().trim().isEmpty() || user.getUsername()==null){
			throw new UserUsernameEmptyOrNullException("El username del usuario no puede ser null ni esta vacío");
		}
		
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
	
	public List<User> findFriendByUser(String username) {
		return userRepository.findFriendByUser(username);
		
	}
	
	
	
	public void delete(User user) {
		userRepository.delete(user);
		
	}	

}

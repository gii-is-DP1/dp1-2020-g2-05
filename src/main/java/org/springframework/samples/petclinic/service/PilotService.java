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

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Pilot;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PilotService {

	@Autowired
	private PilotRepository pilotRepository;
	
<<<<<<< HEAD
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public PilotService(PilotRepository pilotRepository) {
		this.pilotRepository = pilotRepository;
	}	

	@Transactional(readOnly = true)
	public Optional<Pilot> findPilotById(int id)  {
		return pilotRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
		return pilotRepository.findByLastName(lastName);
	}

=======
>>>>>>> branch 'master' of https://github.com/gii-is-DP1/dp1-2020-g2-05.git
	@Transactional
	public int pilotCount() {
		return (int) pilotRepository.count();
	}
	
	@Transactional
	public Iterable<Pilot> findAll(){
		return pilotRepository.findAll();
	}
<<<<<<< HEAD

	public void delete(Pilot pilotId) {
		pilotRepository.delete(pilotId);
		
	}	
=======
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private AuthoritiesService authoritiesService;
//
//	@Autowired
//	public PilotService(PilotRepository pilotRepository) {
//		this.pilotRepository = pilotRepository;
//	}	
//
	@Transactional
	public Optional<Pilot> findPilotById(int pilotId) {
		return pilotRepository.findById(pilotId);
	}
//	@Transactional(readOnly = true)
//	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
//		return pilotRepository.findByLastName(lastName);
//	}
//
//	@Transactional
//	public void savePilot(Pilot pilot) throws DataAccessException {
//		//creating owner
//		pilotRepository.save(pilot);		
//	}		
//	
//	@Transactional(readOnly = true)	
//	public Collection<Pilot> findPilots() throws DataAccessException {
//		return pilotRepository.findAll();
//	}	
>>>>>>> branch 'master' of https://github.com/gii-is-DP1/dp1-2020-g2-05.git

}

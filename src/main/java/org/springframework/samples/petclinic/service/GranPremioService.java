package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.repository.GranPremioRepository;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import motogpAPI.Category;
import motogpAPI.PeticionesGet;
import motogpAPI.Session;
import motogpAPI.model.InfoCarrera;

@Service
public class GranPremioService {

	@Autowired
	private GranPremioRepository GPRepository;

	@Autowired
	private UserService userService;
	

	@Autowired
	private GranPremioService pilotService;

	@Autowired
	private AuthoritiesService authoritiesService;

	

//	@Transactional(readOnly = true)
//	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
//		return pilotRepository.findByLastName(lastName);
//	}



	@Transactional
	public Iterable<GranPremio> findAll() {
		return GPRepository.findAll();
	}

	public void delete(GranPremio gp) {
		GPRepository.delete(gp);

	}

	
	@Transactional
	public Optional<GranPremio> findGPById(int gp) {
		return GPRepository.findById(gp);
	}

//	@Transactional(readOnly = true)
//	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
//		return pilotRepository.findByLastName(lastName);
//	}
//
	@Transactional
	public void saveGP(GranPremio gp) throws DataAccessException {
		GPRepository.save(gp);
		
		
	}
}
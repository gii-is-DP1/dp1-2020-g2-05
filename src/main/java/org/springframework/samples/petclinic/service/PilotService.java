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
package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.web.ResultFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.samples.petclinic.model.Category;
import motogpAPI.PeticionesGet;
import motogpAPI.RaceCode;
import motogpAPI.Session;
import motogpAPI.model.InfoCarrera;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PilotService {
	
	public static final Integer MAXIMO_CARRERAS=18;
	
	@Autowired
	private PilotRepository pilotRepository;

	@Autowired
	private GranPremioService gpService;
	

	@Autowired
	private ResultService resultService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public PilotService(PilotRepository pilotRepository) {
		this.pilotRepository = pilotRepository;
	}

//	@Transactional(readOnly = true)
//	public Collection<Pilot> findPilotByLastName(String lastName) throws DataAccessException {
//		return pilotRepository.findByLastName(lastName);
//	}

	@Transactional
	public int pilotCount() {
		return (int) pilotRepository.count();
	}

	@Transactional
	public Iterable<Pilot> findAll() {
		return pilotRepository.findAll();
	}

	public void delete(Pilot pilotId) {
		pilotRepository.delete(pilotId);

	}
	
//	public void prueba() throws JSONException, IOException{
//		FormRellenarBD form = new FormRellenarBD();
//		
//		form.setAnyoFinal(2019);
//		form.setAnyoFinal(2018);
//		form.setCategory(Category.Moto3);
//		
//	}
	
	public List<String> obtenerResultsFormatted(Set<Result> result){
		ResultFormatter formatter = new ResultFormatter(resultService);
		List<String> res = new ArrayList<String>();
		for(Result r:result) {
			res.add(formatter.print(r, Locale.ENGLISH));
		}
		return res;
	}
	
	public void poblarBD(FormRellenarBD form) throws JSONException, IOException {

		for(int i=form.getAnyoInicial();i<form.getAnyoFinal();i++) {

			for(int j=0;j<MAXIMO_CARRERAS;j++) {
				GranPremio gp = new GranPremio(); //entidad de una carrera
				List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceNumberCampu(form.getCategory(), i, j, Session.RACE);
				if(todosLosResultadosDeUnaCarrera.size()==0) {
					
				}else {
					gp.setSite(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
					gp.setCircuit(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
					gp.setDate0(todosLosResultadosDeUnaCarrera.get(0).getFecha());
					gp.setRaceCode(todosLosResultadosDeUnaCarrera.get(0).getRaceCode());
					this.gpService.saveGP(gp);
					 
					for(int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
						InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
						gp.setCircuit(resultado_k.getLugar());
						gp.setSite(resultado_k.getNombreEvento());
						
						Pilot pilot = new Pilot();
						pilot.setName(resultado_k.getPiloto().split(" ")[0]);
						pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
						pilot.setDorsal(resultado_k.getNumeros().toString());

						if(resultado_k.getPais().isEmpty()) {
							pilot.setNationality("Andorra");

						}else {
							pilot.setNationality(resultado_k.getPais());

						}
					
						
						pilot.setCategory((form.getCategory()));
						Result result = new Result();
						
						if(this.countByName(pilot.getLastName(), pilot.getName())!=0) {
							result.setPilot(this.pilotRepository.findByName(pilot.getLastName(), pilot.getName()).get());

						}else {
							result.setPilot(pilot);

						}
						result.setPosition(resultado_k.getPosicion());
						result.setGp(gp);
						result.setLap(false);
						result.setPole(false);
						this.resultService.saveResult(result);
						this.savePilot(pilot);
					}
					
				}
				
				
				
			}			
		}
	}
	
//	2016, RaceCode.AUT, Session.RACE
	public void poblarBDCarreraACarrera(BDCarrera form) throws JSONException, IOException {


				GranPremio gp = new GranPremio(); //entidad de una carrera
				List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceCodeCampu(form.getCategory(), form.getYear(), form.getRacecode(), form.getSession());
				if(todosLosResultadosDeUnaCarrera.size()==0) {
					
				}else {
					gp.setSite(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
					gp.setCircuit(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
					gp.setDate0(todosLosResultadosDeUnaCarrera.get(0).getFecha());
					gp.setRaceCode(todosLosResultadosDeUnaCarrera.get(0).getRaceCode());
					this.gpService.saveGP(gp);
					 
					for(int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
						InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
						gp.setCircuit(resultado_k.getLugar());
						gp.setSite(resultado_k.getNombreEvento());
						
						Pilot pilot = new Pilot();
						pilot.setName(resultado_k.getPiloto().split(" ")[0]);
						pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
						pilot.setDorsal(resultado_k.getNumeros().toString());

						if(resultado_k.getPais().isEmpty()) {
							pilot.setNationality("Andorra");

						}else {
							pilot.setNationality(resultado_k.getPais());

						}
					
						
						pilot.setCategory(resultado_k.getCategory());
						Result result = new Result();
						
						if(this.countByName(pilot.getLastName(), pilot.getName())!=0) {
							result.setPilot(this.pilotRepository.findByName(pilot.getLastName(), pilot.getName()).get());

						}else {
							result.setPilot(pilot);

						}
						result.setPosition(resultado_k.getPosicion());
						result.setGp(gp);
						result.setLap(false);
						result.setPole(false);
						this.resultService.saveResult(result);
						this.savePilot(pilot);
					}
					
				}
				
				
				
			}			
		
	
	
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
	@Transactional
	public void savePilot(Pilot pilot) throws DataAccessException {
		if(this.countByName(pilot.getLastName(), pilot.getName())==0) {
			pilotRepository.save(pilot);
		}
		
	}
	
	
	public Optional<Pilot> findByName(String lastName,String name) {
		return this.pilotRepository.findByName(lastName, name);
	}
	
	public Integer countByName(String lastName,String name) {
		return this.pilotRepository.countByName(lastName, name);
	}


	
	
	
//	
//	@Transactional(readOnly = true)	
//	public Collection<Pilot> findPilots() throws DataAccessException {
//		return pilotRepository.findAll();
//	}	

	public List<Pilot> getRecruits() throws DataAccessException {
		return this.pilotRepository.findAllRecruits();
	}
	
	public List<Pilot> getRecruits(int teamID) throws DataAccessException {
		return this.pilotRepository.findAllRecruits(teamID);
	}
}
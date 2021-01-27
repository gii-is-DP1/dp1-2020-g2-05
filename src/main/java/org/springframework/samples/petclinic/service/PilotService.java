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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.web.ResultFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import motogpAPI.PeticionesGet;
import motogpAPI.Session;
import motogpAPI.model.InfoCarrera;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Slf4j
@Service
public class PilotService {
	
	public static final Integer MAXIMO_CARRERAS=18;
	
	private PilotRepository pilotRepository;
	private GranPremioService gpService;
	private ResultService resultService;
//	private AuthoritiesService authoritiesService;
	
	@Autowired
	public PilotService(PilotRepository pilotRepository, GranPremioService gpService, ResultService resultService) {
		this.pilotRepository = pilotRepository;
		this.gpService = gpService;
		this.resultService = resultService;
	}

	@Transactional
	public int pilotCount() {
		return (int) pilotRepository.count();
	}

	@Transactional
	public List<Pilot> findAll() {
		return (List<Pilot>) pilotRepository.findAll();
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
		for (Result r:result) {
			res.add(formatter.print(r, Locale.ENGLISH));
		}
		return res;
	}
	
	public void poblarBD(FormRellenarBD form) throws JSONException, IOException, ParseException {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");		
		for(int i=form.getAnyoInicial(); i < form.getAnyoFinal(); i++) {
			for (int j=0;j<MAXIMO_CARRERAS;j++) {
				GranPremio gp = new GranPremio(); //entidad de una carrera
				List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceNumberCampu(form.getCategory(), i, j, Session.RACE);
//				if(todosLosResultadosDeUnaCarrera.size()==0) {
//					throw new NotFoundException("No se han encontrado carreras para los anyos dados");
//				}else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(todosLosResultadosDeUnaCarrera.get(0).getFecha(), formatter);				
					gp.setSite(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
					gp.setCircuit(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
					gp.setDate0(date);
					gp.setRaceCode(todosLosResultadosDeUnaCarrera.get(0).getRaceCode());
					gp.setCalendar(false);
					this.gpService.saveGP(gp);

					for (int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
						InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
						gp.setCircuit(resultado_k.getLugar());
						gp.setSite(resultado_k.getNombreEvento());

						Pilot pilot = new Pilot();
						pilot.setName(resultado_k.getPiloto().split(" ")[0]);
						pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
						pilot.setDorsal(resultado_k.getNumeros().toString());

						if (resultado_k.getPais().isEmpty()) {pilot.setNationality("Andorra");} 
						else {pilot.setNationality(resultado_k.getPais());}

						pilot.setCategory((form.getCategory()));

						Random random = new Random();
						pilot.setBaseValue(random.nextInt(3000) + 1000);//Valores arbitrarios, pueden cambiar

						Result result = new Result();

						if (this.countByName(pilot.getLastName(), pilot.getName())!=0) {
							result.setPilot(this.pilotRepository.findByName(pilot.getLastName(), pilot.getName()).get());
						} else {
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
//	}
	
	//	2016, RaceCode.AUT, Session.RACE
	public void poblarBDCarreraACarrera(BDCarrera form,GranPremio gp,Boolean GpEstaEnCalendario) throws JSONException, IOException, ParseException {

		//EL GP QUE SE PASA COMO PARAMETRO, O ESTA VACIO, O ESTA EN EL CALENDARIO
		//				GranPremio gp = new GranPremio(); //entidad de una carrera
		List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceCodeCampu(form.getCategory(), form.getYear(), form.getRacecode(), form.getSession());
		log.info("La api ha obtenido los resultados satisfactoriamente para year :" + form.getYear()+", category:" + form.getCategory());

		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//		if (todosLosResultadosDeUnaCarrera.size()==0) {
//			throw new NotFoundException("No se han encontrado carreras para los parametros dados");
//		} else {
			
			if (GpEstaEnCalendario==false) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(todosLosResultadosDeUnaCarrera.get(0).getFecha(), formatter);			
				gp.setSite(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
				gp.setCircuit(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
				gp.setDate0(date);
				gp.setRaceCode(todosLosResultadosDeUnaCarrera.get(0).getRaceCode());
				gp.setHasBeenRun(true);
				gp.setCalendar(true);
				try {
					this.gpService.populateRecord(gp);
				} catch (Exception e) {
					System.out.println("Sorry, records are unavailable for this GP!"); // Provisional
				}
				this.gpService.saveGP(gp);	
			}

			for (int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
				InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
				if (GpEstaEnCalendario==false) {
					gp.setCircuit(resultado_k.getLugar());
					gp.setSite(resultado_k.getNombreEvento());
				}

				Pilot pilot = new Pilot();
				pilot.setName(resultado_k.getPiloto().split(" ")[0]);
				pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
				pilot.setDorsal(resultado_k.getNumeros().toString());

				if (resultado_k.getPais().isEmpty()) {
					pilot.setNationality("Andorra");
				} else {
					pilot.setNationality(resultado_k.getPais());
				}

				pilot.setCategory(resultado_k.getCategory());

				Random random = new Random();
				pilot.setBaseValue(random.nextInt(3000) + 1000);//Valores arbitrarios, pueden cambiar

				Result result = new Result();

				if (this.countByName(pilot.getLastName(), pilot.getName())!=0) {
					result.setPilot(this.pilotRepository.findByName(pilot.getLastName(), pilot.getName()).get());
				} else {
					result.setPilot(pilot);
				}
				
				result.setPosition(resultado_k.getPosicion());
				result.setGp(gp);
				result.setLap(false);
				result.setPole(false);
				this.resultService.saveResult(result);
				Set<Result> results = new HashSet<Result>();
				results.add(result);
				pilot.setResults(results);
				this.savePilot(pilot);
			}
			log.info("La api ha asociado los resultados al gp : " + gp.getSite());
//		}
	}			
		
	@Transactional
	public Optional<Pilot> findPilotById(int pilotId) {
		return pilotRepository.findById(pilotId);
	}

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

	@Transactional(readOnly = true)	
	public Page<Pilot> findAllPage(Pageable pageable) throws DataAccessException {
		return pilotRepository.findAllPage(pageable);
	}	

	public List<Pilot> getRecruits() throws DataAccessException {
		return this.pilotRepository.findAllRecruits();
	}
	
	public List<Pilot> getRecruits(int teamID) throws DataAccessException {
		return this.pilotRepository.findAllRecruits(teamID);
	}
}
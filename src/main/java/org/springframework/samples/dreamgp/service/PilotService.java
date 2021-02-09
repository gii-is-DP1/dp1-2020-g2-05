package org.springframework.samples.dreamgp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dreamgp.model.BDCarrera;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.repository.PilotRepository;
import org.springframework.samples.dreamgp.web.formatter.ResultFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


import motogpApiV2.results.Competitor;
import motogpApiV2.testing.testing;

@Service
public class PilotService {
	
	private PilotRepository pilotRepository;
	private ResultService resultService;
	
	@Autowired
	public PilotService(PilotRepository pilotRepository, ResultService resultService) {
		this.pilotRepository = pilotRepository;
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
	
	public List<String> obtenerResultsFormatted(Set<Result> result){
		ResultFormatter formatter = new ResultFormatter(resultService);
		List<String> res = new ArrayList<String>();
		for (Result r:result) {
			res.add(formatter.print(r, Locale.ENGLISH));
		}
		return res;
	}
	public List<Pilot> top10Pilots(Category category){
		return this.pilotRepository.findTop3ByPointsAndCategory(category);
	}		
	
	public void poblarUnaCarreraYSusResultados(BDCarrera form,GranPremio gp) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		List<Competitor> listaDeRiders =testing.findCompetitorAndItsResultsByCategoryRaceCodeAndYear(form.getYear(), form.getRacecode(), form.getCategory());

		Integer numeroDeRiders = listaDeRiders.size();

		for (int i=0;i<numeroDeRiders;i++) {
			Competitor rider_i = listaDeRiders.get(i);

			if (rider_i.getResult()==null) {
				motogpApiV2.results.Result resultNull = new motogpApiV2.results.Result();
				resultNull.setPosition(0);
				resultNull.setPoints(0);
				rider_i.setResult(resultNull);
			}

			Result result = new Result();
			result.setGp(gp);

			
			if (rider_i.getResult().getPosition()==null) {
				result.setPosition(0);
			} else {
				result.setPosition(rider_i.getResult().getPosition());
			}
			
			this.resultService.saveResult(result);
			
			if (this.countByName(rider_i.getName().split(", ")[0], rider_i.getName().split(", ")[1])!=0) {

				this.updatePilotResults(rider_i, result);

				if (rider_i.getResult().getPoints()==null) {
					this.updatePilot(rider_i, 0);
				} else {
					this.updatePilot(rider_i, rider_i.getResult().getPoints());
				}

			} else {
				this.createNewPilot(rider_i,form.getCategory());

				this.assignResultToAPilot(rider_i, result);

				if (rider_i.getResult().getPoints()==null) {
					this.updatePilot(rider_i, 0);
				} else {
					this.updatePilot(rider_i, rider_i.getResult().getPoints());
				}
			}
		}
	}
	
	
	@Transactional
	public Optional<Pilot> findPilotById(int pilotId) {
		return pilotRepository.findById(pilotId);
	}

	@Transactional
	public void savePilot(Pilot pilot) throws DataAccessException {
			pilotRepository.save(pilot);
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
	
	public void createNewPilot(Competitor rider_i,Category categoryFromForm) {
		Pilot pilotAGuardar = new Pilot();
		pilotAGuardar.setName(rider_i.getName().split(", ")[1]);
		pilotAGuardar.setLastName(rider_i.getName().split(", ")[0]);
		pilotAGuardar.setNationality(rider_i.getNationality());
		pilotAGuardar.setCategory(categoryFromForm);
		pilotAGuardar.setPoints(0);
		Random random = new Random();
		pilotAGuardar.setBaseValue(random.nextInt(3000) + 1000);
		this.savePilot(pilotAGuardar);
	}
	public void updatePilot(Competitor rider_i,Integer puntos) {
		Pilot pilotAGuardar = this.findByName(rider_i.getName().split(", ")[0], rider_i.getName().split(", ")[1]).get();
		Integer suma=pilotAGuardar.getPoints()+puntos;
		pilotAGuardar.setPoints(suma);
		this.savePilot(pilotAGuardar);
	}
	
	public void assignResultToAPilot(Competitor rider_i,Result result) {
		Pilot pilotAGuardar = this.findByName(rider_i.getName().split(", ")[0], rider_i.getName().split(", ")[1]).get();
		Set<Result> results = new HashSet<Result>();
		results.add(result);
		result.setPilot(pilotAGuardar);
		pilotAGuardar.setResults(results);
		this.savePilot(pilotAGuardar);
		this.resultService.saveResult(result);
	}
	
	
	public void updatePilotResults(Competitor rider_i,Result result) {
		Pilot pilotAGuardar = this.findByName(rider_i.getName().split(", ")[0], rider_i.getName().split(", ")[1]).get();
		pilotAGuardar.addResult(result);
		result.setPilot(pilotAGuardar);
		this.savePilot(pilotAGuardar);
		this.resultService.saveResult(result);
	}
}
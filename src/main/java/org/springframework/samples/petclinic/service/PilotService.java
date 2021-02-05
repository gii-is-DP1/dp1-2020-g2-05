package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.security.SecureRandom;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.web.ResultFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
//import motogpAPI.PeticionesGet;

import motogpApiV2.results.Competitor;
import motogpApiV2.testing.testing;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Slf4j
@Service
public class PilotService {
	
//	public static final Integer MAXIMO_CARRERAS=18;
	
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
	
//	public void poblarBD(FormRellenarBD form) throws JSONException, IOException, ParseException {
////		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");		
//		for(int i=form.getAnyoInicial(); i < form.getAnyoFinal(); i++) {
//			for (int j=0;j<MAXIMO_CARRERAS;j++) {
//				GranPremio gp = new GranPremio(); //entidad de una carrera
//				List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceNumberCampu(form.getCategory(), i, j, Session.RACE);
////				if(todosLosResultadosDeUnaCarrera.size()==0) {
////					throw new NotFoundException("No se han encontrado carreras para los anyos dados");
////				}else {
//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//					LocalDate date = LocalDate.parse(todosLosResultadosDeUnaCarrera.get(0).getFecha(), formatter);				
//					gp.setSite(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
//					gp.setCircuit(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
//					gp.setDate0(date);
//					gp.setRaceCode(todosLosResultadosDeUnaCarrera.get(0).getRaceCode());
//					gp.setCalendar(false);
//					this.gpService.saveGP(gp);
//
//					for (int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
//						InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
//						gp.setCircuit(resultado_k.getLugar());
//						gp.setSite(resultado_k.getNombreEvento());
//
//						Pilot pilot = new Pilot();
//						pilot.setName(resultado_k.getPiloto().split(" ")[0]);
//						pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
//						pilot.setDorsal(resultado_k.getNumeros().toString());
//
//						if (resultado_k.getPais().isEmpty()) {pilot.setNationality("Andorra");} 
//						else {pilot.setNationality(resultado_k.getPais());}
//
//						pilot.setCategory((form.getCategory()));
//
//						Random random = new Random();
//						pilot.setBaseValue(random.nextInt(3000) + 1000);//Valores arbitrarios, pueden cambiar
//
//						Result result = new Result();
//
//						if (this.countByName(pilot.getLastName(), pilot.getName())!=0) {
//							result.setPilot(this.pilotRepository.findByName(pilot.getLastName(), pilot.getName()).get());
//						} else {
//							result.setPilot(pilot);
//						}
//						result.setPosition(resultado_k.getPosicion());
//						result.setGp(gp);
//						result.setLap(false);
//						result.setPole(false);
//						this.resultService.saveResult(result);
//						savePilot(pilot);
//					}
//				}
//			}			
//		}
////	}
//	
//	@Transactional
//	//	2016, RaceCode.AUT, Session.RACE
//	public void poblarBDCarreraACarrera(BDCarrera form,GranPremio gp,Boolean GpEstaEnCalendario) throws JSONException, IOException, ParseException {
//
//		//EL GP QUE SE PASA COMO PARAMETRO, O ESTA VACIO, O ESTA EN EL CALENDARIO
//		//				GranPremio gp = new GranPremio(); //entidad de una carrera
//		List<InfoCarrera> todosLosResultadosDeUnaCarrera = PeticionesGet.getResultsByRaceCodeCampu(form.getCategory(), form.getYear(), form.getRacecode(), form.getSession());
//		log.info("La api ha obtenido los resultados satisfactoriamente para year :" + form.getYear()+", category:" + form.getCategory());
//
//		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
////		if (todosLosResultadosDeUnaCarrera.size()==0) {
////			throw new NotFoundException("No se han encontrado carreras para los parametros dados");
////		} else {
//			
//			if (GpEstaEnCalendario==false) {
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//				LocalDate date = LocalDate.parse(todosLosResultadosDeUnaCarrera.get(0).getFecha(), formatter);			
//				gp.setSite(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
//				gp.setCircuit(todosLosResultadosDeUnaCarrera.get(0).getNombreEvento());
//				gp.setDate0(date);
//				gp.setRaceCode(todosLosResultadosDeUnaCarrera.get(0).getRaceCode());
//				gp.setHasBeenRun(true);
//				gp.setCalendar(true);
//				try {
//					this.gpService.populateRecord(gp);
//				} catch (Exception e) {
//					System.out.println("Sorry, records are unavailable for this GP!"); // Provisional
//				}
//				this.gpService.saveGP(gp);	
//			}
//
//			for (int k=0;k<todosLosResultadosDeUnaCarrera.size();k++) {
//				InfoCarrera resultado_k = todosLosResultadosDeUnaCarrera.get(k);
//				if (GpEstaEnCalendario==false) {
//					gp.setCircuit(resultado_k.getLugar());
//					gp.setSite(resultado_k.getNombreEvento());
//				}
//
//				Pilot pilot = new Pilot();
//				pilot.setName(resultado_k.getPiloto().split(" ")[0]);
//				pilot.setLastName(resultado_k.getPiloto().split(" ")[1]);
//				pilot.setDorsal(resultado_k.getNumeros().toString());
//
//				if (resultado_k.getPais().isEmpty()) {
//					pilot.setNationality("Andorra");
//				} else {
//					pilot.setNationality(resultado_k.getPais());
//				}
//
//				pilot.setCategory(resultado_k.getCategory());
//
//				Random random = new Random();
//				pilot.setBaseValue(random.nextInt(3000) + 1000);//Valores arbitrarios, pueden cambiar
//
//				Result result = new Result();
//
//				if (this.countByName(pilot.getLastName(), pilot.getName())!=0) {
//					result.setPilot(this.pilotRepository.findByName(pilot.getLastName(), pilot.getName()).get());
//				} else {
//					result.setPilot(pilot);
//				}
//				
//				result.setPosition(resultado_k.getPosicion());
//				result.setGp(gp);
//				result.setLap(false);
//				result.setPole(false);
//				this.resultService.saveResult(result);
//				Set<Result> results = new HashSet<Result>();
//				results.add(result);
//				pilot.setResults(results);
//				savePilot(pilot);
//			}
//			log.info("La api ha asociado los resultados al gp : " + gp.getSite());
////		}
//	}			
//		
	
	
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
			result.setLap(false);
			result.setPole(false);
			
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
		pilotAGuardar.setPoints("0");
		Random random = new Random();
		pilotAGuardar.setBaseValue(random.nextInt(3000) + 1000);//Valores arbitrarios, pueden cambiar
		this.savePilot(pilotAGuardar);
	}
	public void updatePilot(Competitor rider_i,Integer puntos) {
		Pilot pilotAGuardar = this.findByName(rider_i.getName().split(", ")[0], rider_i.getName().split(", ")[1]).get();
		Integer suma=Integer.parseInt(pilotAGuardar.getPoints())+puntos;
		pilotAGuardar.setPoints(suma.toString());
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
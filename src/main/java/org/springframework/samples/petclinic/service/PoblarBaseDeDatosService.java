package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.web.PoblarBaseDeDatosController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import motogpApiV2.RaceCode;
import motogpApiV2.Session;
import motogpApiV2.results.Competitor;
import motogpApiV2.testing.testing;

@Slf4j
@Service
@Transactional
public class PoblarBaseDeDatosService {

	private GranPremioService gpService; 
	private PilotService pilotService;
	private ResultService resultService;

	
	@Autowired
	public PoblarBaseDeDatosService(PilotService pilotService,ResultService resultService,GranPremioService gpService) {
		this.pilotService = pilotService;
		this.resultService = resultService;
		this.gpService =gpService;
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
			
			if (this.pilotService.countByName(rider_i.getName().split(", ")[0], rider_i.getName().split(", ")[1])!=0) {

				this.pilotService.updatePilotResults(rider_i, result);

				if (rider_i.getResult().getPoints()==null) {
					this.pilotService.updatePilot(rider_i, 0);
				} else {
					this.pilotService.updatePilot(rider_i, rider_i.getResult().getPoints());
				}

			} else {
				this.pilotService.createNewPilot(rider_i,form.getCategory());

				this.pilotService.assignResultToAPilot(rider_i, result);

				if (rider_i.getResult().getPoints()==null) {
					this.pilotService.updatePilot(rider_i, 0);
				} else {
					this.pilotService.updatePilot(rider_i, rider_i.getResult().getPoints());
				}
			}
		}
	}
	
	public void poblandoUltimaCarreraCompletada() throws Exception {
//		Integer mesactual = LocalDate.now().getMonthValue();
//		Integer añoactual = LocalDate.now().getYear();
//		Integer diaactual = LocalDate.now().getDayOfMonth();
		Integer mesactual = 3;
		Integer añoactual = 2019;
		Integer diaactual = 29;
		LocalDate fecha = LocalDate.of(añoactual, mesactual, diaactual);
		GranPremio gp = new GranPremio();
		System.out.println(fecha);
		try {
			gp =this.gpService.ultimoGpCorrido(fecha);
		} catch (Exception e) {
			throw new Exception("No se ha encontrado gp sin completar con la fecha de hoy.");
		}
		System.out.println(gp);
		
		BDCarrera form = new BDCarrera();
		form.setCategory(Category.MOTO3);
		form.setRacecode(RaceCode.valueOf(gp.getRaceCode()));
		form.setSession(Session.RACE);
		form.setYear(añoactual);
		this.poblarUnaCarreraYSusResultados(form, gp);
		
	}
	
}
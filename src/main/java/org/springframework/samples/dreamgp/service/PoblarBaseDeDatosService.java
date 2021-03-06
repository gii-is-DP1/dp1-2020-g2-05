package org.springframework.samples.dreamgp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.BDCarrera;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.web.PoblarBaseDeDatosController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import motogpApiV2.RaceCode;
import motogpApiV2.Session;
import motogpApiV2.apiCore.ApiMain;
import motogpApiV2.results.Competitor;
@Slf4j
@Service
@Transactional
public class PoblarBaseDeDatosService {

	private TablaConsultasService TCService;
	private GranPremioService gpService; 
	private PilotService pilotService;
	private ResultService resultService;

	
	@Autowired
	public PoblarBaseDeDatosService(PilotService pilotService,ResultService resultService,GranPremioService gpService,TablaConsultasService TCService) {
		this.pilotService = pilotService;
		this.resultService = resultService;
		this.gpService =gpService;
		this.TCService = TCService;
	}
	
	
	
	public void poblarUnaCarreraYSusResultados(BDCarrera form,GranPremio gp) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		List<Competitor> listaDeRiders =ApiMain.findCompetitorAndItsResultsByCategoryRaceCodeAndYear(form.getYear(), form.getRacecode(), form.getCategory());

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
		
		try {
			this.gpService.populateRecord(gp);
		} catch (Exception e) {

		}
	}
	
	public void poblandoUltimaCarreraCompletada() throws Exception {
		Integer mesactual = 3;//LocalDate.now().getMonthValue();
		Integer añoactual = 2019;//LocalDate.now().getYear();
		Integer diaactual = 8;//LocalDate.now().getDayOfMonth();
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
		try {
			this.poblarUnaCarreraYSusResultados(form, gp);
		} catch (FileNotFoundException e) {
			log.warn("API has not found any result to code " + gp.getRaceCode() + " for moto3 ");
			

		}


		try {
			form.setCategory(Category.MOTO2);
			this.poblarUnaCarreraYSusResultados(form, gp);
		} catch (FileNotFoundException e) {
			log.warn("API has not found any result to code " + gp.getRaceCode() + " for moto2 ");

		}
		
		
		try {
			form.setCategory(Category.MOTOGP);
			this.poblarUnaCarreraYSusResultados(form, gp);
		} catch (FileNotFoundException e) {
			log.warn("API has not found any result to code " + gp.getRaceCode() + " for motogp ");

		}
		
	

		this.TCService.actualizarTabla(Category.MOTO2);
		this.TCService.actualizarTabla(Category.MOTO3);
		this.TCService.actualizarTabla(Category.MOTOGP);
		gp.setHasBeenRun(true);
		gpService.saveGP(gp);
		
		
	}
	
	public List<GranPremio> findAllActualYear(Integer year) throws ParseException {
		return this.gpService.findAllActualYear(year);
	}
	
}
package org.springframework.samples.dreamgp.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.BDCarrera;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.service.GranPremioService;
import org.springframework.samples.dreamgp.service.PoblarBaseDeDatosService;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.samples.dreamgp.web.validator.BDValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import motogpApiV2.RaceCode;
import motogpApiV2.Session;


@Slf4j
@Controller
public class PoblarBaseDeDatosController {

	TeamService teamService;
	GranPremioService GPService;
	TablaConsultasService TCService;
	PoblarBaseDeDatosService poblarBaseDeDatosService;

	@Autowired
	public PoblarBaseDeDatosController(
			GranPremioService GPService, TablaConsultasService TCService, TeamService teamService,
			PoblarBaseDeDatosService poblarBaseDeDatosService) {
		this.GPService = GPService;
		this.TCService = TCService;
		this.teamService = teamService;
		this.poblarBaseDeDatosService = poblarBaseDeDatosService;
	}



	@InitBinder("form")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BDValidator());
	}

	@GetMapping(path = "/BD/carrerasBD/{date}/{code}/{id}")
	public String actualizarTablaGPs(@PathVariable("date") String date, @PathVariable("code") String code,
			@PathVariable("id") String id, ModelMap model) throws JSONException, IOException, ParseException,
			InterruptedException, NotAllowedNumberOfRecruitsException {
		GranPremio gp = this.GPService.findGPById(Integer.parseInt(id)).get();
		gp.setHasBeenRun(true);
		return PoblarBDCarreras(date, code, model, gp);
	}

	@GetMapping(path = "/BD/carrerasBD/{date}/{code}")
	public String PoblarBDCarreras(@PathVariable("date") String date, @PathVariable("code") String code, ModelMap model,
			GranPremio gp) throws JSONException, IOException, ParseException, InterruptedException,
			NotAllowedNumberOfRecruitsException {
		BDCarrera form = new BDCarrera();
		form.setCategory(Category.MOTO3);
		form.setRacecode(RaceCode.valueOf(code));
		form.setSession(Session.RACE);
		form.setYear(Integer.parseInt((date.split("-")[0])));
		log.info("Intentando obtener resultados para gp :" + gp);
		Integer contador = 0;
		String notFound = "";
		
		try {
			this.poblarBaseDeDatosService.poblarUnaCarreraYSusResultados(form, gp);
		} catch (FileNotFoundException e) {
			model.addAttribute("messageMoto3NotFound",
					"API has not found any result to date " + date + " and code " + code + " for moto3 ");
			log.warn("API has not found any result to date " + date + " and code " + code + " for moto3 ");
			contador++;
			notFound += "3";
		}
		log.info("Resultados obtenidos :" + gp);

		try {
			form.setCategory(Category.MOTO2);
			this.poblarBaseDeDatosService.poblarUnaCarreraYSusResultados(form, gp);
		} catch (FileNotFoundException e) {
			model.addAttribute("messageMoto2NotFound",
					"API has not found any result to date " + date + " and code " + code + " for moto2");
			log.warn("API has not found any result to date " + date + " and code " + code + " for moto2 ");
			contador++;
			notFound += "2";

		}

		try {
			form.setCategory(Category.MOTOGP);
			this.poblarBaseDeDatosService.poblarUnaCarreraYSusResultados(form, gp);
		} catch (Exception e) {
			model.addAttribute("messageMotogpNotFound",
					"API has not found any result to date " + date + " and code " + code + " for motogp");
			log.warn("API has not found any result to date " + date + " and code " + code + " for motogp ");
			contador++;
			notFound += "G";
		}

		try {
			this.GPService.populateRecord(gp);
		} catch (Exception e) {

		}
		Category category = TCService.getTabla().get().getCurrentCategory();

		this.TCService.actualizarTabla(Category.MOTO2);
		this.TCService.actualizarTabla(Category.MOTO3);
		this.TCService.actualizarTabla(Category.MOTOGP);

		Category newCategory = TCService.getTabla().get().getCurrentCategory();

		if (!category.equals(newCategory)) {
			List<Team> teams = new ArrayList<Team>();
			teamService.findAllTeams().forEach(teams::add);
			log.info(
					"Se ha detectado el cambio de categoria, se procede a vender todos los pilotos y configurar el mercado");
			for (int i = 0; i < teams.size(); i++) {
				Team team = teams.get(i);
				teamService.sellAllTeamRecruits(team);
				if (team.getName().equals("Sistema")) {
					teamService.recruitAndOfferAll(team, newCategory);
				}
			}

			log.info("Ahora se va asignar 2 pilotos de la nueva categoria a cada equipo");
			for (int i = 0; i < teams.size(); i++) {
				Team team = teams.get(i);
				if (!team.getName().equals("Sistema")) {
					teamService.randomRecruit2Pilots(team);
				}
			}
		}

		return "redirect:/controlPanel/" + contador + notFound + "/" + code;
	}
}
package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import motogpAPI.RaceCode;
import motogpAPI.Session;

@Slf4j
@Controller
public class PoblarBaseDeDatosController {

	LeagueController leagueController;
	LeagueService leagueService;
	PanelDeControlController PController;
	UserService userService;
	GranPremioService GPService;
	PilotService pilotService;
	TablaConsultasService TCService;

	@Autowired
	public PoblarBaseDeDatosController(LeagueService leagueService, UserService userService, PilotService pilotService,
			LeagueController leagueController, PanelDeControlController PController, GranPremioService GPService,
			TablaConsultasService TCService) {
		this.leagueService = leagueService;
		this.userService = userService;
		this.pilotService = pilotService;
		this.leagueController = leagueController;
		this.PController = PController;
		this.GPService = GPService;
		this.TCService = TCService;
	}

	private Boolean messageNullPointerException = false;
	private BDCarrera formError = new BDCarrera();

	@InitBinder("form")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BDValidator());
	}

	@GetMapping(path = "/BD")
	public String seleccionarTipoPoblacion(ModelMap model) {
		return "/BD/seleccionarTipoPoblacion";
	}

	@GetMapping(path = "/BD/pilotsBD")
	public String PoblarBD(ModelMap model) {
		model.addAttribute("FormRellenarBD", new FormRellenarBD());
		Category[] yourEnums = Category.values();
		List<Category> num = new ArrayList<>();

		for (int i = 0; i < yourEnums.length; i++) {
			num.add(yourEnums[i]);
		}
		model.addAttribute("listaCategoria", num);

		return "/BD/BD";
	}

	@PostMapping(path = "/BD/pilotsBD")
	public String Poblar(@Valid FormRellenarBD form, BindingResult result, ModelMap model)
			throws JSONException, IOException, ParseException {
		System.out.println(result);

		if (result.hasErrors()) {
			System.out.println(result);
			model.put("FormRellenarBD", form);
			model.put("message", result.getAllErrors());
			return "/BD/BD";
		} else {
			try {
				this.pilotService.poblarBD(form);
			} catch (NullPointerException e) {
				return "redirect:/BD/pilotsBD";
			}
			return "redirect:/pilots";
		}

	}

	@GetMapping(path = "/BD/carrerasBD")
	public String PoblarBDCarreras(ModelMap model) {
		model.addAttribute("BDCarrera", new BDCarrera());
		Category[] yourEnums = Category.values();
		List<Category> num = new ArrayList<>();

		for (int i = 0; i < yourEnums.length; i++) {
			num.add(yourEnums[i]);
		}

		motogpAPI.RaceCode[] enums = motogpAPI.RaceCode.values();
		List<String> num1 = new ArrayList<>();

		for (int i = 0; i < enums.length; i++) {
			num1.add(enums[i].toString());
		}

		Collections.sort(num1);
		System.out.println(num1);
		motogpAPI.Session[] enums2 = motogpAPI.Session.values();
		List<motogpAPI.Session> num2 = new ArrayList<>();

		for (int i = 0; i < enums2.length; i++) {
			num2.add(enums2[i]);
		}
		model.addAttribute("listaCategoria", num);
		model.addAttribute("listaRacecode", num1);
		model.addAttribute("listaSession", num2);

		if (messageNullPointerException)
			model.put("messageNullPointerException", true);
		messageNullPointerException = false;
		if (formError.getYear() != null)
			model.addAttribute("BDCarrera", formError);
		formError = new BDCarrera();

		return "/BD/BDCarrera";
	}

	@PostMapping(path = "/BD/carrerasBD")
	public String PoblarBDcarrera(@Valid BDCarrera form, BindingResult result, ModelMap model)
			throws JSONException, IOException, DataAccessException, duplicatedLeagueNameException, ParseException {

		if (result.hasErrors()) {
			System.out.println(result);
			model.put("BDCarrera", form);
			model.put("message", result.getAllErrors());
			return "/BD/BD";
		} else {
			try {
				this.pilotService.poblarBDCarreraACarrera(form, new GranPremio(), false);
			} catch (NullPointerException e) {
				messageNullPointerException = true;
				formError = form;
				return "redirect:/BD/carrerasBD";

			}
			model.addAttribute("category", form.getCategory());
			return this.leagueController.incrementarLiga(model);
		}

	}

	@GetMapping(path = "/BD/carrerasBD/{date}/{code}/{id}")
	public String actualizarTablaGPs(@PathVariable("date") String date, @PathVariable("code") String code,
			@PathVariable("id") String id, ModelMap model) throws JSONException, IOException, ParseException {
		GranPremio gp = this.GPService.findGPById(Integer.parseInt(id)).get();
		gp.setHasBeenRun(true);
		return PoblarBDCarreras(date, code, model, gp);
	}

	@GetMapping(path = "/BD/carrerasBD/{date}/{code}")
	public String PoblarBDCarreras(@PathVariable("date") String date, @PathVariable("code") String code, ModelMap model,
			GranPremio gp) throws JSONException, IOException, ParseException {
		BDCarrera form = new BDCarrera();
		form.setCategory(Category.MOTO3);
		form.setRacecode(RaceCode.valueOf(code));
		form.setSession(Session.RACE);
		form.setYear(Integer.parseInt((date.split("-")[0])));
		log.info("Intentando obtener resultados para gp :" + gp);
		try {
			this.pilotService.poblarBDCarreraACarrera(form, gp, true);
		} catch (Exception e) {
			model.addAttribute("messageMoto3NotFound",
					"API has not found any result to date " + date + " and code " + code + " for moto3 ");
		}
		log.info("Resultados obtenidos :" + gp);

		
		try {
			form.setCategory(Category.MOTO2);
			this.pilotService.poblarBDCarreraACarrera(form, gp, true);
		} catch (Exception e) {
			model.addAttribute("messageMoto2NotFound",
					"API has not found any result to date " + date + " and code " + code + " for moto2");
		}
		

		try {
			form.setCategory(Category.MOTOGP);
			this.pilotService.poblarBDCarreraACarrera(form, gp, true);
		} catch (Exception e) {
			model.addAttribute("messageMotogpNotFound",
					"API has not found any result to date " + date + " and code " + code + " for motogp");
		}
		try {
			this.GPService.populateRecord(gp);
		} catch (Exception e) {

		}
		this.TCService.actualizarTabla(Category.MOTO2);
		this.TCService.actualizarTabla(Category.MOTO3);
		this.TCService.actualizarTabla(Category.MOTOGP);
		return PController.muestraPanel(model);
	}

}
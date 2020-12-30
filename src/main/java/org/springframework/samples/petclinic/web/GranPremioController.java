package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.ResultService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.java.Log;
import motogpAPI.Category;
import motogpAPI.PeticionesGet;
import motogpAPI.RaceCode;
import motogpAPI.Session;
import motogpAPI.model.InfoCarrera;

@Controller
public class GranPremioController {
	
	GranPremioService GPService;
	TablaConsultasService TCService;

	
	@Autowired
	public GranPremioController(GranPremioService GPService,TablaConsultasService TCService) {
		this.GPService = GPService;
		this.TCService=TCService;
	}
	
//	@InitBinder("granpremio")
//	public void initGPBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new GranPremioValidator());
//	}


	@GetMapping(path="/granPremios")
	public String detallesLiga(ModelMap model) throws ParseException {	
//		List<GranPremio> gps = GPService.convertirIterableLista(GPService.findAllActualYear(2019));
//		List<List<GranPremio>> gps_calificados = GranPremioService.granPremiosPorCategoria(gps);
//		List<GranPremio> motogp = gps_calificados.get(0);
//		List<GranPremio> moto2 = gps_calificados.get(1);
//		List<GranPremio> moto3 = gps_calificados.get(2);
		List<GranPremio> gps = GPService.findAllActualYear(2020).stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
		model.addAttribute("listaGP",gps.stream().sorted(Comparator.comparing(GranPremio::getId)).collect(Collectors.toList()));
		model.addAttribute("racesCompleted",this.TCService.getTabla().get().getRacesCompleted());
		return "/gp/gpList";
		 
	}
	
	@GetMapping(path="/granPremios/new")
	public String nuevoGranPremio(ModelMap model) {	
		model.addAttribute("GranPremio",new GranPremio());
		return "/gp/nuevoGP";
	}
	
	@PostMapping(path="/granPremios/new")
	public String nuevoGranPremio(@Valid GranPremio granpremio,BindingResult results,ModelMap model) {	
		
		if(results.hasErrors()) {
			model.addAttribute("errors",results.getAllErrors());
			return nuevoGranPremio(model);
		}else {
			granpremio.setCalendar(true);
			granpremio.setHasBeenRun(false);
			this.GPService.saveGP(granpremio);
			model.addAttribute("message","Gran Premio loaded succesfully!");
		}
		
		

		return "redirect:/controlPanel";
	}
	
	@RequestMapping(path="/granPremios/setRecords/{gpId}")
	public String populateRecords(@PathVariable("gpId") int gpId, ModelMap model) throws IOException {
		GranPremio gp = this.GPService.findGPById(gpId).get();
		this.GPService.populateRecord(gp);
		this.GPService.saveGP(gp);
		return "gp/gpList";
	}
	
}

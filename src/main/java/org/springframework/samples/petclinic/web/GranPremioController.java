package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.jasper.tagplugins.jstl.core.Set;
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


	
	@Autowired
	public GranPremioController(GranPremioService GPService) {
		this.GPService = GPService;
	}


	
	
	
	
//	@InitBinder("league")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new LeagueValidator());
//	}
	

	@GetMapping(path="/granPremios")
	public String detallesLiga(ModelMap model) {	
//		List<GranPremio> gps = GPService.convertirIterableLista(GPService.findAllActualYear(2019));
		List<GranPremio> gps = GPService.findAllActualYear(2018);
		List<List<GranPremio>> gps_calificados = GranPremioService.granPremiosPorCategoria(gps);
		List<GranPremio> motogp = gps_calificados.get(0);
		List<GranPremio> moto2 = gps_calificados.get(1);
		List<GranPremio> moto3 = gps_calificados.get(2);
		
		
		model.addAttribute("listaGP",motogp);
		model.addAttribute("lista2",moto2);
		model.addAttribute("lista3",moto3);
		return "/gp/gpList";
		 
	} //DE MOMENTO NO HACE NADA

	
}

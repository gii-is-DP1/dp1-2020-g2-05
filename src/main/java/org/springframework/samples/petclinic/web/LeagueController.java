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
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.JSONException;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.ResultService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.petclinic.model.Category;
import motogpAPI.RaceCode;
import motogpAPI.Session;
import motogpAPI.model.InfoCarrera;

@Controller
public class LeagueController{

//	private Boolean yaTienesEquipo=false; //si ya tiene equipo en esa liga
//	private Boolean noLeagueFound=false; //si no se ha encontrado liga
//	private Integer leagueYaEquipoId=-1; // para que te señale la liga
//	private Boolean yaTieneMaxTeams=false; // no puedes unirte a esa liga
	private String AUTHORITY;

	TablaConsultasService TCService;
	LeagueService leagueService;
	UserService userService;
	
	@Autowired
	public LeagueController(LeagueService leagueService, UserService userService,TablaConsultasService TCService) {
		this.leagueService = leagueService;
		this.userService = userService;
		this.TCService = TCService;
	}


	
	
	
	
	@InitBinder("league")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LeagueValidator());
	}
	

	
	@GetMapping("/leagues")
	public String leagues(ModelMap modelMap) throws JSONException, IOException {
		User user = this.userService.getUserSession();
		AUTHORITY = this.leagueService.findAuthoritiesByUsername(user.getUsername());

		Optional<TablaConsultas> tabla = this.TCService.getTabla();
		
		List<League> result = leagueService.convertirIterableLista(leagueService.findAll());
	  
		if(this.leagueService.comprobarLigaVacia(result)) { //si ha eliminado a una liga da true y refresco la pagina
			return leagues(modelMap);
		}
		
	    if(AUTHORITY.equals("admin")) {
		modelMap.addAttribute("admin", true);
	    }else if(!AUTHORITY.equals("admin")) {
		modelMap.addAttribute("user", true);	
	    }
	 
		modelMap.addAttribute("ligas", result);
		modelMap.addAttribute("categoriaActual", tabla.get().getCurrentCategory());
		modelMap.addAttribute("carrerasCompletadas",tabla.get().getRacesCompleted());
		return "leagues/leagueList";
	}
	
	
	@GetMapping("/leagues/myLeagues")
	public String myLeagues(ModelMap modelMap) {
		User user = this.userService.getUserSession();
		
		List<League> myLeaguesList = new ArrayList<League>();

		if(Optional.of(user).isPresent()) {
			 myLeaguesList = leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername())); //obtengo las ligas por usuario
		}
		
	    
	
	    try {
//	    	if(modelMap.getAttribute("vengoDeAbajo").equals(true)) {
//		   		 modelMap.addAttribute("vengoDeAbajo",false);
//		    	return myLeagues(modelMap);
//		    	} //si es necesario se descomenta
//	    	else {
	    		if(modelMap.getAttribute("yaTienesEquipo").equals(true)) {
					modelMap.addAttribute("leagueYaEquipoId", modelMap.getAttribute("leagueYaEquipoId"));
				
			    }
	    		
	    		
	    	
	    }catch (NullPointerException e) {
		}
		
	    if(myLeaguesList.size()==0) {
		    modelMap.addAttribute("noTengoLigas", true);
		    return "leagues/myLeagues";
	    }else {
		    modelMap.addAttribute("noTengoLigas", false);

		    modelMap.addAttribute("misLigas", myLeaguesList);

		    return "leagues/myLeagues";
	    }
	   
	}
	
	
	
	@GetMapping(path="/leagues/increase")
	public String incrementarLiga(ModelMap model) throws DataAccessException, duplicatedLeagueNameException {	
//		AUTHORITY = this.leagueService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());
//		if(!(AUTHORITY.equals("admin"))) {
//			return "redirect:/leagues";
//		}
		Category category = Category.valueOf(model.getAttribute("category").toString());
		
		
		TCService.actualizarTabla(category);
		
//		this.leagueService.updateGPsFromLeagueWithCategory();
		
		return "redirect:/leagues";
	}
	
	@GetMapping(path="/leagues/new")
	public String initcrearLiga(ModelMap model) throws DataAccessException, duplicatedLeagueNameException {	
		User user = this.userService.getUserSession();
		
		if(!Optional.of(user).isPresent()) {
			return "/leagues/leagueList";
		}
		
		Integer num_leagues = leagueService.findLeaguesByUsername(user.getUsername());
		
		if(num_leagues>=5) {
			model.addAttribute("yaTieneMaxTeams",true);
			return myLeagues(model);
		}
		List<League> result = leagueService.convertirIterableLista(leagueService.findAll());
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    
	    
	    League newLeague = new League();
//	    newLeague.setId(result.get(result.size()-1).getId()+1);
	    newLeague.setLeagueCode(leagueService.randomString(10));
	    newLeague.setLeagueDate(formatter.format(date));
//	    newLeague.setActiveCategory(Category.MOTO3);
//	    newLeague.setRacesCompleted(0);
		model.addAttribute("league",newLeague);
		//borrar ligas sin equipos aqui para evitar que nos peten
		return "/leagues/createLeagueName";
		 }
	
	@PostMapping(path="/leagues/new")
	public String processCrearLiga(@Valid League league, BindingResult results,ModelMap model) {	
		if(results.hasErrors()) {
			model.put("league", league);
			model.put("message",results.getAllErrors());
			return "/leagues/createLeagueName";
		}else {

			
			
				
				
				  try {
						this.leagueService.saveLeague(league);
					}catch (duplicatedLeagueNameException e) {
	                    results.rejectValue("name", "duplicate", "already exists a league with that name");
	        			model.put("message",results.getAllErrors());
	                    return "/leagues/createLeagueName";
					}
				 
				 leagueService.saveSystemTeam(league);
			  

				 return "redirect:/leagues/"+league.getId()+"/teams/new";	
			
			
		}
	
	}
	
	@GetMapping(path="/leagues/join")
	public String unirseLiga(ModelMap model) {	
		User user = this.userService.getUserSession();
		
		if(!Optional.of(user).isPresent()) {
			return "/leagues/leagueList";
		}
		
		model.addAttribute("league", new League());

		Integer num_leagues = this.leagueService.findLeaguesByUsername("asdas");
		
		if(num_leagues==5) {
			model.addAttribute("yaTieneMaxTeams",true);
//			model.addAttribute("vengoDeAbajo",true);
			return myLeagues(model);
		}
		try {
			if(model.getAttribute("noLeagueFound").equals(true)) {
				model.addAttribute("noLeagueFound", "Any league has been found with the code provided :(");
				}
		}catch (NullPointerException e) {

		}
		
		//noLeagueFound=false;
		return "/leagues/createLeague";
		 }
	
	@PostMapping(value="/leagues/join")
	public String unirseLigaCode(League league,ModelMap model) {	
		User user = this.userService.getUserSession();
		
//		List<Integer> collect = leagueService.findTeamsByUsername(user.getUsername());
		
		List<Integer> idLeague = this.leagueService.findTeamsByUsername(user.getUsername());
			
		Optional<League> liga = this.leagueService.findLeagueByLeagueCode(league.getLeagueCode().trim());
				
		
		if(!liga.isPresent()) {
			model.addAttribute("noLeagueFound",true);
			return unirseLiga(model);
		}
		
		Integer numTeamsLeague = this.leagueService.findTeamsByLeagueId(liga.get().getId());
		
		if(idLeague.contains(liga.get().getId())) {
			model.addAttribute("yaTienesEquipo",true);
			model.addAttribute("leagueYaEquipoId",liga.get().getId());
//			model.addAttribute("vengoDeAbajo",true);
			return myLeagues(model);
		}
		
		if(numTeamsLeague>5) {
			model.addAttribute("yaTieneMaxTeams",true);
//			model.addAttribute("vengoDeAbajo",true);
			return myLeagues(model);
		}

		model.addAttribute("yaTienesEquipo", false);
		return "redirect:/leagues/"+liga.get().getId()+"/teams/new";
		 }
	
	@GetMapping(path="/leagues/{leagueId}/details")
	public String detallesLiga(@PathVariable("leagueId") int leagueId,ModelMap model) {	
		User user = userService.getUserSession();
		return "/leagues/leagueDetails";
		 } //DE MOMENTO NO HACE NADA

}

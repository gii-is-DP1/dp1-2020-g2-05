package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.apache.jasper.tagplugins.jstl.core.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
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

@Controller
public class LeagueController {

	private Boolean yaTienesEquipo=false;
	private Boolean noLeagueFound=false;
	private Integer leagueYaEquipoId=-1;
	private Boolean yaTieneMaxLigas=false;
	@Autowired

	LeagueService leagueService;
	@Autowired

	UserService userService;
	
		

	
	public String randomString(int longitud) {
		 String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		 String sb="";
		 Random random = new Random();
		

	    for(int i = 0; i < longitud; i++) {

	      int index = random.nextInt(alphabet.length());

	      char randomChar = alphabet.charAt(index);

	      sb+=(randomChar);
	    }
	    return sb;
	}
	
	
	

	
	@GetMapping("/leagues")
	public String leagues(ModelMap modelMap) {
		Iterable<League> leagues = leagueService.findAll() ;
		List<League> result = new ArrayList<League>();
	    leagues.forEach(result::add);
		
//		leagueService.activeMotogp(3);

	    for(League league:result) {
	    	if(league.getRacesCompleted()<10) leagueService.activeMoto3(league.getId());
	    	//activar moto3 si las carreras son > que 10
	    	else if(league.getRacesCompleted()>=10 && league.getRacesCompleted()<15 ) leagueService.activeMoto2(league.getId());
	    	//activar moto2 si las carreras son >= que 10 y < 15
	    	else if(league.getRacesCompleted()>=15 )leagueService.activeMotogp(league.getId());
	    	//activar motogp si las carreras son >= 15
	    	
	    	if(league.getRacesCompleted()>20) league.setRacesCompleted(20);
	    	
			if(league.getTeam().isEmpty()) leagueService.deleteLeague(league);

	    }
		
		modelMap.addAttribute("ligas", leagueService.findAll());
	
		return "leagues/leagueList";
	}
	
	
	@GetMapping("/leagues/myLeagues")
	public String myLeagues(ModelMap modelMap) {
		User user = userService.getUserSession();
		
		Collection<Integer> collect = leagueService.findTeamsByUsername(user.getUsername());
		
		List<Integer> idLeague = new ArrayList<Integer>();
		
		collect.forEach(idLeague::add);
	    List<League> myLeaguesList = new ArrayList<League>();

	    
		for(Integer i:idLeague) {
			League league_i = leagueService.findLeague(i).get();
			myLeaguesList.add(league_i);
		}
		
		
		
	    
	    if(yaTienesEquipo) {
	    	modelMap.addAttribute("yaTienesEquipo",true);yaTienesEquipo=false;
			modelMap.addAttribute("leagueYaEquipoId", leagueYaEquipoId);leagueYaEquipoId=-1;

	    }
	    
	    if(yaTieneMaxLigas) modelMap.addAttribute("yaTieneMaxLigas",true);yaTieneMaxLigas=false;

	    
	    modelMap.addAttribute("misLigas", myLeaguesList);
	    return "leagues/myLeagues";
	}
	
	
	
	@GetMapping(path="/leagues/{leagueId}/increase")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, ModelMap model) {	
		
		League league = leagueService.findLeague(leagueId).get();
		league.setRacesCompleted(league.getRacesCompleted()+1);
//		leagueService.incrementarCarrerasLiga(leagueId);
		model.addAttribute("ligas", leagueService.findAll());
		return "redirect:/leagues";
	}
	
	@GetMapping(path="/leagues/new")
	public String crearLiga(ModelMap model) {	
		
		Integer num_leagues = leagueService.findLeaguesByUsername(userService.getUserSession().getUsername());
		
		if(num_leagues==5) {
			yaTieneMaxLigas=true;
			return "redirect:/leagues/myLeagues";
		}
		
		Iterable<League> leagues = leagueService.findAll() ;
		List<League> result = new ArrayList<League>();
	    leagues.forEach(result::add);
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    
	    
	    League newLeague = new League();
	    newLeague.setId(result.get(result.size()-1).getId()+1);
	    newLeague.setLeagueCode(randomString(10));
	    newLeague.setLeagueDate(formatter.format(date));
	    newLeague.setMoto2Active(false);
	    newLeague.setMoto3Active(true);
	    newLeague.setMotogpActive(false);
	    newLeague.setName("Liga"+newLeague.getId().toString());
	    newLeague.setRacesCompleted(0);

	    leagueService.saveLeague(newLeague);

		 return "redirect:/leagues/"+newLeague.getId()+"/teams/new";	
		 }
	
	@GetMapping(path="/leagues/join")
	public String unirseLiga(ModelMap model) {	
		model.addAttribute("league", new League());

		if(noLeagueFound) model.addAttribute("noLeagueFound", "Any league has been found with the code provided :(");
		noLeagueFound=false;
		return "/leagues/createLeague";
		 }
	
	@PostMapping(value="/leagues/join")
	public String unirseLigaCode(League league,ModelMap model) {	
		User user = userService.getUserSession();
		Collection<Integer> collect = leagueService.findTeamsByUsername(user.getUsername());
		
		List<Integer> idLeague = new ArrayList<Integer>();
		
		collect.forEach(idLeague::add);
		
		
		Optional<League> liga = leagueService.findLeagueByLeagueCode(league.getLeagueCode().trim());

		
		if(!liga.isPresent()) {
			noLeagueFound=true;
			return "redirect:/leagues/join";
		}
		
		if(idLeague.contains(liga.get().getId())) {
			
			yaTienesEquipo=true;
			leagueYaEquipoId=liga.get().getId();
			return "redirect:/leagues/myLeagues";

		}
		
		if(idLeague.size()==5) {
			yaTieneMaxLigas=true;
			return "redirect:/leagues/myLeagues";
		}

		model.addAttribute("yaTienesEquipo", false);
		return "redirect:/leagues/"+liga.get().getId()+"/teams/new";
		 }
	
	@GetMapping(path="/leagues/{leagueId}/details")
	public String detallesLiga(@PathVariable("leagueId") int leagueId,ModelMap model) {	
		User user = userService.getUserSession();
		return "/leagues/leagueDetails";
		 }

	
}
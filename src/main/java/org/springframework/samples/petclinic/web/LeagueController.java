package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
//@RequestMapping("/leagues/{leagueId}")
public class LeagueController {


	//private static final String VIEWS_RESULT_CREATE_OR_UPDATE_FORM = "result/createOrUpdateResultForm";
	@Autowired

	LeagueService leagueService;
	//private final TeamService pilotService;
	@Autowired

	UserService userService;
	
//	public LeagueController(LeagueService leagueService, UserService userService) {
//		this.leagueService = leagueService;
//		this.userService = userService;
//	}

//
//	@ModelAttribute("league")
//	public Optional<League> findLeague(@PathVariable("leagueId") int leagueId) {
//		return this.leagueService.findLeague(leagueId);
//	}


//	@InitBinder("league")
//	public void initPilotBinder(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
	
	public User getUserSession() {
		User usuario = new User();  
		try {
			  Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  Integer index1 = auth.toString().indexOf("Username:");
			  Integer index2 = auth.toString().indexOf("; Password:"); // CON ESTO TENEMOS EL STRIN Username: user
			  String nombreUsuario = auth.toString().substring(index1, index2).split(": ")[1]; //con esto hemos spliteado lo de arriba y nos hemos quedado con user.

			  Optional<User> user = this.userService.findUser(nombreUsuario);
			  
			  usuario =  user.get();
		  }catch (Exception e) {	
			// TODO: handle exception
		  }
		return usuario;
	}

	
	@GetMapping("/leagues")
	public String leagues(ModelMap modelMap) {
		Iterable<League> leagues = leagueService.findAll() ;
		List<League> result = new ArrayList<League>();
	    leagues.forEach(result::add);
		
	    for(League league:result) {
	    	if(league.getRacesCompleted()<10) {
	    		league.setMoto3Active(true);
	    		league.setMoto2Active(false);//menos de 10 carreras es moto3
	    		league.setMotogpActive(false);

	    	}else if(league.getRacesCompleted()>=10 && league.getRacesCompleted()<15 ) {
	    		league.setMoto2Active(true);
	    		league.setMotogpActive(false); // si las carreras estan entre 10 y 15 pues estamos en moto2
	    		league.setMoto3Active(false);

	    	}else if(league.getRacesCompleted()>=15 ) {
	    		league.setMoto2Active(false);
	    		league.setMotogpActive(true); // mas de 15 carreras es motogp
	    		league.setMoto3Active(false);
	    	}
	    	
	    	if(league.getRacesCompleted()>20) league.setRacesCompleted(20);
	    }
		
		modelMap.addAttribute("ligas", leagueService.findAll());
	
		return "leagues/leagueList";
	}
	
	@GetMapping("/createLeague")
	public String crearLiga(ModelMap modelMap) {
		modelMap.addAttribute("ligas", leagueService.findAll());
		return "leagues/leagueList";
	}
	
	@GetMapping("/leagues/myLeagues")
	public String myLeagues(ModelMap modelMap) {
		Iterable<League> leagues = leagueService.findAll() ;
		List<League> result = new ArrayList<League>();
	    leagues.forEach(result::add);
	    List<League> myLeaguesList = new ArrayList<League>();
	    String username = getUserSession().getUsername();

	    for(int i=0;i<result.size();i++) {
		    List<Team> teams = new ArrayList<>(result.get(i).getTeam());
		    for(int j=0;j<teams.size();j++) {
		    	if(teams.get(j).getUser().getUsername().equals(username)){
		    		myLeaguesList.add(result.get(i));
		    	}
		    }
	    }
	    
	    modelMap.addAttribute("misLigas", myLeaguesList);
	return "leagues/myLeagues";
	}
	
	
	
	@GetMapping(path="/leagues/{leagueId}/increase")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, ModelMap model) {	
		
		League league = leagueService.findLeague(leagueId).get();
		league.setRacesCompleted(league.getRacesCompleted()+1);
		
		model.addAttribute("ligas", leagueService.findAll());
		return "redirect:/leagues";
	}
}
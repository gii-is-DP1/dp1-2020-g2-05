package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeamController {
	
//	@Autowired
//	public TeamController(LeagueService leagueService) {
//		this.leagueService = leagueService;
//	}
	
//	
	@Autowired
	UserService userService;
	
	@Autowired
	LeagueService leagueService;
	
	
	
//	@Autowired
//	public TeamController(UserService userService) {
//		this.userService = userService;
//	}
//

//
	
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
	
	
	@GetMapping(path="/leagues/{leagueId}/teams/new")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, ModelMap model) {
		model.addAttribute("teams", new Team());
	  return "/leagues/TeamsEdit";
	}
	
	
	
	@PostMapping(value = "/leagues/{leagueId}/teams/new")
	public String saveNewTeam(@PathVariable("leagueId") int leagueId,Team team, BindingResult result) {
		if (result.hasErrors()) {
			return "/leagues/TeamsEdit";
		}
		else {
			System.out.println(team);
			System.out.println(leagueId);
			this.leagueService.saveTeam(team);
			
			return "redirect:/leagues/{leagueId}/teams";
		}
	}
	
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/delete")
	public String borrarEscuderia (@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,  ModelMap model) {
		System.out.println(leagueId);
		Optional<Team> team = leagueService.findTeamById(teamId);
		if(team.isPresent()) {
			leagueService.delete(team.get());
			model.addAttribute("message", "Team successfully deleted!");
		}else {
			model.addAttribute("message", "Team not found!");
		}
		return "redirect:/leagues/{leagueId}/teams";
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarPiloto(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) {
		Optional<Team> team = leagueService.findTeamById(teamId);
		String view = "leagues/TeamList";
		model.addAttribute(team.get());
		if(team.isPresent()) {
			System.out.println("hola");
			view = "leagues/TeamsEdit";
			
		}else {
			model.addAttribute("message", "team not found!");
			view=showTeams(leagueId, model);
		}
		return view;
	}
	
	@GetMapping("/myTeams")
	public String myTeams(ModelMap modelMap) {
		Iterable<League> leagues = leagueService.findAll() ;
		List<League> result = new ArrayList<League>();
	    leagues.forEach(result::add);
	    List<Team> myTeamsList = new ArrayList<Team>();
	    String username = getUserSession().getUsername();
	    List<League> leaguesIDs = new ArrayList<League>();
	    for(int i=0;i<result.size();i++) {
		    List<Team> teams = new ArrayList<>(result.get(i).getTeam());
		    for(int j=0;j<teams.size();j++) {
		    	if(teams.get(j).getUser().getUsername().equals(username)){
		    		myTeamsList.add(teams.get(j));
		    		Integer a = teams.get(j).getLeague().getId();
		    		leaguesIDs.add(this.leagueService.findLeague(a).get());
		    	}
		    }
	    }
	    
	    modelMap.addAttribute("league", leaguesIDs);
	    System.out.println(leaguesIDs);
	    modelMap.addAttribute("teams", myTeamsList);
		return "leagues/myTeams";
	}

	
	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		model.put("teams", this.leagueService.findLeague(leagueId).get().getTeam());
		model.put("league", this.leagueService.findLeague(leagueId).get());
		return "/leagues/TeamList";
	}
}

package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
	@GetMapping(path="/leagues/{leagueId}/teams/new")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, ModelMap model) {
		model.addAttribute("teams", new Team());
	  return "/leagues/TeamsEdit";
	}
	
	
	
	@PostMapping(value = "/leagues/{leagueId}/teams/new")
	public String saveNewTeam(Team team, BindingResult result) {
		if (result.hasErrors()) {
			return "/leagues/TeamsEdit";
		}
		else {
			System.out.println(team);
			this.leagueService.saveTeam(team);
			
			return "redirect:/leagues/{leagueId}/teams";
		}
	}
	

	
	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		model.put("teams", this.leagueService.findLeague(leagueId).get().getTeam());
		return "/leagues/TeamList";
	}
}

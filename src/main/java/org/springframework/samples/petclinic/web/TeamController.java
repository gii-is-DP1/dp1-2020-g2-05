package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
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
	

	
	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		model.put("teams", this.leagueService.findLeague(leagueId).get().getTeam());
		model.put("league", this.leagueService.findLeague(leagueId).get());
		return "/leagues/TeamList";
	}
}

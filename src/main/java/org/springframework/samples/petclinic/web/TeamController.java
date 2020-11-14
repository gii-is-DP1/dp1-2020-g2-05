package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
//	
//	@ModelAttribute("team")
//	public Team loadLeaguewithTeams(@PathVariable("leagueId") int leagueId) {
//		League league = this.leagueService.findLeagueById(leagueId);
//		Team team = new Team();
//		league.addTeam(team);
//		return team;
//	}
//	
//	@ModelAttribute("team")
//	public Team loadUserwithTeams(@PathVariable("userId") int userId) {
//		User user = this.leagueService.findLeagueById(leagueId);
//		Team team = new Team();
//		league.addTeam(team);
//		return team;
//	}
//	
	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		model.put("teams", this.leagueService.findLeague(leagueId).get().getTeam());
		return "/leagues/TeamList";
	}
}

package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/leagues/{leagueId}")
public class LineupController {


	//private static final String VIEWS_RESULT_CREATE_OR_UPDATE_FORM = "result/createOrUpdateResultForm";
	@Autowired
	LineupService lineupService;
	//private final TeamService pilotService;
	
//	@Autowired
//	UserService userService;
	
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

	@GetMapping("/lineups")
	public String listadoAlineaciones(ModelMap modelMap) {
		modelMap.addAttribute("lineups", lineupService.findAll());
		return "lineups/lineupsList";
	}
}

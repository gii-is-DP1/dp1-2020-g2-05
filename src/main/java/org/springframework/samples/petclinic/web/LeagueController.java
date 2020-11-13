package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.ResultService;
import org.springframework.samples.petclinic.service.UserService;
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

	@GetMapping("/leagues")
	public String listadoPilotos(ModelMap modelMap) {
		modelMap.addAttribute("ligas", leagueService.findAll());
		return "leagues/leagueList";
	}
	

}
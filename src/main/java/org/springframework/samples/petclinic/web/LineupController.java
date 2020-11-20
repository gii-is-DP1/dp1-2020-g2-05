package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/leagues/{leagueId}")
public class LineupController {


	//private static final String VIEWS_RESULT_CREATE_OR_UPDATE_FORM = "result/createOrUpdateResultForm";
	
	//private final TeamService lineupService;
	
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
//	public void initLineupBinder(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
	
	@Autowired
	LineupService lineupService;

	@GetMapping("/lineups")
	public String listadoAlineaciones(ModelMap modelMap) {
		modelMap.addAttribute("resultados", lineupService.findAll());
		return "lineups/lineupsList";
	}
	
	@GetMapping(path="lineups/{lineupId}")
	public String muestraAlineacionPorId(@PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		if(lineup.isPresent()) {
			model.addAttribute("lineup", lineup.get());
		}else {
			model.addAttribute("encontrado", false);
		}
		return "lineups/lineupDetails";
	}
	
	@GetMapping(path="/lineups/new")
	public String crearAlineacion(ModelMap model) {
		model.addAttribute("lineup", new Lineup());
		return "lineups/lineupsEdit";
	}
	
	@PostMapping(path="lineups/save")
	public String guardarLineup(@Valid Lineup lineup, BindingResult result, ModelMap model) {
		String view = "lineups/lineupsList";
		if(result.hasErrors()) {
			model.addAttribute("lineup", lineup);
			return "lineups/lineupsEdit";
		}else {
			lineupService.saveLineup(lineup);
			model.addAttribute("message", "Lineup successfully saved!");
			model.addAttribute("resultados", lineupService.findAll());
		}
		return view;
	}
	
	@GetMapping(path="lineups/delete/{lineupId}")
	public String borrarLineup(@PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		if(lineup.isPresent()) {
			lineupService.delete(lineup.get());
			model.addAttribute("message", "Lineup successfully deleted!");
		}else {
			model.addAttribute("message", "Lineup not found!");
		}
		return "redirect:/lineups";
	}
	
	@GetMapping(path="lineups/edit/{lineupId}")
	public String editarLineup(@PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = this.lineupService.findLineup(lineupId);
		String view = "lineups/lineupsList";
		model.addAttribute(lineup.get());
		if(lineup.isPresent()) {
			view = "lineups/lineupsEdit";
			
		}else {
			model.addAttribute("message", "Lineup not found!");
			view=listadoAlineaciones(model);
		}
		return view;
	}
	
}

package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

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

	@Autowired
	LeagueService leagueService;

	@GetMapping("/lineups")
	public String listadoAlineaciones(ModelMap modelMap) {
		modelMap.addAttribute("resultados", lineupService.findAll());
		return "lineups/lineupsList";
	}

	@GetMapping(path="/lineups/{lineupId}")
	public String muestraAlineacionPorId(@PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		if(lineup.isPresent()) {
			model.addAttribute("lineup", lineup.get());
		}else {
			model.addAttribute("encontrado", false);
		}
		return "lineups/lineupDetails";
	}

	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/newLineup")
	public String crearAlineacionGet(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) {
		model.put("lineup", new Lineup());
		model.addAttribute("leagueCategory", this.leagueService.findLeague(leagueId).get().getCurrentCategory());
		return "lineups/lineupsEdit";
	}

	@PostMapping(value = "/leagues/{leagueId}/teams/{teamId}/newLineup")
	public String crearAlineacionPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, Lineup lineup, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("lineup", lineup);
			return "lineups/lineupsEdit";
		}
		else {
			this.lineupService.saveLineup(lineup);
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
	}

	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}")
	public String editarLineupGet(@RequestHeader(name = "Referer") String referer, @PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = this.lineupService.findLineup(lineupId);
		String view = "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		model.addAttribute(lineup.get());
		model.addAttribute("leagueCategory", this.leagueService.findLeague(leagueId).get().getCurrentCategory());
		if(lineup.isPresent()) {
			view = "lineups/lineupsEdit";

		}else {
			model.addAttribute("message", "Lineup not found!");
		}
		return view;
	}

	@PostMapping(value = "/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}")
	public String editarLineupPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, Lineup lineup, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("lineup", lineup);
			return "lineups/lineupsEdit";
		}
		else {
			Lineup lineupToUpdate = this.lineupService.findLineup(lineup.getId()).get();
			System.out.println("el ID es: " + lineup.getId() + lineupToUpdate.getId());
			BeanUtils.copyProperties(lineup, lineupToUpdate);
			this.lineupService.saveLineup(lineupToUpdate);
			model.addAttribute("message", "Lineup successfully saved!");
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
	}

	@GetMapping(path="/lineups/delete/{lineupId}")
	public String borrarLineup(@RequestHeader(name = "Referer") String referer, @PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		if(lineup.isPresent()) {
			lineupService.delete(lineup.get());
			model.addAttribute("message", "Lineup successfully deleted!");
		}else {
			model.addAttribute("message", "Lineup not found!");
		}

		return "redirect:" + referer;
	}

}

package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/leagues/{leagueId}/teams/{teamId}")
public class LineupController {


	@InitBinder("lineup")
	public void initLineupBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LineupValidator());
	}
	
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//	dataBinder.setDisallowedFields("id");
//	}
	
	TablaConsultasService TCService;
	LineupService lineupService;
	LeagueService leagueService;
	RecruitService recruitService;
	GranPremioService granPremioService;
	
	@Autowired
	public LineupController(LeagueService leagueService, LineupService lineupService,TablaConsultasService TCService,
			RecruitService recruitService, 	GranPremioService granPremioService) {
		this.lineupService = lineupService;
		this.leagueService = leagueService;
		this.TCService = TCService;
		this.recruitService = recruitService;
		this.granPremioService = granPremioService;
	}
	
	@ModelAttribute("recruitsSelection")
	public List<Pilot> getAllRecruits(@PathVariable("teamId") int teamId) {
		return this.recruitService.getRecruits(teamId);
	}

	@GetMapping("/lineups")
	public String listadoAlineaciones(ModelMap modelMap) {
		modelMap.addAttribute("resultados", lineupService.findAll());
		return "lineups/lineupsList";
	}

	@GetMapping(path="/lineups/{lineupId}")
	public String muestraAlineacionPorId(@PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		if (lineup.isPresent()) {
			model.addAttribute("lineup", lineup.get());
		} else {
			model.addAttribute("encontrado", false);
		}
		return "lineups/lineupDetails";
	}

	@GetMapping(path="/newLineup")
	public String crearAlineacionGet(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) {
		
		Lineup lineup = new Lineup();
		Category currentCategory = this.TCService.getTabla().get().getCurrentCategory();
		lineup.setCategory(currentCategory);
		Integer currentGPId = this.TCService.getTabla().get().getActualRace();
		GranPremio currentGP = this.granPremioService.findGPById(currentGPId).get();
		lineup.setGp(currentGP);
		model.put("lineup", lineup);
		model.addAttribute("leagueCategory", this.TCService.getTabla().get().getCurrentCategory());
		return "lineups/lineupsEdit";
	}

	@PostMapping(value = "/newLineup")
	public String crearAlineacionPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, @Valid Lineup lineup, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			log.debug("The lineup creation form has errors!");
			Integer currentGPId = this.TCService.getTabla().get().getActualRace();
			GranPremio currentGP = this.granPremioService.findGPById(currentGPId).get();
			lineup.setGp(currentGP);
			model.put("lineup", lineup);
			return "lineups/lineupsEdit";
		}
		else {
			this.lineupService.saveLineup(lineup);
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
	}

	@GetMapping(path="/editLineup/{lineupId}")
	public String editarLineupGet(@RequestHeader(name = "Referer") String referer, @PathVariable("leagueId") int leagueId, 
			@PathVariable("teamId") int teamId, @PathVariable("lineupId") int lineupId, ModelMap model) {
		
		String view = "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		Optional<Lineup> lineup = this.lineupService.findLineup(lineupId);

		if (lineup.isPresent()) {
			view = "lineups/lineupsEdit";
			log.info("Editing lineup with ID: " + lineupId + "\nLineup info: " + lineup.get());
			model.addAttribute("lineup", lineup.get());
		} else {
			model.addAttribute("message", "Lineup not found!");
			log.info("The lineup was not found!");
		}

		return view;
	}

	@PostMapping(value = "/editLineup/{lineupId}")
	public String editarLineupPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, @Valid Lineup lineup, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			log.info("The lineup edit form has errors!");
			GranPremio currentGP = this.lineupService.findLineup(lineup.getId()).get().getGp();
			lineup.setGp(currentGP);
			model.put("lineup", lineup);
			return "lineups/lineupsEdit";
		} else {
			Lineup lineupToUpdate = this.lineupService.findLineup(lineup.getId()).get();
			log.info("Updating lineup with ID: " + lineup.getId());
			BeanUtils.copyProperties(lineup, lineupToUpdate);
			this.lineupService.saveLineup(lineupToUpdate);
			log.info("Guardando lineup editado: " + lineupToUpdate);
			model.addAttribute("message", "Lineup successfully saved!");
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
	}

	@GetMapping(path="/delete/{lineupId}")
	public String borrarLineup(@RequestHeader(name = "Referer") String referer, @PathVariable("lineupId") int lineupId, ModelMap model) {
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		if (lineup.isPresent()) {
			lineupService.delete(lineup.get());
			model.addAttribute("message", "Lineup successfully deleted!");
		} else {
			model.addAttribute("message", "Lineup not found!");
		}

		return "redirect:" + referer;
	}

}

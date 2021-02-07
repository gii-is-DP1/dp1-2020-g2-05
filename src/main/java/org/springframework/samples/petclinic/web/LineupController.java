package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedRiderOnLineupException;
import org.springframework.samples.petclinic.service.exceptions.NoTeamInThisLeagueException;
import org.springframework.samples.petclinic.service.exceptions.NotYourTeamException;
import org.springframework.samples.petclinic.web.validator.LineupValidator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/leagues/{leagueId}/teams/{teamId}")
public class LineupController {

	@InitBinder("lineup")
	public void initLineupBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LineupValidator());
	}

	TablaConsultasService TCService;
	LineupService lineupService;
	RecruitService recruitService;
	GranPremioService granPremioService;
	TeamService teamService;
	UserService userService;

	@Autowired
	public LineupController(LineupService lineupService, TablaConsultasService TCService, RecruitService recruitService,
			GranPremioService granPremioService, TeamService teamService, UserService userService) {
		this.lineupService = lineupService;
		this.TCService = TCService;
		this.recruitService = recruitService;
		this.granPremioService = granPremioService;
		this.teamService = teamService;
		this.userService = userService;
	}

	@ModelAttribute("recruitsSelection")
	public List<Recruit> getAllRecruits(@PathVariable("teamId") int teamId) {
		return this.recruitService.getRecruitsNotOnSaleByTeam(teamId);
	}
	
//	@ModelAttribute("userTeam")
//	public Team getUserTeam(@PathVariable("leagueId") int leagueId) throws NoTeamInThisLeagueException {
//		Optional<Team> userTeam = teamService.findTeamByUsernameAndLeagueId(userService.getUserSession().getUsername(), leagueId);
//		if (userTeam.isPresent()){
//			return userTeam.get();
//		} else {
//			throw new NoTeamInThisLeagueException();
//		}
//	}
	
	@GetMapping("/lineups")
	public String listadoAlineaciones(ModelMap modelMap) {
		modelMap.addAttribute("resultados", lineupService.findAll());
		log.info("Showing all lineups in database");
		return "lineups/lineupsList";
	}

	@GetMapping(path = "/lineups/{lineupId}")
	public String muestraAlineacionPorId(@PathVariable("lineupId") int lineupId, ModelMap model) {
		String view = "lineups/lineupDetails";
		Optional<Lineup> lineupOptional = lineupService.findLineup(lineupId);
		if (lineupOptional.isPresent()) {
			Lineup lineup = lineupOptional.get();
			log.info("Lineup con id (" + lineupId + ") encontrado: " + lineup);
			model.addAttribute("lineup", lineup);
		} else {
			view = "redirect:/leagues/{leagueId}/teams/{teamId}/details";
			log.warn("Lineup con id (" + lineupId + ") NO encontrado.");
		}
		return view;
	}
	
	@GetMapping(path = "/newLineup")
	public String crearAlineacionGet(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model,
			RedirectAttributes redirectAttributes) throws NotYourTeamException {
		
		User user = this.userService.getUserSession();
		Optional<Team> optTeam = teamService.findTeamById(teamId);
		if (!(optTeam.isPresent() && user.equals(optTeam.get().getUser()))) {
			throw new NotYourTeamException("This is not your team!");
		}
		
		List<Integer> gpsConLineupsDeEsteEquipo = this.lineupService.findByTeam(teamId).stream().map(x -> x.getGp().getId()).collect(Collectors.toList());
		Integer currentGPId = this.TCService.getTabla().get().getActualRace();
		
		if (gpsConLineupsDeEsteEquipo.contains(currentGPId)) {
			log.warn("Solo puedes tener un lineup para cada GP!");
			redirectAttributes.addFlashAttribute("message", "Solo puedes tener un lineup para cada GP!");
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
		
		log.info("Creating a lineup for the team with id: " + teamId);
		Lineup lineup = new Lineup();
		Category currentCategory = this.TCService.getTabla().get().getCurrentCategory();
		GranPremio currentGP = this.granPremioService.findGPById(currentGPId).get();
		lineup.setCategory(currentCategory);
		lineup.setGp(currentGP);
		model.put("lineup", lineup);
		model.addAttribute("leagueCategory", currentCategory);
		log.info("Leading to creation form...");

	    return "thymeleaf/lineupsEdit";
	}
	
	@PostMapping(value = "/newLineup")
	public String crearAlineacionPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@Valid Lineup lineup, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) throws NotYourTeamException {
		
		User user = this.userService.getUserSession();
		Optional<Team> optTeam = teamService.findTeamById(teamId);
		if (!(optTeam.isPresent() && user.equals(optTeam.get().getUser()))) {
			throw new NotYourTeamException("This is not your team!");
		}
		
		List<Integer> gpsConLineupsDeEsteEquipo = this.lineupService.findByTeam(teamId).stream().map(x -> x.getGp().getId()).collect(Collectors.toList());
		Integer currentGPId = this.TCService.getTabla().get().getActualRace();
		
		if (gpsConLineupsDeEsteEquipo.contains(currentGPId)) {
			log.warn("Solo puedes tener un lineup para cada GP!");
			redirectAttributes.addFlashAttribute("message", "Solo puedes tener un lineup para cada GP!");
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
		
		if (result.hasErrors()) {
			log.warn("The lineup creation form has errors!");
			log.warn("Errors: " + result);
			GranPremio currentGP = this.granPremioService.findGPById(currentGPId).get();
			lineup.setGp(currentGP);
			model.put("lineup", lineup);
			return "thymeleaf/lineupsEdit";
		} else {
			try {
				this.lineupService.saveLineup(lineup);
			}  catch (DuplicatedRiderOnLineupException e) {
				log.warn("Fallo al intentar crear un nuevo lineup: Se ha seleccionado dos veces a :" + lineup.getRecruit1());
				result.rejectValue("recruit1", "duplicatedRider", "selected the same rider twice");
				result.rejectValue("recruit2", "duplicatedRider", "selected the same rider twice");
				redirectAttributes.addFlashAttribute("message", result.getAllErrors());
				GranPremio currentGP = this.granPremioService.findGPById(currentGPId).get();
				lineup.setGp(currentGP);
				model.put("lineup", lineup);
				return "thymeleaf/lineupsEdit";
			}
			log.info("Lineup succesfully created!: " + lineup);
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
	}
	
	@GetMapping(path = "/editLineup/{lineupId}")
	public String editarLineupGet(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("lineupId") int lineupId, ModelMap model, RedirectAttributes redirectAttributes) throws NotYourTeamException {

		User user = this.userService.getUserSession();
		Optional<Team> optTeam = teamService.findTeamById(teamId);
		if (!(optTeam.isPresent() && user.equals(optTeam.get().getUser()))) {
			throw new NotYourTeamException("This is not your team!");
		}
		
		String view = "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		Optional<Lineup> lineup = this.lineupService.findLineup(lineupId);

		if (lineup.isPresent()) {
			// Si ya se ha disputado el GP, no permite editar
			Optional<Boolean> condition = Optional.of(lineup.get().getGp().getHasBeenRun());
			if (condition.isPresent() && condition.get()) {
				redirectAttributes.addFlashAttribute("message", "No se puede modificar una alineacion para un GP que ya se ha disputado!");
				return view;
			} else {
				view = "thymeleaf/lineupsEdit";
				log.info("Editing lineup with ID: " + lineupId + "\nLineup info: " + lineup.get());
				model.addAttribute("lineup", lineup.get());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "Lineup not found!");
			log.info("The lineup was not found!");
		}

		return view;
	}

	@PostMapping(value = "/editLineup/{lineupId}")
	public String editarLineupPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@Valid Lineup lineup, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) throws NotYourTeamException {

		User user = this.userService.getUserSession();
		Optional<Team> optTeam = teamService.findTeamById(teamId);
		if (!(optTeam.isPresent() && user.equals(optTeam.get().getUser()))) {
			throw new NotYourTeamException("This is not your team!");
		}
		
		if (result.hasErrors()) {
			log.info("The lineup edit form has errors!");
			log.warn("Errors: " + result);
			GranPremio currentGP = this.lineupService.findLineup(lineup.getId()).get().getGp();
			lineup.setGp(currentGP);
			model.put("lineup", lineup);
			return "thymeleaf/lineupsEdit";
		} else {
			Lineup lineupToUpdate = this.lineupService.findLineup(lineup.getId()).get();
			log.info("Updating lineup with ID: " + lineup.getId());
			log.info("Lineup: " + lineup);
			BeanUtils.copyProperties(lineup, lineupToUpdate);
			GranPremio gp = this.granPremioService.findGPById(lineupToUpdate.getGp().getId()).get();
			lineupToUpdate.setGp(gp);
			log.debug("lineupToUpdate: " + lineupToUpdate);

			Optional<Boolean> condition = Optional.of(lineupToUpdate.getGp().getHasBeenRun());
			if (condition.isPresent() && condition.get()) {
				redirectAttributes.addFlashAttribute("message", "No se puede modificar una alineacion para un GP que ya se ha disputado!");
				return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
			}
			try {
				this.lineupService.saveLineup(lineupToUpdate);
			}  catch (DuplicatedRiderOnLineupException e) {
				log.warn("Fallo al intentar crear un nuevo lineup: Se ha seleccionado dos veces a :" + lineupToUpdate.getRecruit1());
				result.rejectValue("recruit1", "duplicatedRider", "selected the same rider twice");
				result.rejectValue("recruit2", "duplicatedRider", "selected the same rider twice");
				redirectAttributes.addFlashAttribute("message", result.getAllErrors());
				GranPremio currentGP = this.lineupService.findLineup(lineup.getId()).get().getGp();
				lineup.setGp(currentGP);
				model.put("lineup", lineup);
				return "thymeleaf/lineupsEdit";
			}
			log.info("Saving edited lineup: " + lineupToUpdate);
			redirectAttributes.addFlashAttribute("message", "Lineup successfully saved!");
			return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
		}
	}


	@GetMapping(path = "/delete/{lineupId}")
	public String borrarLineup(@RequestHeader(name = "Referer", defaultValue = "/leagues/{leagueId}/teams/{teamId}/details") String referer, @PathVariable("lineupId") int lineupId,
			@PathVariable("teamId") int teamId, ModelMap model, RedirectAttributes redirectAttributes) throws NotYourTeamException {
		
		User user = this.userService.getUserSession();
		Optional<Team> optTeam = teamService.findTeamById(teamId);
		if (!(optTeam.isPresent() && user.equals(optTeam.get().getUser()))) {
			throw new NotYourTeamException("This is not your team!");
		}
		
		Optional<Lineup> lineup = lineupService.findLineup(lineupId);
		log.info("Deleting lineup with id: (" + lineupId + ")");
		if (lineup.isPresent()) {
			// Si ya se ha disputado el GP, no permite borrar
			Optional<Boolean> condition = Optional.of(lineup.get().getGp().getHasBeenRun());
			if (condition.isPresent() && condition.get()) {
				redirectAttributes.addFlashAttribute("message", "No se puede modificar una alineacion para un GP que ya se ha disputado!");
				return "redirect:/leagues/{leagueId}/teams/{teamId}/details";
			} else {
				log.info("Lineup: " + lineup.get());
				lineupService.delete(lineup.get());
				redirectAttributes.addFlashAttribute("message", "Lineup successfully deleted!");
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "Lineup not found!");
			log.warn("The lineup with id (" + lineupId + ") was not found!");
		}

		return "redirect:" + referer;
	}

}

package org.springframework.samples.petclinic.web;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TeamController {

	UserService userService;

	TeamService teamService;

	LeagueService leagueService;

	RecruitService recruitService;

	OfferService offerService;

	LineupService lineupService;

	@Autowired
	public TeamController(LeagueService leagueService, UserService userService, RecruitService recruitService,
			OfferService offerService, LineupService lineupService, TeamService teamService) {
		this.leagueService = leagueService;
		this.teamService = teamService;
		this.userService = userService;
		this.recruitService = recruitService;
		this.lineupService = lineupService;
		this.offerService = offerService;

	}

	@InitBinder("team")
	public void initTeamBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new TeamValidator());
	}

	private String authority;

	private Boolean EquipoSi = false;
	private Boolean EquipoNo = false;
	private Boolean Error = false;
	private Boolean Editar = false;
	private Boolean BorrarDesdeMyTeams = false;

	@GetMapping(path = "/leagues/{leagueId}/teams/new")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, ModelMap model) {
		log.info("Abriendo el formulario para crear un equipo");
		Team team = new Team();
		League liga = this.leagueService.findLeague(leagueId).get();
		team.setLeague(liga);
		team.setUser(this.userService.getUserSession());
		Integer max = this.teamService.findTeamsByLeagueId(liga.getId());

		if (max >= 6) {
			model.addAttribute("message", "No se puede crear mas equipos en esta liga");
			return "redirect:/leagues/{leagueId}/teams";
		}
		System.out.println(team.getLeague());
		model.addAttribute("team", team);

		return "/leagues/TeamsEdit";
	}

	@PostMapping(value = "/leagues/{leagueId}/teams/new")
	public String saveNewTeam(@PathVariable("leagueId") int leagueId, @Valid Team team, BindingResult result,
			ModelMap model) throws NotAllowedNumberOfRecruitsException {
		League league = this.leagueService.findLeague(leagueId).get();

		log.debug("Asignandole la liga " + league);
		team.setLeague(league);

		log.info("La liga " + league + " ha sido asignada correctamente");
		if (result.hasErrors()) {
			model.put("team", team);
			model.put("message", result.getAllErrors());
			return "/leagues/TeamsEdit";

		} else {

			log.debug("Asignandole el usuario actual al equipo");
			team.setUser(this.userService.getUserSession());
			log.info("Usuario " + this.userService.getUserSession() + "asignado correctamente");
			Optional<Team> tem = this.teamService.findTeamByUsernameAndLeagueId(team.getUser().getUsername(), leagueId);
			List<Team> equipos = this.teamService.findTeamByLeagueId(league.getId());

			Integer igual = 0;

			for (int i = 0; i < equipos.size(); i++) {
				if (igual == 0) {
					if (team.getName().equals(equipos.get(i).getName())) {
						igual += 1;
					}
				}
			}

			if (tem.isPresent()) {
				log.warn("El equipo " + team + " no se ha podido crear");
				model.addAttribute("message", "Sorry, you cannot have more teams in this league!");
				EquipoNo = true;
				return "redirect:/leagues/{leagueId}/teams";

			} else if (igual != 0) {
				log.warn("El equipo " + team + " no se ha podido crear");
				model.addAttribute("message",
						"Sorry, already exists a team with thsi name, try it with another team Name");
				return "redirect:/leagues/{leagueId}/teams";
			} else {
				team.setMoney(2000);
				team.setPoints(0);
				log.debug("Guardando el equipo " + team + " en la base de datos.");
				this.teamService.saveTeam(team);
				log.info("Equipo " + team + " guardado correctamente.");
				EquipoSi = true;

				log.debug("Se procede asignar aleatoriamente los 2 pilotos al equipo " + team);
				teamService.randomRecruit2Pilots(team);
				log.info("2 pilotos iniciales asignados al equipo " + team);

				return "redirect:/leagues/{leagueId}/teams";

			}
		}

	}

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/details")
	public String mostrarDetallesEscuderia(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamID,
			ModelMap model) {
		Optional<Team> team = this.teamService.findTeamById(teamID);

		if (team.isPresent()) {
			if (!model.containsAttribute("message")) model.addAttribute("message", "Team found!");
			model.addAttribute("team", team.get());
			List<Recruit> fichajesEnVenta = recruitService.getRecruitsOnSaleByTeam(teamID);
			List<Recruit> fichajes = recruitService.getRecruitsNotOnSaleByTeam(teamID);
			model.addAttribute("misFichajes", fichajes);
			model.addAttribute("fichajesEnVenta", fichajesEnVenta);
			model.addAttribute("misAlineaciones", lineupService.findByTeam(teamID));
		} else {
			if (!model.containsAttribute("message")) model.addAttribute("message", "Team not found!");
		}
		return "/leagues/teamDetails";
	}

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/details/{recruitId}")
	public String setPrice(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("recruitId") int recruitId, ModelMap modelMap) {

		Optional<Recruit> opRecruit = recruitService.findRecruit(recruitId);
		if (opRecruit.isPresent()) {
			modelMap.addAttribute("offer", new Offer());
			modelMap.addAttribute("recruitToSale", opRecruit.get());
			return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
		} else {
			modelMap.addAttribute("message", "Recruit not found!");
			return "/leagues/teamDetails";
		}
	}

	@PostMapping(path = "/leagues/{leagueId}/teams/{teamId}/details/{recruitId}")
	public String putOnSale(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("recruitId") int recruitId, @Valid Offer offer, BindingResult result, ModelMap modelMap)
			throws NotAllowedNumberOfRecruitsException {
		if (result.hasErrors()) {
			System.out.println(result);
			modelMap.put("message", result.getAllErrors());
			return setPrice(leagueId, teamId, recruitId, modelMap);
		} else {
			Optional<Recruit> opRecruit = recruitService.findRecruit(recruitId);
			if (opRecruit.isPresent()) {
				log.info("Fichaje: " + opRecruit.get().getId() + " a un precio de: " + offer.getPrice());
				try {
					recruitService.putOnSale(opRecruit.get());
					offerService.putOnSale(opRecruit.get(), offer.getPrice());
				} catch (Exception e) {
					modelMap.addAttribute("message",
							"You must own at least 2 riders not on sale to perform this action");
					return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
				}

				return "redirect:/leagues/{leagueId}/market";
			} else {
				modelMap.addAttribute("message", "Recruit not found!");
				return "redirect:/leagues/" + leagueId + "/teams/" + teamId + "/details";
			}
		}
	}

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/delete")
	public String borrarEscuderia(
			@RequestHeader(name = "Referer", defaultValue = "/leagues/{leagueId}/teams") String referer,
			@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) {

		Optional<Team> team = this.teamService.findTeamById(teamId);
		log.info("Preparandose para borrar el equipo " + team);

		if (team.isPresent()) {
			log.debug("Borrando el equipo " + team);
			teamService.delete(team.get());
			log.info("Equipo " + team + "borrado correctamente");
			model.addAttribute("message", "Team successfully deleted!");
			List<Team> t = this.teamService.findTeamByLeagueId(leagueId);
			if (t.size() == 1 && t.get(0).getName().equals("Sistema")) {
				return "redirect:/leagues";
			} else {
				return "redirect:" + referer;

			}
		} else {
			log.info("El equipo " + team + "no se ha podido borrar correctamente");
			model.addAttribute("message", "Team not found!");
			return "redirect:/leagues";
		}

	}

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarPiloto(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			ModelMap model) {
		Optional<Team> team = this.teamService.findTeamById(teamId);
		log.info("Preparandose para editar el equipo " + team);
		model.put("team", team.get());

		authority = this.leagueService.findAuthoritiesByUsername(team.get().getUser().getUsername());

		if (authority.equals("admin")) {
			model.put("admin", true);
		} else {
			model.put("usuario", true);
		}

		Editar = true;

		model.put("Editar", true);

		return "leagues/TeamsEdit";

	}

	@PostMapping(value = "/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarPilotoPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@Valid Team team, BindingResult result, ModelMap model) {

		System.out.println(team);
		System.out.println(team.getUser().getUsername());
		authority = this.leagueService.findAuthoritiesByUsername(team.getUser().getUsername());
		if (authority.equals("admin")) {
			model.put("admin", true);
		} else {
			model.put("usuario", true);
		}

		League league = this.leagueService.findLeague(leagueId).get();

		log.debug("Asignandole la liga " + league);

		team.setLeague(league);

		if (result.hasErrors()) {
			model.put("Editar", true);
			log.warn("El equipo " + team + " no ha podido ser editado correctamente");
			model.put("team", team);
			return "/leagues/TeamsEdit";

		} else {
			log.info("Equipo " + team + " editado correctamente");
			User usuario = this.userService.getUserSession();
			Team teamToUpdate = this.teamService.findTeamById(teamId).get();
			BeanUtils.copyProperties(team, teamToUpdate);
			model.put("team", team);

			log.debug("Guardando el equipo " + team + " editado correctamente");

			this.teamService.saveTeam(teamToUpdate);

			log.info("Equipo " + team + " guardado editado correctamente");

			model.addAttribute("message", "Team successfully Updated!");

			if (BorrarDesdeMyTeams != true) {
				return "redirect:/leagues/{leagueId}/teams";
			} else {
				model.put("BorrarDesdeMyTeams", true);
				return myTeams(model);

			}
		}
	}

	@GetMapping("/myTeams")
	public String myTeams(ModelMap modelMap) {
		User user = this.userService.getUserSession();
		log.debug("Obteniendo los equipos de un usuario");

		List<Team> team = this.teamService.findTeamByUsername(user.getUsername());

		log.info("Equipos encontrados correctamente");
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("teams", team);
		BorrarDesdeMyTeams = true;
		return "Perfil/Perfil";
	}

	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		User usuario = this.userService.getUserSession();

		Boolean existeLiga = !this.leagueService.findLeague(leagueId).isPresent();
		if (existeLiga) {
			return "redirect:/leagues";
		}

		log.debug("Obteniendo equipos de la liga" + this.leagueService.findLeague(leagueId).get());

		List<Team> tem = this.teamService.findTeamByLeagueId(leagueId);

		log.info("Equipos obtenindos correctamente");
		log.debug("Ordenando los equipos");

		tem = tem.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		log.info("Equipos ordenados correctamente");

		authority = this.leagueService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());

		if (authority.equals("admin")) {
			model.put("admin", true);
		} else {
			model.put("usuario", true);
		}
		model.put("teams", tem);
		model.put("league", this.leagueService.findLeague(leagueId).get());
		model.put("user", usuario);
		BorrarDesdeMyTeams = false;
		if (EquipoNo)
			model.put("EquipoNo", "You cannot have more than 1 teams in the same league");
		EquipoNo = false;
		if (EquipoSi)
			model.put("EquipoSi", "Team created succesfully!");
		EquipoSi = false;
		if (Error)
			model.put("Error", "Your team have some errors!");
		Error = false;
		if (Error)
			model.put("Error", "Your team have some errors!");
		Error = false;

		return "/leagues/TeamList";
	}
}

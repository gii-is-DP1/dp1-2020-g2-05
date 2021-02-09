package org.springframework.samples.dreamgp.web;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.Offer;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.service.LeagueService;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.samples.dreamgp.service.OfferService;
import org.springframework.samples.dreamgp.service.RecruitService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.service.exceptions.DuplicatedTeamNameException;
import org.springframework.samples.dreamgp.service.exceptions.JoinWithoutCodeException;
import org.springframework.samples.dreamgp.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.samples.dreamgp.service.exceptions.NotTeamUserException;
import org.springframework.samples.dreamgp.web.validator.TeamValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

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
	
	@GetMapping(path = "/leagues/{leagueId}/teams/new")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, @RequestHeader(name = "Referer", defaultValue = "http://localhost:8090/") String referer,
			ModelMap model) throws JoinWithoutCodeException {
		
		authority = this.userService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());

		if (authority.equals("admin")) {
			model.put("admin", true);
		} else {
			model.put("usuario", true);
		}
		
		String r = referer.split(":[0-9]{4}")[1];
		if(!r.equalsIgnoreCase("/leagues/join") && !authority.equals("admin")) {
			throw new JoinWithoutCodeException();
		}
		
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
		
		model.addAttribute("team", team);

		return "/leagues/TeamsEdit";
	}
	

	@PostMapping(value = "/leagues/{leagueId}/teams/new")
	public String saveNewTeam(@PathVariable("leagueId") int leagueId, @Valid Team team, BindingResult result,
			ModelMap model) throws NotAllowedNumberOfRecruitsException, DuplicatedTeamNameException {
		
		authority = this.userService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());
		League league = this.leagueService.findLeague(leagueId).get();
		log.debug("Asignandole la liga " + league);
		team.setLeague(league);
		

		log.info("La liga " + league + " ha sido asignada correctamente");
		
		if (result.hasErrors()) {
			model.put("team", team);
			model.put("message", result.getAllErrors());
			return "/leagues/TeamsEdit";

		}else {

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

			if (tem.isPresent() && !authority.equals("admin")) {
				log.warn("El equipo " + team + " no se ha podido crear");
				model.addAttribute("message", "Sorry, you cannot have more teams in this league!");
				return showTeams(leagueId, model);

			}else if (igual != 0) {
				log.warn("El equipo " + team + " no se ha podido crear");
				throw new DuplicatedTeamNameException();
				
				
			}else {
				if(!authority.equals("admin")) {
				team.setMoney(2000);
				team.setPoints(0);
				}
				
				log.debug("Guardando el equipo " + team + " en la base de datos.");
				this.teamService.saveTeam(team);
				log.info("Equipo " + team + " guardado correctamente.");
				
				model.addAttribute("message", "Team created Succesfully!");
				
				log.debug("Se procede asignar aleatoriamente los 2 pilotos al equipo " + team);
				teamService.randomRecruit2Pilots(team);
				log.info("2 pilotos iniciales asignados al equipo " + team);

				return showTeams(leagueId, model);
			}
		}
	}
	

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/details")
	public String mostrarDetallesEscuderia(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamID,
			ModelMap model) throws NotTeamUserException {
		
		authority = this.userService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());

		if(this.userService.getUserSession() != this.teamService.findTeamById(teamID).get().getUser() && !authority.equals("admin")){
			throw new NotTeamUserException();
		}else {
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
	}
	

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/details/{recruitId}")
	public String setPrice(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("recruitId") int recruitId, ModelMap modelMap) throws NotTeamUserException {

		Optional<Recruit> opRecruit = recruitService.findRecruitById(recruitId);
		if (opRecruit.isPresent()) {
			modelMap.addAttribute("offer", new Offer());
			modelMap.addAttribute("recruitToSale", opRecruit.get());
			return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
		} else {
			modelMap.addAttribute("message", "Recruit not found!");
			return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
		}
	}

	@PostMapping(path = "/leagues/{leagueId}/teams/{teamId}/details/{recruitId}")
	public String putOnSale(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("recruitId") int recruitId, @Valid Offer offer, BindingResult result, ModelMap modelMap)
			throws NotAllowedNumberOfRecruitsException, NotTeamUserException {
		if (result.hasErrors()) {
			
			modelMap.put("message", "Set a valid price");
			return setPrice(leagueId, teamId, recruitId, modelMap);
		} else {
			Optional<Recruit> opRecruit = recruitService.findRecruitById(recruitId);
			if (opRecruit.isPresent()) {
				log.info("Fichaje: " + opRecruit.get().getId() + " a un precio de: " + offer.getPrice());
				try {
					recruitService.putOnSale(opRecruit.get());
					offerService.putOnSale(opRecruit.get(), offer.getPrice());
				} catch (NotAllowedNumberOfRecruitsException e) {
					modelMap.addAttribute("message",
							"You must own at least 2 riders not on sale to perform this action");
					return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
				}

				return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
			} else {
				modelMap.addAttribute("message", "Recruit not found!");
				return mostrarDetallesEscuderia(leagueId, teamId, modelMap);
			}
		}
	}

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/delete")
	public String borrarEscuderia(
			@RequestHeader(name = "Referer", defaultValue = "/leagues/{leagueId}/teams") String referer,
			@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) throws NotTeamUserException {
		
		authority = this.userService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());

		if(this.userService.getUserSession() != this.teamService.findTeamById(teamId).get().getUser() && !authority.equals("admin")){
			throw new NotTeamUserException();
		}else {
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
			} 
		}
		return "redirect:" + referer;

		}
	}
	
	

	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/edit")
	
	public String editarTeam(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			ModelMap model) throws NotTeamUserException {
		
		authority = this.userService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());

			if(this.userService.getUserSession() != this.teamService.findTeamById(teamId).get().getUser() && !authority.equals("admin")){
				throw new NotTeamUserException();
			}else {

		Optional<Team> team = this.teamService.findTeamById(teamId);
		log.info("Preparandose para editar el equipo " + team);
		model.put("team", team.get());

		authority = this.userService.findAuthoritiesByUsername(team.get().getUser().getUsername());

		if (authority.equals("admin")) {
			model.put("admin", true);
		} else {
			model.put("usuario", true);
		}
		model.put("Editar", true);

		return "leagues/TeamsEdit";

			}
	}

	@PostMapping(value = "/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarTeamPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@Valid Team team, BindingResult result, ModelMap model) {
		

		if (result.hasErrors()) {
			model.put("Editar", true);
			log.warn("El equipo " + team + " no ha podido ser editado correctamente");
			model.put("team", team);
			return "/leagues/TeamsEdit";

		} else {
			log.info("Equipo " + team + " editado correctamente");
			Team teamToUpdate = this.teamService.findTeamById(teamId).get();
			BeanUtils.copyProperties(team, teamToUpdate);
			model.put("team", team);

			log.debug("Guardando el equipo " + team + " editado correctamente");

			this.teamService.saveTeam(teamToUpdate);

			log.info("Equipo " + team + " guardado editado correctamente");

			model.addAttribute("message", "Team successfully Updated!");

				return myTeams(model);
			

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

		authority = this.userService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());

		if (authority.equals("admin")) {
			model.put("admin", true);
		} else {
			model.put("usuario", true);
		}
		
		model.put("teams", tem);
		model.put("league", this.leagueService.findLeague(leagueId).get());
		model.put("user", usuario);
		return "/leagues/TeamList";
	}
}

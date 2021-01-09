package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LeagueController {

	private String AUTHORITY;

	TeamController teamController;
	TablaConsultasService TCService;
	TeamService teamService;
	LeagueService leagueService;
	UserService userService;

	@Autowired
	public LeagueController(LeagueService leagueService, UserService userService, TablaConsultasService TCService,
			TeamService teamService, TeamController teamController) {
		this.leagueService = leagueService;
		this.userService = userService;
		this.TCService = TCService;
		this.teamService = teamService;
		this.teamController = teamController;
	}

	@InitBinder("league")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LeagueValidator());
	}
	

	@GetMapping("/leagues")
	public String leagues(ModelMap modelMap) throws JSONException, IOException {
		User user = this.userService.getUserSession();
		AUTHORITY = this.leagueService.findAuthoritiesByUsername(user.getUsername());

		Optional<TablaConsultas> tabla = this.TCService.getTabla();

		List<League> result = leagueService.convertirIterableLista(leagueService.findAll());

		log.debug("Intentando detectar si hay ligas sin equipos");
		if (this.leagueService.comprobarLigaVacia(result)) { // si ha eliminado a una liga da true y refresco la pagina
			log.info("Liga borrada");

			return leagues(modelMap);
		} else {
			log.info("No se han detectado ligas sin equipos");

		}

		if (AUTHORITY.equals("admin")) {
			modelMap.addAttribute("admin", true);
		} else if (!AUTHORITY.equals("admin")) {
			modelMap.addAttribute("user", true);
		}

		modelMap.addAttribute("ligas", result);
		modelMap.addAttribute("categoriaActual", tabla.get().getCurrentCategory());
		modelMap.addAttribute("carrerasCompletadas", tabla.get().getRacesCompleted());
		return "leagues/leagueList";
	}

	@GetMapping("/leagues/myLeagues")
	public String myLeagues(ModelMap modelMap) {
		User user = this.userService.getUserSession();

		List<League> myLeaguesList = new ArrayList<League>();

		if (Optional.of(user).isPresent()) {
			myLeaguesList = leagueService.obtenerListaIntegerToTeams(teamService.findTeamsByUsername(user.getUsername())); // obtengo
			// usuario
		}
		log.info("Mostrando ligas del user '"+user.getUsername()+"'");
		try {

			if (modelMap.getAttribute("yaTienesEquipo").equals(true)) {
				modelMap.addAttribute("leagueYaEquipoId", modelMap.getAttribute("leagueYaEquipoId"));

			}

		} catch (NullPointerException e) {
		}

		if (myLeaguesList.size() == 0) {
			modelMap.addAttribute("noTengoLigas", true);
			return "leagues/myLeagues";
		} else {
			modelMap.addAttribute("noTengoLigas", false);

			modelMap.addAttribute("misLigas", myLeaguesList);

			return "leagues/myLeagues";
		}

	}

	@GetMapping(path = "/leagues/increase")
	public String incrementarLiga(ModelMap model) throws DataAccessException, duplicatedLeagueNameException {
//		AUTHORITY = this.leagueService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());
//		if(!(AUTHORITY.equals("admin"))) {
//			return "redirect:/leagues";
//		}
		Category category = Category.valueOf(model.getAttribute("category").toString());

		TCService.actualizarTabla(category);


		return "redirect:/leagues";
	}

	@GetMapping(path = "/leagues/new")
	public String initcrearLiga(ModelMap model) throws DataAccessException, duplicatedLeagueNameException {
		User user = this.userService.getUserSession();

		if ( user==null || !Optional.of(user).isPresent()) {
			return "/leagues/leagueList";
		}

		Integer num_leagues = leagueService.findLeaguesByUsername(user.getUsername());

		if (num_leagues >= 5) {
			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();

		League newLeague = new League();
		newLeague.setLeagueCode(leagueService.randomString(10));
		newLeague.setLeagueDate(formatter.format(date));

		log.debug("Liga dummy : " + newLeague);

		model.addAttribute("league", newLeague);

		return "/leagues/createLeagueName";
	}

	@PostMapping(path = "/leagues/new")
	public String processCrearLiga(@Valid League league, BindingResult results, ModelMap model)
			throws DataAccessException, duplicatedLeagueNameException {
		log.debug("Obtenida la liga del form : " + league);
		if (results.hasErrors()) {
			model.put("league", league);
			model.put("message", results.getAllErrors());
			return "/leagues/createLeagueName";
		} else {

			try {

				User user = this.userService.getUserSession();

				Set<Team> setTeams = new HashSet();

				Team team = new Team();
				team.setUser(this.userService.getUserSession());
				team.setLeague(league);
				team.setMoney(2000);
				team.setPoints(0);
				team.setName(user.getUsername() + " team");
				league.setTeam(setTeams);

				this.leagueService.saveLeague(league);

				this.teamService.saveTeam(team);
				log.info("Equipo " + team + " guardado correctamente.");

			} catch (duplicatedLeagueNameException e) {
				log.warn("Fallo al intentar crear la nueva liga : " + league + ", el nombre '" + league.getName()
						+ "' ya esta asignado a una liga");
				results.rejectValue("name", "duplicate", "already exists a league with that name");
				model.put("message", results.getAllErrors());
				return "/leagues/createLeagueName";
			}
			
			log.debug("Creando el equipo sistema");
			teamService.saveSystemTeam(league);
			log.info("Equipo sistema creado correctamente");


			return "redirect:/leagues/myLeagues";
		}

	}

	@GetMapping(path = "/leagues/join")
	public String unirseLiga(ModelMap model) {
		User user = this.userService.getUserSession();

		if (user==null || !Optional.of(user).isPresent() ) {
			return "/leagues/leagueList";
		}
		log.debug("Intentando unir a " + user.getUsername() + " a una liga");

		model.addAttribute("league", new League());

		Integer num_leagues = this.leagueService.findLeaguesByUsername(user.getUsername());

		if (num_leagues == 5) {
			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}
		try {
			if (model.getAttribute("noLeagueFound").equals(true)) {
				model.addAttribute("noLeagueFound", "Any league has been found with the code provided :(");
			}
		} catch (NullPointerException e) {

		}

		return "/leagues/createLeague";
	}

	@PostMapping(value = "/leagues/join")
	public String unirseLigaCode(League league, ModelMap model) {
		User user = this.userService.getUserSession();


		List<Integer> idLeague = this.teamService.findTeamsByUsername(user.getUsername());

		Optional<League> liga = this.leagueService.findLeagueByLeagueCode(league.getLeagueCode().trim());

		if (!liga.isPresent()) {
			log.warn("No se han encontrado ligas con el codigo :'" + league.getLeagueCode()+"'");
			model.addAttribute("noLeagueFound", true);
			return unirseLiga(model);
		}

		Integer numTeamsLeague = this.teamService.findTeamsByLeagueId(liga.get().getId());

		if (idLeague.contains(liga.get().getId())) {
			log.info("El user '"+user.getUsername()+ "' ya pertenece a la liga con codigo :'"+league.getLeagueCode()+"'");

			model.addAttribute("yaTienesEquipo", true);
			model.addAttribute("leagueYaEquipoId", liga.get().getId());
			return myLeagues(model);
		}

		if (numTeamsLeague > 5) {
			log.info("La liga con codigo :'"+league.getLeagueCode()+"' ya tiene el maximo de equipos");

			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}
		log.info("Redirigiendo a crear equipo para entrar en la liga con codigo :'"+league.getLeagueCode()+"'");
		model.addAttribute("yaTienesEquipo", false);
		return "redirect:/leagues/" + liga.get().getId() + "/teams/new";
//		teamController.crearEquipo(leagueId, model);
	}

}

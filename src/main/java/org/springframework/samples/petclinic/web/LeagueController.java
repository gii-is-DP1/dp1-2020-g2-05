package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	TablaConsultasService TCService;
	TeamService teamService;
	LeagueService leagueService;
	UserService userService;

	@Autowired
	public LeagueController(LeagueService leagueService, UserService userService, TablaConsultasService TCService,
			TeamService teamService) {
		this.leagueService = leagueService;
		this.userService = userService;
		this.TCService = TCService;
		this.teamService = teamService;
	}

	@InitBinder("league")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LeagueValidator());
	}

	@GetMapping("/leagues")
	public String leagues(ModelMap modelMap) throws JSONException, IOException {

		Optional<TablaConsultas> tabla = this.TCService.getTabla();

		List<League> leagueList = leagueService.findAll();

		log.debug("Intentando detectar si hay ligas sin equipos");
		if (this.leagueService.comprobarLigaVacia(leagueList)) { // si ha eliminado a una liga da true 
			log.info("Liga borrada");                        // y refresco la lista de ligas
		
			leagueList = leagueService.findAll();
		} else {
			log.info("No se han detectado ligas sin equipos");
		}
		Boolean message=this.TCService.checkDates(tabla.get());
		if(message) {
			modelMap.addAttribute("temporalMessage","Results has been validated, check your lineups and teams score!!");
		}
		modelMap.addAttribute("ligas", leagueList);
		modelMap.addAttribute("categoriaActual", tabla.get().getCurrentCategory());
		modelMap.addAttribute("carrerasCompletadas", tabla.get().getRacesCompleted());
		return "leagues/leagueList";
	}

	@GetMapping("/leagues/myLeagues")
	public String myLeagues(ModelMap modelMap) {
		User user = this.userService.getUserSession();

		List<League> myLeaguesList = new ArrayList<League>();

		if (Optional.of(user).isPresent()) {
			myLeaguesList = leagueService.obtenerListaIntegerToTeams(teamService.findTeamsByUsername(user.getUsername())); 
			//se obtienen las ligas en las que un usuario participa 
			//consultando la id de las ligas en las que tiene sus equipos
		}
		log.info("Mostrando ligas del user '" + user.getUsername() + "'");
		try {

			if (modelMap.getAttribute("yaTienesEquipo").equals(true)) {
				modelMap.addAttribute("leagueYaEquipoId", modelMap.getAttribute("leagueYaEquipoId"));
					//si yatienesequipo es true significa que el usuario ya 
					//tiene equipo en esta liga
					//leagueyaequipo es para saber que id es la de la liga
					//en la que el usuario ya tiene equipo
			}

			} catch (NullPointerException e) {
				//si yatienesequipo es null entra aqui
				log.warn("Null pointer exception capturado porque 'yaTienesEquipo' es null " );
			}

		if (myLeaguesList.size() == 0) {
			//si el tamaño de la lista es 0 tenemos que 
			//decirle que no tiene ligas en la vista
			modelMap.addAttribute("noTengoLigas", true);
			return "leagues/myLeagues";
		} else {
			modelMap.addAttribute("noTengoLigas", false);

			modelMap.addAttribute("misLigas", myLeaguesList);

			return "leagues/myLeagues";
		}

	}

	@GetMapping(path = "/leagues/increase/{category}")
	public String incrementarLiga(@PathVariable("category") String category,ModelMap model) throws DataAccessException, duplicatedLeagueNameException {
		
		//aqui se entra cuando se corre un GP para aumentar el numero de carreras
		Category categoryP = Category.valueOf(category);

		TCService.actualizarTabla(categoryP);

		return "redirect:/leagues";
	}

	@GetMapping(path = "/leagues/new")
	public String initcrearLiga(ModelMap model) throws DataAccessException, duplicatedLeagueNameException {
		User user = this.userService.getUserSession();

		if (user == null || !Optional.of(user).isPresent()) {
			return "/leagues/leagueList";
		}

		Integer num_leagues = leagueService.findLeaguesByUsername(user.getUsername());

		if (num_leagues >= 5) {
			//Si pertence a mas de 5 ligas, no puede
			//crear mas ligas
			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();

		League newLeague = new League();
		newLeague.setLeagueCode("DntHckMe:(");
		newLeague.setLeagueDate(formatter.format(date));
		//creamos la liga con codigo y fecha actual 
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
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				User user = this.userService.getUserSession();


				Team team = new Team();
				team.setUser(this.userService.getUserSession());
				team.setLeague(league);
				team.setMoney(2000);
				team.setPoints(0);
				team.setName(user.getUsername() + " team");
				league.addTeam(team);
				league.setLeagueCode(leagueService.randomString(10));
				league.setLeagueDate(formatter.format(date));

				this.leagueService.saveLeague(league);

				this.teamService.saveTeam(team);
				
				//para evitar que se creara la liga y despues se quedara sin equipos
				//asignados le asignamos un equipo con el nombre del usuario + teams
				
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

		if (user == null || !Optional.of(user).isPresent()) {
			return "/leagues/leagueList";
		}
		
		log.debug("Intentando unir a " + user.getUsername() + " a una liga");

		

		Integer num_leagues = this.leagueService.findLeaguesByUsername(user.getUsername());

		if (num_leagues >= 5) {
			//Si pertence a mas de 5 ligas, no puede
			//unirse a mas ligas
			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}
		try {
			if (model.getAttribute("noLeagueFound").equals(true)) {
				model.addAttribute("noLeagueFound", "Any league has been found with the code provided :(");
			}
		} catch (NullPointerException e) {

		}
		log.debug("redirigiendo para introducir un codigo de liga");
		model.addAttribute("league", new League());
		return "/leagues/createLeague";
	}

	@PostMapping(value = "/leagues/join")
	public String unirseLigaCode(League league, ModelMap model) {
		User user = this.userService.getUserSession();

		List<Integer> idLeague = this.teamService.findTeamsByUsername(user.getUsername());

		Optional<League> liga = this.leagueService.findLeagueByLeagueCode(league.getLeagueCode().trim());

		if (!liga.isPresent()) {
			log.warn("No se han encontrado ligas con el codigo :'" + league.getLeagueCode() + "'");
			model.addAttribute("noLeagueFound", true);
			return unirseLiga(model);
		}

		Integer numTeamsLeague = this.teamService.findTeamsByLeagueId(liga.get().getId());

		if (idLeague.contains(liga.get().getId())) {
			log.info("El user '" + user.getUsername() + "' ya pertenece a la liga con codigo :'"
					+ league.getLeagueCode() + "'");

			model.addAttribute("yaTienesEquipo", true);
			model.addAttribute("leagueYaEquipoId", liga.get().getId());
			return myLeagues(model);
		}

		if (numTeamsLeague > 5) {
			log.info("La liga con codigo :'" + league.getLeagueCode() + "' ya tiene el maximo de equipos");

			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}
		log.info("Redirigiendo a crear equipo para entrar en la liga con codigo :'" + league.getLeagueCode() + "'");
		model.addAttribute("yaTienesEquipo", false);
	
//		model.addAttribute("vengoDelJoinLeague",true);
//		this.teamController.crearEquipo(liga.get().getId(),model);
//		return "redirect:/leagues/myLeagues";
		return "redirect:/leagues/" + liga.get().getId() + "/teams/new";
	}

}

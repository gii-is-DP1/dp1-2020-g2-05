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
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
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
	public void initLeagueBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LeagueValidator());
	}

	@GetMapping("/leagues")
	public String leagues(ModelMap modelMap) throws JSONException, IOException {

		Optional<TablaConsultas> tablaConPropiedadesSistema = this.TCService.getTabla();

		List<League> todasLasLigas = leagueService.findAll();

		log.debug("Intentando detectar si hay ligas sin equipos");
		if (this.leagueService.comprobarSiHayLigasVacias(todasLasLigas)) { // si ha eliminado a una liga da true
			log.info("Liga borrada"); // y refresco la lista de ligas

			todasLasLigas = leagueService.findAll();
		} else {
			log.info("No se han detectado ligas sin equipos");
		}
		Boolean message = this.TCService.checkDates(tablaConPropiedadesSistema.get());
		if (message) {
			modelMap.addAttribute("temporalMessage",
					"Results has been validated, check your lineups and teams score!!");
		}
		modelMap.addAttribute("ligas", todasLasLigas);
		Category categoriaActual=tablaConPropiedadesSistema.get().getCurrentCategory();
		this.leagueService.calculaCarrerasParaSiguienteCategoria(modelMap, categoriaActual)	;	
		modelMap.addAttribute("carrerasCompletadas", tablaConPropiedadesSistema.get().getRacesCompleted());
		
		return "leagues/leagueList";
	}

	@GetMapping("/leagues/myLeagues")
	public String myLeagues(ModelMap modelMap) {
		User usuarioSesion = this.userService.getUserSession();

		List<League> ligasDelUsuarioDeLaSesion = new ArrayList<League>();

		if (Optional.of(usuarioSesion).isPresent()) {
			ligasDelUsuarioDeLaSesion = leagueService.obtenerListaDeLigasDeUnaListaDeIntegers(
					teamService.findTeamsIntByUsername(usuarioSesion.getUsername()));
			// se obtienen las ligas en las que un usuario participa
			// consultando la id de las ligas en las que tiene sus equipos
		}
		log.info("Mostrando ligas del user '" + usuarioSesion.getUsername() + "'");
		try {

			if (modelMap.getAttribute("yaTienesEquipo").equals(true)) {
				modelMap.addAttribute("leagueYaEquipoId", modelMap.getAttribute("leagueYaEquipoId"));
				// si yatienesequipo es true significa que el usuario ya
				// tiene equipo en esta liga
				// leagueyaequipo es para saber que id es la de la liga
				// en la que el usuario ya tiene equipo
			}

		} catch (NullPointerException e) {
			// si yatienesequipo es null entra aqui
			log.warn("Null pointer exception capturado porque 'yaTienesEquipo' es null ");
		}

		if (ligasDelUsuarioDeLaSesion.size() == 0) {
			// si el tamaño de la lista es 0 tenemos que
			// decirle que no tiene ligas en la vista
			modelMap.addAttribute("noTengoLigas", true);
			return "leagues/myLeagues";
		} else {
			modelMap.addAttribute("noTengoLigas", false);

			modelMap.addAttribute("nombreUsuarioDeLaSesion", usuarioSesion.getUsername());

			modelMap.addAttribute("misLigas", ligasDelUsuarioDeLaSesion);

			return "leagues/myLeagues";
		}

	}

	@GetMapping(path = "/leagues/increase/{category}")
	public String incrementarCarrerasLigas(@PathVariable("category") String categoryParametro, ModelMap model)
			throws DataAccessException, duplicatedLeagueNameException {

		// aqui se entra cuando se corre un GP para aumentar el numero de carreras
		Category category = Category.valueOf(categoryParametro);

		TCService.actualizarTabla(category);

		return "redirect:/leagues";
	}

	@GetMapping(path = "/leagues/new")
	public String initcrearLiga(ModelMap model) throws DataAccessException, duplicatedLeagueNameException {
		User usuarioSesion = this.userService.getUserSession();

		if (usuarioSesion == null || !Optional.of(usuarioSesion).isPresent()) {
			return "/leagues/leagueList";
		}

		Integer numeroDeLigasDelUsuarioDeLaSesion = leagueService.findLeaguesByUsername(usuarioSesion.getUsername());

		if (numeroDeLigasDelUsuarioDeLaSesion >= 5) {
			// Si pertence a mas de 5 ligas, no puede
			// crear mas ligas
			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();

		League nuevaLiga = new League();
		nuevaLiga.setLeagueCode("DntHckMe:(");
		nuevaLiga.setLeagueDate(formatter.format(date));
		// creamos la liga con codigo y fecha actual
		log.debug("Liga dummy : " + nuevaLiga);

		model.addAttribute("league", nuevaLiga);

		return "/leagues/createLeagueName";
	}

	@PostMapping(path = "/leagues/new")
	public String processCrearLiga(@Valid League nuevaLiga, BindingResult results, ModelMap model)
			throws DataAccessException, duplicatedLeagueNameException, NotAllowedNumberOfRecruitsException {
		log.debug("Obtenida la liga del form : " + nuevaLiga);
		if (results.hasErrors()) {
			model.put("league", nuevaLiga);
			model.put("message", results.getAllErrors());
			return "/leagues/createLeagueName";
		} else {

			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				User usuarioSesion = this.userService.getUserSession();

				Team nuevoEquipoEnLaNuevaLiga = new Team();
				nuevoEquipoEnLaNuevaLiga.setUser(this.userService.getUserSession());
				nuevoEquipoEnLaNuevaLiga.setLeague(nuevaLiga);
				nuevoEquipoEnLaNuevaLiga.setMoney(2000);
				nuevoEquipoEnLaNuevaLiga.setPoints(0);
				nuevoEquipoEnLaNuevaLiga.setName(usuarioSesion.getUsername() + " team");
				nuevaLiga.addTeam(nuevoEquipoEnLaNuevaLiga);
				nuevaLiga.setLeagueCode(leagueService.randomString(10));
				nuevaLiga.setLeagueDate(formatter.format(date));

				this.leagueService.saveLeague(nuevaLiga);

				this.teamService.saveTeam(nuevoEquipoEnLaNuevaLiga);

				// para evitar que se creara la liga y despues se quedara sin equipos
				// asignados le asignamos un equipo con el nombre del usuario + teams

				log.info("Equipo " + nuevoEquipoEnLaNuevaLiga + " guardado correctamente.");

				log.debug("Creando el equipo sistema");
				teamService.saveSystemTeam(nuevaLiga);
				log.info("Equipo sistema creado correctamente");

				log.debug("Se procede asignar aleatoriamente los 2 pilotos al equipo " + nuevoEquipoEnLaNuevaLiga);
				teamService.randomRecruit2Pilots(nuevoEquipoEnLaNuevaLiga);
				log.info("2 pilotos iniciales asignados al equipo " + nuevoEquipoEnLaNuevaLiga);

			} catch (duplicatedLeagueNameException e) {
				log.warn("Fallo al intentar crear la nueva liga : " + nuevaLiga + ", el nombre '" + nuevaLiga.getName()
						+ "' ya esta asignado a una liga");
				results.rejectValue("name", "duplicate", "already exists a league with that name");
				model.put("message", results.getAllErrors());
				return "/leagues/createLeagueName";
			}

			return "redirect:/leagues/myLeagues";
		}

	}

	@GetMapping(path = "/leagues/join")
	public String unirseLiga(ModelMap model) {
		User usuarioSesion = this.userService.getUserSession();

		if (usuarioSesion == null || !Optional.of(usuarioSesion).isPresent()) {
			return "/leagues/leagueList";
		}

		log.debug("Intentando unir a " + usuarioSesion.getUsername() + " a una liga");

		Integer numeroDeLigasDelUsuarioDeLaSesion = this.leagueService
				.findLeaguesByUsername(usuarioSesion.getUsername());

		if (numeroDeLigasDelUsuarioDeLaSesion >= 5) {
			// Si pertence a mas de 5 ligas, no puede
			// unirse a mas ligas
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
		User usuarioSesion = this.userService.getUserSession();

		List<Integer> idsDeLasLigasDelUsuarioDeLaSesion = this.teamService
				.findTeamsIntByUsername(usuarioSesion.getUsername());

		Optional<League> ligaObtenidaDelCodigoIntroducido = this.leagueService
				.findLeagueByLeagueCode(league.getLeagueCode().trim());

		if (!ligaObtenidaDelCodigoIntroducido.isPresent()) {
			log.warn("No se han encontrado ligas con el codigo :'" + league.getLeagueCode() + "'");
			model.addAttribute("noLeagueFound", true);
			return unirseLiga(model);
		}

		Integer numeroEquiposDeLaLigaCodigoIntroducido = this.teamService
				.findTeamsByLeagueId(ligaObtenidaDelCodigoIntroducido.get().getId());

		if (idsDeLasLigasDelUsuarioDeLaSesion.contains(ligaObtenidaDelCodigoIntroducido.get().getId())) {
			log.info("El user '" + usuarioSesion.getUsername() + "' ya pertenece a la liga con codigo :'"
					+ league.getLeagueCode() + "'");

			model.addAttribute("yaTienesEquipo", true);
			model.addAttribute("leagueYaEquipoId", ligaObtenidaDelCodigoIntroducido.get().getId());
			return myLeagues(model);
		}

		if (numeroEquiposDeLaLigaCodigoIntroducido > 5) {
			log.info("La liga con codigo :'" + league.getLeagueCode() + "' ya tiene el maximo de equipos");

			model.addAttribute("yaTieneMaxTeams", true);
			return myLeagues(model);
		}
		log.info("Redirigiendo a crear equipo para entrar en la liga con codigo :'" + league.getLeagueCode() + "'");
		model.addAttribute("yaTienesEquipo", false);

//		model.addAttribute("vengoDelJoinLeague",true);
//		this.teamController.crearEquipo(liga.get().getId(),model);
//		return "redirect:/leagues/myLeagues";
		return "redirect:/leagues/" + ligaObtenidaDelCodigoIntroducido.get().getId() + "/teams/new";
	}

}

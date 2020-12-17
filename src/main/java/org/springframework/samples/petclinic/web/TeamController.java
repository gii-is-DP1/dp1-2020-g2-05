package org.springframework.samples.petclinic.web;

import java.util.ArrayList;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;

import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TeamController {
	

	@Autowired
	UserService userService;
	
	@Autowired
	LeagueService leagueService;
	
	@Autowired
	RecruitService recruitService;
	
	@Autowired
	OfferService offerService;

	@Autowired
	LineupService lineupService;
	
	@InitBinder("team")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new TeamValidator());
	}


	private Boolean EquipoSi=false;
	private Boolean EquipoNo=false;
	private Boolean Error=false;
	private Boolean Editar=false;
	private Boolean BorrarDesdeMyTeams=false;
	
	public User getUserSession() {
		User usuario = new User();  
		try {
			  Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  Integer index1 = auth.toString().indexOf("Username:");
			  Integer index2 = auth.toString().indexOf("; Password:"); // CON ESTO TENEMOS EL STRIN Username: user
			  String nombreUsuario = auth.toString().substring(index1, index2).split(": ")[1]; //con esto hemos spliteado lo de arriba y nos hemos quedado con user.

			  Optional<User> user = this.userService.findUser(nombreUsuario);
			  
			  usuario =  user.get();
		  }catch (Exception e) {	
			// TODO: handle exception
		  }
		return usuario;
	}
	
	
	@GetMapping(path="/leagues/{leagueId}/teams/new")
	public String crearEquipo(@PathVariable("leagueId") int leagueId, ModelMap model) {
		model.addAttribute("team", new Team());
		
	  return "/leagues/TeamsEdit";
	}
	
	
	
	@PostMapping(value = "/leagues/{leagueId}/teams/new")
	public String saveNewTeam(@PathVariable("leagueId") int leagueId, @Valid Team team, BindingResult result, ModelMap model) {
		System.out.println("holaaa");
		

		if(result.hasErrors()) {
			System.out.println(result);
			model.put("team", team);
			model.put("message",result.getAllErrors());
			return "/leagues/TeamsEdit";
		}else {
		
		
		User usuario = getUserSession();
			List<Team> tem = this.leagueService.findTeamByUsernameAndLeagueId(usuario.getUsername(), leagueId);

		
		
		 if(tem.size()>= 1){
//			model.addAttribute("message", "Sorry, you cannot have more teams in this league!");
			EquipoNo=true;
			return "redirect:/leagues/{leagueId}/teams";
			
		}
			else {
			team.setUser(usuario);
			this.leagueService.saveTeam(team);
			EquipoSi=true;
			
			return "redirect:/leagues/{leagueId}/teams";
		}
		}
		
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/details")
	public String mostrarDetallesEscuderia (@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamID,  ModelMap model) {
		Optional<Team> team = leagueService.findTeamById(teamID);
		if(team.isPresent()) {
			model.addAttribute("message", "Team found!");
			model.addAttribute("team", team.get());
			List<Recruit> l = recruitService.getRecruitsByTeam(teamID);
			System.out.println(l);
			model.addAttribute("misFichajes", l);
			model.addAttribute("misAlineaciones", new ArrayList<Lineup>());//lineupService.findByTeam(teamID));
		}else {
			model.addAttribute("message", "Team not found!");
		}
		return "/leagues/teamDetails";
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/details/{recruitId}")
	public String setPrice(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,
			@PathVariable("recruitId") int recruitId, ModelMap modelMap) {
		
		Optional<Recruit> opRecruit = recruitService.findRecruit(recruitId);
		if(opRecruit.isPresent()) {
			modelMap.addAttribute("offer", new Offer());
			modelMap.addAttribute("recruitToSale",opRecruit.get());
			return mostrarDetallesEscuderia(leagueId,teamId,modelMap);
		}else {
			modelMap.addAttribute("message", "Recruit not found!");
			return "/leagues/teamDetails";
		}
	}
	
	@PostMapping(path="/leagues/{leagueId}/teams/{teamId}/details/{recruitId}")
	public String putOnSale(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, @PathVariable("recruitId") int recruitId,
			@Valid Offer offer,BindingResult result, ModelMap modelMap) {
		if(result.hasErrors()) {
			System.out.println(result);
			modelMap.put("message",result.getAllErrors());
			return setPrice(leagueId,teamId,recruitId,modelMap);
		}else {
			Optional<Recruit> opRecruit = recruitService.findRecruit(recruitId);
			if(opRecruit.isPresent()) {
				offerService.putOnSale(opRecruit.get(), offer.getPrice());
				return "redirect:/leagues/{leagueId}/market";
			}else {
				modelMap.addAttribute("message", "Recruit not found!");
				return "/leagues/teamDetails";
			}
		}
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/delete")
	public String borrarEscuderia (@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,  ModelMap model) {
		Optional<Team> team = leagueService.findTeamById(teamId);
		if(team.isPresent()) {
			leagueService.delete(team.get());
			model.addAttribute("message", "Team successfully deleted!");
		}else {
			model.addAttribute("message", "Team not found!");
		}
		if(BorrarDesdeMyTeams) {
			BorrarDesdeMyTeams = false;
			return "redirect:/myTeams"; 
		}else {
		return "redirect:/leagues/{leagueId}/teams";
		}
		
//		return "redirect:/leagues/{leagueId}/teams";

	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarPiloto(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) {
		Optional<Team> team = leagueService.findTeamById(teamId);
				model.put("team", team.get());
				
				Editar = true;
				
				model.put("Editar", Editar);
				return "leagues/TeamsEdit";
		
	}
	
	@PostMapping(value = "/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarPilotoPost(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, Team team, ModelMap model, BindingResult result) {
		if (result.hasErrors()) {
			model.put("team", team);
			
			return "leagues/TeamsEdit";
		}
		else {
			User usuario = getUserSession();

			Editar = false;
			Team teamToUpdate = this.leagueService.findTeamById(team.getId()).get();
			BeanUtils.copyProperties(team, teamToUpdate);
			teamToUpdate.setUser(usuario);
			this.leagueService.saveTeam(teamToUpdate);
			model.addAttribute("message", "Team successfully saved!");
			return "redirect:/leagues/{leagueId}/teams";
		}
	}
	
	@GetMapping("/myTeams")
	public String myTeams(ModelMap modelMap) {
		String username = getUserSession().getUsername();
		List<Team> team = this.leagueService.findTeamByUsername(username);
	    modelMap.addAttribute("teams", team);
	    BorrarDesdeMyTeams = true;
		return "leagues/myTeams";
	}

	
	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		User usuario = getUserSession();
		List<Team> tem = this.leagueService.findTeamByLeagueId(leagueId);
		tem = tem.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		System.out.println(tem);
		model.put("teams", tem);
		model.put("league", this.leagueService.findLeague(leagueId).get());
		model.put("user", usuario);
		BorrarDesdeMyTeams = false;
		if(EquipoNo) model.put("EquipoNo", "You cannot have more than 1 teams in the same league");
		EquipoNo=false;
		if(EquipoSi) model.put("EquipoSi", "Team created succesfully!");
		EquipoSi=false;
		if(Error) model.put("Error", "Your team have some errors!");
		Error=false;
		if(Error) model.put("Error", "Your team have some errors!");
		Error=false;
		
		return "/leagues/TeamList";
	}
}

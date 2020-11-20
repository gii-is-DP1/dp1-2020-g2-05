package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {
	
//	@Autowired
//	public TeamController(LeagueService leagueService) {
//		this.leagueService = leagueService;
//	}
	
//	
	@Autowired
	UserService userService;
	
	@Autowired
	LeagueService leagueService;
	
	@Autowired
	RecruitService recruitService;
	
	
	
//	@Autowired
//	public TeamController(UserService userService) {
//		this.userService = userService;
//	}
//

//
	
	private static boolean messageequipoduplicado;
	
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
		model.addAttribute("teams", new Team());
		
	  return "/leagues/TeamsEdit";
	}
	
	
	
	@PostMapping(value = "/leagues/{leagueId}/teams/new")
	public String saveNewTeam(@PathVariable("leagueId") int leagueId,Team team, BindingResult result, ModelMap model) {
		User usuario = getUserSession();
			List<Team> tem = new ArrayList<Team>();
			Optional<League> league = this.leagueService.findLeague(leagueId);
			List<Team> list = league.get().getTeam().stream().collect(Collectors.toList());
			System.out.println(list);
			String username = getUserSession().getUsername();
			for(int i = 0; i<list.size(); i++) {
				if(list.get(i).getUser().getUsername().equals(username)){
		    		tem.add(list.get(i));
		    		
	    	}
		}
		System.out.println(tem.size());

		
		System.out.println(tem);
		if (result.hasErrors()) {
			model.addAttribute("message", "Team have some errors!");
			return "/leagues/TeamsEdit";
	}
		else if(tem.size()>= 1){
//			model.addAttribute("message", "Sorry, you cannot have more teams in this league!");
			messageequipoduplicado = true;
			
			return "redirect:/leagues/{leagueId}/teams";
			
		}
			else {
			team.setUser(usuario);
			this.leagueService.saveTeam(team);
			model.addAttribute("message", "Team successfully saved!");
			
			return "redirect:/leagues/{leagueId}/teams";
		}
		
		
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/details")
	public String mostrarDetallesEscuderia (@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamID,  ModelMap model) {
		Optional<Team> team = leagueService.findTeamById(teamID);
		if(team.isPresent()) {
			model.addAttribute("message", "Team found!");
			model.addAttribute("team", team.get());
			List<Pilot> l = recruitService.getRecruits(teamID);
			System.out.println(l);
			model.addAttribute("misFichajes", l);
		}else {
			model.addAttribute("message", "Team not found!");
		}
		return "/leagues/teamDetails";
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/delete")
	public String borrarEscuderia (@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId,  ModelMap model) {
		System.out.println(leagueId);
		Optional<Team> team = leagueService.findTeamById(teamId);
		if(team.isPresent()) {
			leagueService.delete(team.get());
			model.addAttribute("message", "Team successfully deleted!");
		}else {
			model.addAttribute("message", "Team not found!");
		}
		return "redirect:/leagues/{leagueId}/teams";
	}
	
	@GetMapping(path="/leagues/{leagueId}/teams/{teamId}/edit")
	public String editarPiloto(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamId, ModelMap model) {
		Optional<Team> team = leagueService.findTeamById(teamId);
		String view = "leagues/TeamList";
		model.addAttribute(team.get());
		if(team.isPresent()) {
			System.out.println("hola");
			view = "leagues/TeamsEdit";
			
		}else {
			model.addAttribute("message", "team not found!");
			view=showTeams(leagueId, model);
		}
		return view;
	}
	
	@GetMapping("/myTeams")
	public String myTeams(ModelMap modelMap) {
		Iterable<League> leagues = leagueService.findAll() ;
		List<League> result = new ArrayList<League>();
	    leagues.forEach(result::add);
	    List<Team> myTeamsList = new ArrayList<Team>();
	    String username = getUserSession().getUsername();
	    ;
	    for(int i=0;i<result.size();i++) {
		    List<Team> teams = new ArrayList<>(result.get(i).getTeam());
		    for(int j=0;j<teams.size();j++) {
		    	if(teams.get(j).getUser().getUsername().equals(username)){
		    		myTeamsList.add(teams.get(j));
		    		
		    	}
		    }
	    }
	    
	    
	    modelMap.addAttribute("teams", myTeamsList);
		return "leagues/myTeams";
	}

	
	@GetMapping(value = "/leagues/{leagueId}/teams")
	public String showTeams(@PathVariable int leagueId, Map<String, Object> model) {
		User usuario = getUserSession();
		model.put("teams", this.leagueService.findLeague(leagueId).get().getTeam());
		model.put("league", this.leagueService.findLeague(leagueId).get());
		model.put("user", usuario);
		if(messageequipoduplicado) {
			model.put("message", true);
			messageequipoduplicado = false;
		}
		
		return "/leagues/TeamList";
	}
}

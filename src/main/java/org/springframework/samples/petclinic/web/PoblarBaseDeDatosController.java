package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PoblarBaseDeDatosController {
	
	@Autowired
	PilotService pilotService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LeagueService leagueService;
	
	
	private String AUTHORITY;
	
	
	
	
	@GetMapping(path="/BD/pilotsBD")
	public String PoblarBD(ModelMap model) {
		model.addAttribute("FormRellenarBD", new FormRellenarBD());	
		System.out.println("hola");
	  return "/BD/BD";
	}
	
	@PostMapping(value = "/BD/pilotsBD")	
	public String Poblar(FormRellenarBD form, ModelMap model, BindingResult result) throws JSONException, IOException {
		System.out.println("holahola");
		System.out.println(form);
		if(result.hasErrors()) {
			System.out.println(result);
			model.put("FormRellenarBD", form);
			model.put("message",result.getAllErrors());
			return "/pilots/BD";
		}else {
			
//			this.pilotService.poblarBD(form);
			
			return "/pilots/pilotsList";
		}
		
		
	}
//	@GetMapping(path="/pilots/BD")
//	public String BaseDeDatos(Integer anyoInicial, Integer anyoFinal, Category categoria, ModelMap modelMap) throws JSONException, IOException {
//	AUTHORITY = this.leagueService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername());
//	
//	
//	}
//	

}
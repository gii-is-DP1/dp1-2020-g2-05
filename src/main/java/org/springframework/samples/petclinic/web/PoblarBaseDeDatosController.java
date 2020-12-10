package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
	private Boolean messageNullPointerException = false;
	private BDCarrera formError = new BDCarrera();
	@InitBinder("form")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BDValidator());
	}

	
	@GetMapping(path="/BD")
	public String seleccionarTipoPoblacion(ModelMap model) {
	  return "/BD/seleccionarTipoPoblacion";
	}
	
	
	@GetMapping(path="/BD/pilotsBD")
	public String PoblarBD(ModelMap model) {
		model.addAttribute("FormRellenarBD", new FormRellenarBD());	
		motogpAPI.Category[] yourEnums = motogpAPI.Category.values();
		List<motogpAPI.Category> num = new ArrayList<>();
		
		for(int i = 0; i<yourEnums.length; i++) {
			num.add(yourEnums[i]);
		}
		model.addAttribute("listaCategoria", num);
		System.out.println("hola");
	  return "/BD/BD";
	}
	
	@PostMapping(path = "/BD/pilotsBD")	
	public String Poblar( FormRellenarBD form, BindingResult result, ModelMap model) throws JSONException, IOException {
		System.out.println(result);

		if(result.hasErrors()) {
			System.out.println(result);
			model.put("FormRellenarBD", form);
			model.put("message",result.getAllErrors());
			return "/BD/BD";
		}else {
			try {
			this.pilotService.poblarBD(form);
			}catch(NullPointerException e) {
				return "redirect:/BD/pilotsBD";
			}
			return "redirect:/pilots";
		}
		
		
	}
	
	
	@GetMapping(path="/BD/carrerasBD")
	public String PoblarBDCarreras(ModelMap model) {
		model.addAttribute("BDCarrera", new BDCarrera());	
		motogpAPI.Category[] yourEnums = motogpAPI.Category.values();
		List<motogpAPI.Category> num = new ArrayList<>();
		
		for(int i = 0; i<yourEnums.length; i++) {
			num.add(yourEnums[i]);
		}
		
		motogpAPI.RaceCode[] enums = motogpAPI.RaceCode.values();
		List<motogpAPI.RaceCode> num1 = new ArrayList<>();
		
		for(int i = 0; i<enums.length; i++) {
			num1.add(enums[i]);
		}
		
		motogpAPI.Session[] enums2 = motogpAPI.Session.values();
		List<motogpAPI.Session> num2 = new ArrayList<>();
		
		for(int i = 0; i<enums2.length; i++) {
			num2.add(enums2[i]);
		}
		model.addAttribute("listaCategoria", num);
		model.addAttribute("listaRacecode", num1);
		model.addAttribute("listaSession", num2);

		if(messageNullPointerException) model.put("messageNullPointerException",true);messageNullPointerException=false;
		if(formError.getYear()!=null) model.addAttribute("BDCarrera", formError); formError=new BDCarrera();

		
	  return "/BD/BDCarrera";
	}
	
	@PostMapping(path = "/BD/carrerasBD")	
	public String PoblarBDcarrera( BDCarrera form, BindingResult result, ModelMap model) throws JSONException, IOException {
		System.out.println(result);

		if(result.hasErrors()) {
			System.out.println(result);
			model.put("BDCarrera", form);
			model.put("message",result.getAllErrors());
			return "/BD/BD";
		}else {
			try {
				this.pilotService.poblarBDCarreraACarrera(form);
			}catch (NullPointerException e) {
				messageNullPointerException=true;
				formError=form;
				return "redirect:/BD/carrerasBD";
					
			}
			 
			return "redirect:/pilots";
		}
		
		
	}
	
	
	


}
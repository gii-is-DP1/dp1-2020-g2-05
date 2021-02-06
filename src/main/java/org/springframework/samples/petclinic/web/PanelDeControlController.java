package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import motogpApiV2.testing.testing;

@Controller
@Slf4j
public class PanelDeControlController {
	
	GranPremioService GPService;
	LeagueService leagueService;
	UserService userService;
	TablaConsultasService TCService;
	
	@Autowired
	public PanelDeControlController(GranPremioService GPService, LeagueService leagueService, UserService userService, 
			TablaConsultasService TCService) {
		this.GPService = GPService;
		this.leagueService=leagueService;
		this.userService=userService;
		this.TCService=TCService;
	}


	
	@GetMapping(path="/controlPanelSP")
	public String muestraPanelSinParams(ModelMap model) throws ParseException {	
		
		
		List<GranPremio> gps = GPService.findAllActualYear(2019).stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
		Set<GranPremio> sortedGps = gps.stream().collect(Collectors.toSet());
		model.addAttribute("listaGP",sortedGps.stream().sorted(Comparator.comparing(GranPremio::getId)).collect(Collectors.toList()));

		return "/panelControl/panelDeControl";
		 
	}

	
	@GetMapping(path={"/controlPanel/{pth}/{code}"})
	public String muestraPanel(@PathVariable("pth") String pth,@PathVariable("code") String code,ModelMap model) throws ParseException {	
		model = this.leagueService.descifraUri(pth, code, model);
		List<GranPremio> gps = GPService.findAllActualYear(2019).stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
		Set<GranPremio> sortedGps = gps.stream().collect(Collectors.toSet());
		model.addAttribute("listaGP",sortedGps.stream().sorted(Comparator.comparing(GranPremio::getId)).collect(Collectors.toList()));

		return "/panelControl/panelDeControl";
		
	}
	
	@GetMapping(path="/controlPanelSP/actualizaVariables")
	public String actualizaVariables(ModelMap model) {	
		log.info("Actualizando variables del sistema");
        TCService.actualizarTablaAutomatica();
		return "redirect:/controlPanelSP";
	}

	@GetMapping(path="/controlPanelSP/validaCarreras")
	public String validaCarreras(ModelMap model) throws Exception {
		log.info("Validando la ultima carrera completada");
        TCService.comprobandoCarrerasCompletadas();
		return "redirect:/controlPanelSP";
	}
	
	@GetMapping(path="/controlPanelSP/poblarCarreras")
	public String vistaPoblarCarreras(ModelMap model) throws ParseException {	
		 
		
		List<GranPremio> gps = GPService.findAllActualYear(2019).stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
		Set<GranPremio> sortedGps = gps.stream().collect(Collectors.toSet());
		model.addAttribute("listaGP",sortedGps.stream().sorted(Comparator.comparing(GranPremio::getId)).collect(Collectors.toList()));

		return "/panelControl/poblarGP";
		 
	}
	
	
	@GetMapping(path={"/poblarConCalendario"})
	public String muestraPanel(ModelMap model) throws ParseException, JsonMappingException, JsonProcessingException, IOException, InterruptedException {	
		List<GranPremio> gp =testing.obtainScheduleForAGivenYearAndGivenCategory(2019, Category.MOTOGP);		
		for(int i=0;i<gp.size();i++) {
			this.GPService.saveGP(gp.get(i));
		}
		
		return "/panelControl/panelDeControl";
		 
	}
	
	
}

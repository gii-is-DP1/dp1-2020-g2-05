package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GranPremioController {
	
	GranPremioService GPService;
	TablaConsultasService TCService;

	
	@Autowired
	public GranPremioController(GranPremioService GPService,TablaConsultasService TCService) {
		this.GPService = GPService;
		this.TCService=TCService;
	}


	
	
	
	
//	@InitBinder("granpremio")
//	public void initGPBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new GranPremioValidator());
//	}


	@GetMapping(path="/granPremios")
	public String detallesLiga(ModelMap model) throws ParseException {	
//		List<GranPremio> gps = GPService.convertirIterableLista(GPService.findAllActualYear(2019));
//		List<List<GranPremio>> gps_calificados = GranPremioService.granPremiosPorCategoria(gps);
//		List<GranPremio> motogp = gps_calificados.get(0);
//		List<GranPremio> moto2 = gps_calificados.get(1);
//		List<GranPremio> moto3 = gps_calificados.get(2);
		List<GranPremio> gps = GPService.findAllActualYear(2020).stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
		model.addAttribute("listaGP",gps.stream().sorted(Comparator.comparing(GranPremio::getId)).collect(Collectors.toList()));
		model.addAttribute("racesCompleted",this.TCService.getTabla().get().getRacesCompleted());
		return "/gp/gpList";
		 
	}
	
	@GetMapping(path="/granPremios/new")
	public String nuevoGranPremio(ModelMap model) {	

		model.addAttribute("GranPremio",new GranPremio());

		return "/gp/nuevoGP";
		 
	}
	
	@PostMapping(path="/granPremios/new")
	public String nuevoGranPremio(@Valid GranPremio granpremio,BindingResult results,ModelMap model) {	

		if(results.hasErrors()) {
			model.addAttribute("errors",results.getAllErrors());
			
			return nuevoGranPremio(model);
		}else {
			granpremio.setCalendar(true);
			granpremio.setHasBeenRun(false);
			this.GPService.saveGP(granpremio);
			model.addAttribute("message","Gran Premio loaded succesfully!");
		}
		
		

		return "redirect:/controlPanel";
		 
	}
	
	@GetMapping(path="/granPremios/{id}/delete")
	public String eliminarGranPremio(@PathVariable("id") String id,ModelMap model) {	
		this.GPService.delete(this.GPService.findGPById(Integer.parseInt(id)).get());
		return "redirect:/controlPanel";
		 
	}
}

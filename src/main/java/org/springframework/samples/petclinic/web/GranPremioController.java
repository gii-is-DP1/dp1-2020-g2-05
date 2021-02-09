package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import motogpApiV2.GranPremioDetails.GranPremioDetails;
import motogpApiV2.GranPremioDetails.Venue;
import motogpApiV2.testing.testing;

@Slf4j
@Controller
public class GranPremioController {
	
	GranPremioService GPService;
	TablaConsultasService TCService;
	
	@Autowired
	public GranPremioController(GranPremioService GPService,TablaConsultasService TCService) {
		this.GPService = GPService;
		this.TCService = TCService;
	}

	@GetMapping(path="/granPremios")
	public String detallesLiga(ModelMap model) throws ParseException {	
		List<GranPremio> gps = GPService.findAllActualYear(2019);
		model.addAttribute("listaGP", gps);
		log.info("Showing all GPs in database: " + gps);
		return "gp/gpList";
	}
	
	@GetMapping(path="/granPremios/new")
	public String nuevoGranPremio(ModelMap model) {	
		log.info("Creating a new GP.");
		model.addAttribute("GranPremio", new GranPremio());
		log.info("Leading to creation form...");
		return "gp/nuevoGP";
	}
	
	
	@PostMapping(path="/granPremios/new")
	public String nuevoGranPremio(@Valid GranPremio granpremio, BindingResult results, ModelMap model, RedirectAttributes redirectAttributes) {	
		if (results.hasErrors()) {
			log.warn("The GP creation form has errors!");
			log.warn("Errors: " + results);
			model.addAttribute("errors", results.getAllErrors());
			return nuevoGranPremio(model);
		} else {
			granpremio.setCalendar(true);
			granpremio.setHasBeenRun(false);
			try {
				this.GPService.populateRecord(granpremio);
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", "Sorry, records are unavailable for this GP!");
				log.warn("Sorry, records are unavailable for this GP!");
			}
			this.GPService.saveGP(granpremio);
			log.info("GP succesfully created!: " + granpremio);
			model.addAttribute("message","Gran Premio loaded succesfully!");
		}
		
		return "redirect:/controlPanel";
	}
	
	@GetMapping(path = "/granPremios/{id}/delete")
	public String eliminarGranPremio(@PathVariable("id") int id, ModelMap model, RedirectAttributes redirectAttributes) {
		Optional<GranPremio> gp = this.GPService.findGPById(id);
		log.info("Deleting GP with id: (" + id + ")");
		if (gp.isPresent()) {
			log.info("GP: " + gp.get());
			GPService.delete(gp.get());
			redirectAttributes.addFlashAttribute("message", "GP successfully deleted!");
		} else {
			redirectAttributes.addFlashAttribute("message", "GP not found!");
			log.warn("The GP with id (" + id + ") was not found!");
		}

		return "redirect:/controlPanelSP";
	}
	



	@GetMapping(path="/granPremios/info/{gpId}")
	public String detallesGP(@PathVariable("gpId") int id, ModelMap model) throws ParseException, JsonMappingException, JsonProcessingException, IOException, InterruptedException {	
		GranPremio gp = GPService.findGPById(id).get();
		Venue datos = testing.obteinDetailsFromGp(gp.getIdApi());
		model.addAttribute("lista", datos);
		model.addAttribute("gp",gp); 
		return "gp/gpDetails";
	}

	
	@GetMapping(path="/granPremios/grid/{gpId}")
	public String gridGp(@PathVariable("gpId") int id, ModelMap model) throws ParseException, JsonMappingException, JsonProcessingException, IOException, InterruptedException {	
		GranPremio gp = GPService.findGPById(id).get();
		List<motogpApiV2.Qualifying.Competitor> grid = testing.obtainGridForAGivenYearAndRacecode(gp.getIdApi());
		model.addAttribute("grid", grid);
		model.addAttribute("gp",gp); 
		return "gp/gpGrid";
	}
}

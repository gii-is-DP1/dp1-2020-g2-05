package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

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
		// El sorted sería mejor meterlo en el repositorio con un ORDER BY
		List<GranPremio> gps = GPService.findAllActualYear(2020).stream().distinct()
								.sorted(Comparator.comparing(GranPremio::getId)).collect(Collectors.toList());
		model.addAttribute("listaGP", gps);
		model.addAttribute("racesCompleted", this.TCService.getTabla().get().getRacesCompleted());
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
	
	
	//Cambiar model.addAttribute por ra.addatributte.
	@PostMapping(path="/granPremios/new")
	public String nuevoGranPremio(@Valid GranPremio granpremio, BindingResult results, ModelMap model) {	
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
				log.warn("Sorry, records are unavailable for this GP!"); // Provisional
			}
			this.GPService.saveGP(granpremio);
			log.info("GP succesfully created!: " + granpremio);
			model.addAttribute("message","Gran Premio loaded succesfully!");
		}
		
		return "redirect:/controlPanel";
	}
	
	@GetMapping(path = "/granPremios/{id}/delete")
	public String eliminarGranPremio(@PathVariable("id") int id, ModelMap model) {
		Optional<GranPremio> gp = this.GPService.findGPById(id);
		log.info("Deleting GP with id: (" + id + ")");
		if (gp.isPresent()) {
			log.info("GP: " + gp.get());
			GPService.delete(gp.get());
			model.addAttribute("message", "GP successfully deleted!");
		} else {
			model.addAttribute("message", "GP not found!");
			log.warn("The GP with id (" + id + ") was not found!");
		}

		return "redirect:/controlPanel";
	}
	
	@RequestMapping(path="/granPremios/setRecords/{gpId}")
	public String populateRecords(@PathVariable("gpId") int gpId, ModelMap model) throws IOException {
		Optional<GranPremio> gp = this.GPService.findGPById(gpId);
		log.info("Populating GP with id: (" + gpId + ")");
		if (gp.isPresent()) {
			log.info("GP: " + gp.get());
			this.GPService.populateRecord(gp.get());
			this.GPService.saveGP(gp.get());
			model.addAttribute("message", "GP successfully populated!");
		} else {
			model.addAttribute("message", "GP not found!");
			log.warn("The GP with id (" + gpId + ") was not found!");
		}
		
		return "gp/gpList";
	}
	
}

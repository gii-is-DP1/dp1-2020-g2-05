package org.springframework.samples.dreamgp.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.service.PilotService;
import org.springframework.samples.dreamgp.web.validator.PilotValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PilotController {
	
	@InitBinder("pilot")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PilotValidator());
	}

	@Autowired
	PilotService pilotService;
	
	@GetMapping(path={"/pilots", "/pilotsPaged"})
	public String listadoPilotosPaginado(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") String pageNumberString, 
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") String pageSizeString, ModelMap modelMap) {

		Integer pageNumber;
		Integer pageSize;
		
		try {  
			pageNumber = Integer.parseInt(pageNumberString);  
			pageSize = Integer.parseInt(pageSizeString);
		} catch (NumberFormatException e) {
			log.warn("Se ha intentado paginar con parametros que no son Integer: " + e);//\nCausa: " + e.getCause() + "\nMensaje: " + e.getMessage());
			pageNumber = 0;  
			pageSize = 5;
		} 

		log.info("Paginando pilotos. Parametros de entrada: pageSize = " + pageSize + ", pageNumber = " + pageNumber);

		if (pageNumber == null || pageNumber < 1) pageNumber = 1;
		if (pageSize == null || pageSize < 1) pageSize = 1;

		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Order.asc("category"), Order.asc("name"), Order.desc("lastName")));
		Page<Pilot> paginaPilotos = this.pilotService.findAllPage(pageable);
		Integer maxPageNumber = (int) Math.ceil((double) paginaPilotos.getTotalElements()/paginaPilotos.getSize());

		if (pageNumber > maxPageNumber) {
			pageNumber = maxPageNumber;
			pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Order.asc("category"), Order.asc("name"), Order.desc("lastName")));
			paginaPilotos = this.pilotService.findAllPage(pageable);
			maxPageNumber = (int) Math.ceil((double) paginaPilotos.getTotalElements()/paginaPilotos.getSize());
		}

		log.info("Paginando pilotos. Parametros procesados: pageSize = " + pageSize + ", pageNumber = " + pageNumber);

		modelMap.addAttribute("pageNumber", pageNumber-1);
		modelMap.addAttribute("pageSize", pageSize);
		modelMap.addAttribute("maxPageNumber", maxPageNumber);
		modelMap.addAttribute("resultadosPaginados", paginaPilotos.getContent());

		log.info("Resultados: " + paginaPilotos.getContent());
		return "pilots/pilotsListPaged";
	}
	
	@GetMapping(path="pilots/{pilotId}")
	public String muestraPilotoPorId(@PathVariable("pilotId") int pilotId, ModelMap model) {
		Optional<Pilot> pilot = pilotService.findPilotById(pilotId);
		
		if(pilot.isPresent()) {
			model.addAttribute("pilot", pilot.get());
			model.addAttribute("results",pilotService.obtenerResultsFormatted(pilot.get().getResults()));
			log.info("Piloto con id (" + pilotId + ") encontrado: " + pilot.get());
		}else {
			model.addAttribute("encontrado", false);
			log.info("Piloto con id (" + pilotId + ") NO encontrado: " + pilot.get());
		}
		log.info("El id del piloto es: " + this.pilotService.findPilotById(pilotId).get());
		return "pilots/pilotDetails";
	}
	
	@GetMapping(path="/pilots/new")
	public String crearPiloto(ModelMap model) {
		log.info("Creating a new rider.");
		model.addAttribute("pilot", new Pilot());
		log.info("Leading to creation form...");
		return "pilots/pilotsEdit";
	}
	 
	@PostMapping(path="pilots/save")
	public String guardarPiloto(@Valid Pilot pilot, BindingResult result, ModelMap model) {
		String view = "pilots/pilotsListPaged";
		if(result.hasErrors()) {
			model.addAttribute("pilot", pilot);
			return "pilots/pilotsEdit";
		}else {
			pilotService.savePilot(pilot);
			model.addAttribute("message", "Rider successfully saved!");
			model.addAttribute("resultados", pilotService.findAll());
		}
		return view;
	}
	
	@GetMapping(path="pilots/delete/{pilotId}")
	public String borrarPiloto(@PathVariable("pilotId") int pilotId, ModelMap model) {
		Optional<Pilot> pilot = pilotService.findPilotById(pilotId);
		if(pilot.isPresent()) {
			pilotService.delete(pilot.get());
			model.addAttribute("message", "Rider successfully deleted!");
		}else {
			model.addAttribute("message", "Rider not found!");
		}
		return "redirect:/pilotsPaged";
	}
	
	@GetMapping(path="pilots/edit/{pilotId}")
	public String editarPiloto(@PathVariable("pilotId") int pilotId, ModelMap model) {
		Optional<Pilot> pilot = this.pilotService.findPilotById(pilotId);
		String view = "pilots/pilotsListPaged";
		model.addAttribute(pilot.get());
		if (pilot.isPresent()) {
			view = "pilots/pilotsEdit";
		} else {
			model.addAttribute("message", "Rider not found!");
		}
		return view;
	}
	
	@GetMapping(path="pilots/top")
	public String topPilotos(ModelMap model) {
		log.info("Mostrando top");
		Category category = Category.MOTO3;
		List<Pilot> topPilotsMoto3 = this.pilotService.top10Pilots(category);
		 category = Category.MOTO2;
		List<Pilot> topPilotsMoto2 = this.pilotService.top10Pilots(category);
		 category = Category.MOTOGP;
		List<Pilot> topPilotsMotoGP = this.pilotService.top10Pilots(category);
		List<Pilot> top3PilotsPerCategory= new ArrayList<Pilot>();
		top3PilotsPerCategory.add(topPilotsMotoGP.get(0));
		top3PilotsPerCategory.add(topPilotsMotoGP.get(1));
		top3PilotsPerCategory.add(topPilotsMotoGP.get(2));
		
		top3PilotsPerCategory.add(topPilotsMoto2.get(0));
		top3PilotsPerCategory.add(topPilotsMoto2.get(1));
		top3PilotsPerCategory.add(topPilotsMoto2.get(2));
		
		top3PilotsPerCategory.add(topPilotsMoto3.get(0));
		top3PilotsPerCategory.add(topPilotsMoto3.get(1));
		top3PilotsPerCategory.add(topPilotsMoto3.get(2));
		
		model.put("listasPilotos",top3PilotsPerCategory);
		System.out.println(top3PilotsPerCategory);
		return "/pilots/topPilots";
	}
	
}


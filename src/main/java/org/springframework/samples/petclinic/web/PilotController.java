/*
d * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;




import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.web.validator.PilotValidator;
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



/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@Slf4j
public class PilotController {
	
	@InitBinder("pilot")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PilotValidator());
	}

//	private static final String VIEWS_PILOT_CREATE_OR_UPDATE_FORM = "pilots/createOrUpdatePilotForm";
//
//	private final PilotService pilotService;
//
//	@Autowired
//	public PilotController(PilotService pilotService, UserService userService, AuthoritiesService authoritiesService) {
//		this.pilotService = pilotService;
//	}
//
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
//
//	@GetMapping(value = "/pilots/new")
//	public String initCreationForm(Map<String, Object> model) {
//		Pilot pilot = new Pilot();
//		model.put("pilot", pilot);
//		return VIEWS_PILOT_CREATE_OR_UPDATE_FORM;
//	}
//
//	@PostMapping(value = "/pilots/new")
//	public String processCreationForm(@Valid Pilot pilot, BindingResult result) {
//		if (result.hasErrors()) {
//			return VIEWS_PILOT_CREATE_OR_UPDATE_FORM;
//		}
//		else {
//			//creating pilot
//			this.pilotService.savePilot(pilot);
//			
//			return "redirect:/pilots/" + pilot.getId();
//		}
//	}
//
//	@GetMapping(value = "/pilots/find")
//	public String initFindForm(Map<String, Object> model) {
//		model.put("pilot", new Pilot());
//		return "pilots/findPilots";
//	}
//
//	@GetMapping(value = "/pilots")
//	public String processFindForm(Pilot pilot, BindingResult result, Map<String, Object> model) {
//		
//		 //allow parameterless GET request for /pilots to return all records
//
//		if (pilot.getLastName() == null) {
//			pilot.setLastName(""); // empty string signifies broadest possible search
//		}
//
//		// find pilots by last name
//		Collection<Pilot> results = this.pilotService.findPilotByLastName(pilot.getLastName());
//		if (results.isEmpty()) {
//			// no pilots found
//			result.rejectValue("lastName", "notFound", "not found");
//			return "pilots/findPilots";
//		}
//		else if (results.size() == 1) {
//			// 1 pilot found
//			pilot = results.iterator().next();
//			return "redirect:/pilots/" + pilot.getId();
//		}
//		else {
//			// multiple pilots found
//			model.put("selections", results);
//			return "pilots/pilotsList";
//		}
//	}
//
//
//	@GetMapping(value = "/pilots/{pilotId}/edit")
//	public String initUpdatePilotForm(@PathVariable("pilotId") int pilotId, Model model) {
//		Pilot pilot = this.pilotService.findPilotById(pilotId);
//		model.addAttribute(pilot);
//		return VIEWS_PILOT_CREATE_OR_UPDATE_FORM;
//	}
//
//	@PostMapping(value = "/pilots/{pilotId}/edit")
//	public String processUpdatePilotForm(@Valid Pilot pilot, BindingResult result,
//			@PathVariable("pilotId") int pilotId) {
//		if (result.hasErrors()) {
//			return VIEWS_PILOT_CREATE_OR_UPDATE_FORM;
//		}
//		else {
//			pilot.setId(pilotId);
//			this.pilotService.savePilot(pilot);
//			return "redirect:/pilots/{pilotId}";
//		}
//	}
//
//	/**
//	 * Custom handler for displaying an pilot.
//	 * @param pilotId the ID of the pilot to display
//	 * @return a ModelMap with the model attributes for the view
//	 */
//	@GetMapping("/pilots/{pilotId}")
//	public ModelAndView showPilot(@PathVariable("pilotId") int pilotId) {
//		ModelAndView mav = new ModelAndView("pilots/pilotDetails");
//		mav.addObject(this.pilotService.findPilotById(pilotId));
//		return mav;
//	}

	@Autowired
	PilotService pilotService;
	
//	@GetMapping("/pilots")
//	public String listadoPilotos(ModelMap modelMap) {
//		List<Pilot> pilotos = StreamSupport.stream(pilotService.findAll().spliterator(), false).collect(Collectors.toList());
////		Collections.sort(pilotos);//.sort(Comparator.naturalOrder());
//		modelMap.addAttribute("resultados", pilotos);
//		return "pilots/pilotsList";
//	}
	
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
	
//	@GetMapping(path="/pilots/{pilotId}")
//	public ModelAndView muestraPilotoPorId(@PathVariable("pilotId") int pilotId) {
//		ModelAndView mav = new ModelAndView("pilot/pilotDetails");
//		mav.addObject(this.pilotService.findPilotById(pilotId));
//		return mav;
//	}
	
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
		if(pilot.isPresent()) {
			view = "pilots/pilotsEdit";
		}else {
			model.addAttribute("message", "Rider not found!");
//			view=listadoPilotosPaginado(model);
		}
		return view;
	}
	
	
}


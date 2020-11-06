/*
 * Copyright 2002-2013 the original author or authors.
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

import java.util.Collection;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class PilotController {

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
	
	@GetMapping("/pilots")
	public String listadoPilotos(ModelMap modelMap) {
		modelMap.addAttribute("resultados", pilotService.findAll());
		return "pilots/pilotsList";
	}
	
	@GetMapping(path="/pilots/new")
	public String crearPiloto(ModelMap model) {
		model.addAttribute("pilot", new Pilot());
		return "pilots/pilotsEdit";
	}
	
	@PostMapping(path="pilots/save")
	public String guardarPiloto(@Valid Pilot pilot, BindingResult result, ModelMap model) {
		String view = "pilots/pilotsList";
		if(result.hasErrors()) {
			model.addAttribute("pilot", pilot);
			return "pilots/pilotsEdit";
		}else {
			pilotService.savePilot(pilot);
			model.addAttribute("message", "Rider successfully saved!");
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
		return listadoPilotos(model);
	}
	
	@GetMapping(path="pilots/edit/{pilotId}")
	public String editarPiloto(@PathVariable("pilotId") int pilotId, ModelMap model) {
		Optional<Pilot> pilot = this.pilotService.findPilotById(pilotId);
		String view = "pilots/pilotsList";
		model.addAttribute(pilot.get());
		if(pilot.isPresent()) {
			view = "pilots/pilotsEdit";
			
		}else {
			model.addAttribute("message", "Rider not found!");
			view=listadoPilotos(model);
		}
		return view;
	}
	
	
}


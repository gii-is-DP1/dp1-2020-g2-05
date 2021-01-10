package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.ResultService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import motogpAPI.RaceCode;


@Slf4j
@Controller
@RequestMapping("/granPremios/{gpId}")
public class ResultController {


//	private static final String VIEWS_RESULT_CREATE_OR_UPDATE_FORM = "result/createOrUpdateResultForm";

	private  ResultService resultService;
	private GranPremioService GPService;
//	private  PilotService pilotService;

	@Autowired
	public ResultController(ResultService resultService, GranPremioService GPService) {//PilotService pilotService) {
		this.resultService = resultService;
		this.GPService = GPService;
//		this.pilotService = pilotService;
	}

	//		@ModelAttribute("types")
	//		public Collection<Result> resultList(String name) {
	//			return this.resultService.findResults(name);
	//		}

//	@ModelAttribute("pilot")
//	public Optional<Pilot> findPilot(@PathVariable("pilotId") int pilotId) {
//		return this.pilotService.findPilotById(pilotId);
//	}

	/*@ModelAttribute("pet")
		public Pet findPet(@PathVariable("petId") Integer petId) {
	            Pet result=null;
			if(petId!=null)
	                    result=this.clinicService.findPetById(petId);
	                else
	                    result=new Pet();
	            return result;
		}*/
//
//	@InitBinder("pilot")
//	public void initPilotBinder(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}

	//		@InitBinder("result")
	//		public void initResultBinder(WebDataBinder dataBinder) {
	//			dataBinder.setValidator(new ResultValidator());
	//		}

//	@GetMapping(value = "/result/new")
//	public String initCreationForm(Pilot pilot, ModelMap model) {
//		Result result = new Result();
//		pilot.addResult(result);
//		model.put("result", result);
//		return VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
//	}
	
	@GetMapping(value = "/results/{category}/{raceCode}")
	public String showResults(@PathVariable("category") String category, @PathVariable("raceCode") String raceCode,
			@PathVariable("gpId") Integer gpId, ModelMap model) {
		
		log.info("Showing results for GP with id (" + gpId + ").");
		GranPremio gp = this.GPService.findGPById(gpId).get();
		List<Result> listaResultados = this.resultService.findResultsByCategoryAndId(gpId, raceCode, Category.valueOf(category));
		Record record = gp.getRecord();
		log.debug("Results: " + listaResultados);
		log.debug("Records: " + record);
		model.addAttribute("gp", gp);
		model.addAttribute("resultados",listaResultados);
		model.addAttribute("record", record);
		return "results/resultsByCategory"; 
	}
	
//	@PostMapping(value = "/result/new")
//	public String processCreationForm(Pilot pilot, @Valid Result result0, BindingResult result, ModelMap model) {		
//		if (result.hasErrors()) {
//			model.put("result0", result0);
//			return VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
//		}
//		else {
//			try{
//				pilot.addResult(result0);;
//				this.resultService.saveResult(result0);
//			}catch(Exception ex){
//				result.rejectValue("name", "duplicate", "already exists");
//				return VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
//			}
//			return "redirect:/result/{resultId}";
//		}
//	}

//	@GetMapping(value = "/pets/{petId}/edit")
//	public String initUpdateForm(@PathVariable("resultId") int id, ModelMap model) {
//		Result result = this.resultService.findResultById(id).get();
//		model.put("result", result);
//		return VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
//	}
//
//
//	@PostMapping(value = "/result/{resultId}/edit")
//	public String processUpdateForm(@Valid Result result0, BindingResult result, Pilot pilot,@PathVariable("resultId") int resultId, ModelMap model) {
//		if (result.hasErrors()) {
//			model.put("result0", result0);
//			return VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
//		}
//		else {
//			Result resultToUpdate=this.resultService.findResultById(resultId).get();
//			BeanUtils.copyProperties(result0, resultToUpdate, "id","pilot","results");                                                                                  
//			try {                    
//				this.resultService.saveResult(resultToUpdate);
//			} catch (Exception ex) {
//				result.rejectValue("name", "duplicate", "already exists");
//				return VIEWS_RESULT_CREATE_OR_UPDATE_FORM;
//			}
//			return "redirect:/pilot/{pilotId}";
//		}
//	}

}

package org.springframework.samples.dreamgp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Record;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.service.GranPremioService;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.samples.dreamgp.service.ResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/granPremios/{gpId}")
public class ResultController {


//	private static final String VIEWS_RESULT_CREATE_OR_UPDATE_FORM = "result/createOrUpdateResultForm";

	private  ResultService resultService;
	private GranPremioService GPService;
	private LineupService lineupService;
//	private  PilotService pilotService;

	@Autowired
	public ResultController(ResultService resultService, GranPremioService GPService,LineupService lineupService) {//PilotService pilotService) {
		this.resultService = resultService;
		this.GPService = GPService;
		this.lineupService=lineupService;
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
		Category categoria = Category.valueOf(category);
		Record record = gp.getRecord(categoria);
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


//	@GetMapping(value = "/validateResults/{code}")
//	public String validateResults(@PathVariable("gpId") Integer gpId,@PathVariable("code") String code,ModelMap model) {
//	
//		List<Lineup> listaLineups =this.lineupService.findAll();
//		
//		
//		this.resultService.validateResults(listaLineups, todosLosResultados,gpId);
//		log.info("resultados validados  correctamente");
//		this.resultService.createTimeMessage();
//		log.info("mensaje temporal creado hasta mañana a las " + LocalTime.now());
//
//		return "redirect:/controlPanelSP"; 
//	}
}

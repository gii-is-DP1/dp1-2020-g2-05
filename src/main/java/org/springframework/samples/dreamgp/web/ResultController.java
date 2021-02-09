package org.springframework.samples.dreamgp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Record;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.service.GranPremioService;
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


	private  ResultService resultService;
	private GranPremioService GPService;

	@Autowired
	public ResultController(ResultService resultService, GranPremioService GPService) {
		this.resultService = resultService;
		this.GPService = GPService;
	}
	
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
}

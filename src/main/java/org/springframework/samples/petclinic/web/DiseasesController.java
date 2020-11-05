package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.service.DiseasesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diseases")
public class DiseasesController {
	
	private final DiseasesService diseasesService;
	
	@Autowired
	public DiseasesController(DiseasesService diseasesService) {
		this.diseasesService = diseasesService;
	}

	@GetMapping
	public String diseasesList(ModelMap model) {
		model.put("diseases", diseasesService.getDiseases());
		return "diseases/DiseasesListing";
	}
	
	@GetMapping("/{id}/edit")
	public String diseaseEdit(@PathVariable("id") int id, ModelMap model) {
		Optional<Disease> d = diseasesService.getDisease(id);
		if(!d.isPresent()) {
			model.addAttribute("message", "No se encuentra la enfermedad a editar");
			return diseasesList(model);
		}else {
			model.put("disease", d.get());
			return "diseases/DiseaseEdit";
		}
	}
	
	@PostMapping("/{id}/edit")
	public String diseaseSave(@PathVariable("id") int id,@Valid Disease dEdit, BindingResult binding, ModelMap model) {
		Optional<Disease> d = diseasesService.getDisease(id);
		if(binding.hasErrors()) {
			return "diseases/DiseaseEdit";
		}else {
			BeanUtils.copyProperties(dEdit, d.get(), "id");
			diseasesService.saveDisease(dEdit);
			model.addAttribute("message", "Enfermedad actualizada correctamente");
			return diseasesList(model);
		}
	}
	
	@GetMapping("/{id}/delete")
	public String diseaseDelete(@PathVariable("id") int id, ModelMap model) {
		Optional<Disease> d = diseasesService.getDisease(id);
		if(!d.isPresent()) {
			model.addAttribute("message", "No se encuentra la enfermedad a eliminar");
			return diseasesList(model);
		}else {
			diseasesService.deleteDisease(d.get());
			model.addAttribute("message", "Enfermedad eliminada correctamente");
			return diseasesList(model);
		}
	}
	
	@GetMapping("/new")
	public String diseaseFormAdd(ModelMap model) {
		model.addAttribute("disease",new Disease());
		return "diseases/DiseaseEdit";
	}
	
	@PostMapping("/new")
	public String diseaseAdd(@Valid Disease d, BindingResult binding, ModelMap model) {
		if(binding.hasErrors()) {
			return "diseases/DiseaseEdit";
		}else {
			diseasesService.saveDisease(d);
			model.addAttribute("message", "Enfermedad añadida correctamente");
			return diseasesList(model);
		}
	}
}

package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.repository.DiseasesRepository;
import org.springframework.stereotype.Service;

@Service
public class DiseasesService {
	
	private DiseasesRepository diseasesRepository;
	
	@Autowired
	public DiseasesService(DiseasesRepository diseasesRepository) {
		this.diseasesRepository = diseasesRepository;
	}
	
	public Iterable<Disease> getDiseases(){
		return diseasesRepository.findAll();
	}
	
	public Optional<Disease> getDisease(Integer id){
		return diseasesRepository.findById(id);
	}
	
	public void saveDisease(@Valid Disease d){
		diseasesRepository.save(d);
	}
	
	public void deleteDisease(Disease d){
		diseasesRepository.delete(d);
	}
}

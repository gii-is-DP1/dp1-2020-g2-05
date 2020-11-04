package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.ResultRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.transaction.annotation.Transactional;

public class ResultService {
	
	
	private ResultRepository resultRepository;
	
	private VisitRepository visitRepository;
	

	@Autowired
	public ResultService(ResultRepository resultRepository,
			VisitRepository visitRepository) {
		this.resultRepository = resultRepository;
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Result> findResults(String name) throws DataAccessException {
		return resultRepository.findResults(name);
	}
	
	@Transactional
	public void saveResult(Result result) throws DataAccessException {
		visitRepository.save(result);
	}

	@Transactional(readOnly = true)
	public Result findResultById(int id) throws DataAccessException {
		return resultRepository.findById(id);
	}

//	@Transactional(rollbackFor = DuplicatedPetNameException.class)
//	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
//			Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
//            if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && otherPet.getId()!=pet.getId())) {            	
//            	throw new DuplicatedPetNameException();
//            }else
//                petRepository.save(pet);                
//	}


	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}
}

package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import motogpAPI.RaceCode;

@Service
public class ResultService {
	

	private ResultRepository resultRepository;
	
	@Autowired
	public ResultService(ResultRepository resultRepository) {
		this.resultRepository = resultRepository;
	}

//	@Transactional(readOnly = true)
//	public Collection<Result> findResults(String name) throws DataAccessException {
//		return resultRepository.findResults(name);
//	}
//	
	@Transactional
	public void saveResult(Result result) throws DataAccessException {
		resultRepository.save(result);
	}

	@Transactional(readOnly = true)
	public Optional<Result> findResultById(int id) throws DataAccessException {
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
	@Transactional
	public List<Result> findResultsByCategoryAndId(Integer gpId, String raceCode,Category category) throws DataAccessException {
		return resultRepository.findResultsByCategoryAndId(gpId, raceCode,category);
	}
	
	
	public List<Result> findAll() {
		return (List<Result>) resultRepository.findAll();
	}
	
	
	
	
}

package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.repository.RecruitRepository;
import org.springframework.stereotype.Service;

@Service
public class RecruitService {
	
	@Autowired
	private RecruitRepository recruitRepository;
	
	@Autowired
	private PilotService pilotService;

	@Autowired
	public RecruitService(RecruitRepository recruitRepository) {
		this.recruitRepository = recruitRepository;
	}

//	@Transactional(readOnly = true)
//	public List<Recruit> getRecruits(Integer teamID) throws DataAccessException {
//		return this.recruitRepository.listTeamRecruits(teamID);
//	}
	
	public Optional<Recruit> findRecruit(Integer recruitId) {
		return recruitRepository.findById(recruitId);
	}
	
	public List<Pilot> getRecruits(int teamID) throws DataAccessException {
		return this.pilotService.getRecruits(teamID);
	}

}

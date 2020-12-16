package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.repository.RecruitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<Pilot> getRecruits() throws DataAccessException {
		return this.pilotService.getRecruits();
	}
	
	public List<Pilot> getRecruits(int teamID) throws DataAccessException {
		return this.pilotService.getRecruits(teamID);
	}
	
	@Transactional
	public void saveRecruit(Pilot pilot, Team team) throws DataAccessException {
		Recruit recruit = createRecruit(pilot, team);
		this.recruitRepository.save(recruit);
	}
	
	private Recruit createRecruit(Pilot pilot, Team team) {
		Recruit recruit = new Recruit();
		recruit.setPilot(pilot);
		recruit.setTeam(team);
		return recruit;
	}

	public Optional<Recruit> getRecruitByPilotId(int pilotId) throws DataAccessException {
		return this.recruitRepository.findRecruitByPilotId(pilotId);
	}
	
	public List<Recruit> getRecruitsByTeam(int teamID) throws DataAccessException {
		return this.recruitRepository.findAllRecruits(teamID);
	}
}

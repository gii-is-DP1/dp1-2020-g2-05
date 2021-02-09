package org.springframework.samples.dreamgp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.repository.RecruitRepository;
import org.springframework.samples.dreamgp.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruitService {

	private RecruitRepository recruitRepository;

	private PilotService pilotService;

	@Autowired
	public RecruitService(RecruitRepository recruitRepository, PilotService pilotService) {
		this.recruitRepository = recruitRepository;
		this.pilotService = pilotService;
	}

	// Operaciones de Create

	private Recruit createRecruit(Pilot pilot, Team team) {
		Recruit recruit = new Recruit();
		recruit.setForSale(false);
		recruit.setPilot(pilot);
		recruit.setTeam(team);
		return recruit;
	}

	@Transactional
	public void saveRecruit(Pilot pilot, Team team) throws DataAccessException {
		Recruit recruit = createRecruit(pilot, team);
		this.recruitRepository.save(recruit);
	}

	@Transactional
	public void saveRecruit(Recruit recruit) throws DataAccessException {
		this.recruitRepository.save(recruit);
	}

	// Operaciones de Read

	@Transactional(readOnly = true)
	public List<Recruit> findAll() {
		return (List<Recruit>) this.recruitRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Recruit> findRecruitById(Integer recruitId) { // Encuentra un fichaje en base a su ID de fichaje
		return recruitRepository.findById(recruitId);
	}

	@Transactional(readOnly = true)
	public Optional<Recruit> getRecruitByPilotId(int pilotId, int leagueId) throws DataAccessException {
		return this.recruitRepository.findRecruitByPilotId(pilotId, leagueId);
	}

	@Transactional(readOnly = true)
	public List<Pilot> getRecruits() throws DataAccessException { // Nos da todos los pilotos correspondientes a los
																	// fichajes que existen en el sistema
		return this.pilotService.getRecruits();
	}
	
	@Transactional(readOnly = true)
	public List<Recruit> getRecruitsByTeam(int teamID) {
		return this.recruitRepository.findAllRecruitsByTeam(teamID);
	}

	@Transactional(readOnly = true)
	public List<Recruit> getRecruitsOnSaleByTeam(int teamID) {
		return this.recruitRepository.findAllRecruitsOnSaleByTeam(teamID);
	}

	@Transactional(readOnly = true)
	public List<Recruit> getRecruitsNotOnSaleByTeam(int teamID) {
		return this.recruitRepository.findAllRecruitsNotOnSaleByTeam(teamID);
	}

	// Operaciones de Update

	@Transactional(rollbackFor = NotAllowedNumberOfRecruitsException.class)
	public boolean putOnSale(Recruit recruit) throws NotAllowedNumberOfRecruitsException {
		if (getRecruitsNotOnSaleByTeam(recruit.getTeam().getId()).size() == 2) {
			throw new NotAllowedNumberOfRecruitsException();
		} else {
			recruit.setForSale(true);
			this.recruitRepository.save(recruit);
		}
		return true;
	}

	@Transactional
	public void quitOnSale(Recruit recruit) {
		recruit.setForSale(false);
		this.recruitRepository.save(recruit);
	}

	@Transactional(rollbackFor = NotAllowedNumberOfRecruitsException.class)
	public Boolean purchaseRecruit(Recruit recruit, Team purchaserTeam)
			throws DataAccessException, NotAllowedNumberOfRecruitsException {
		if (this.getRecruitsByTeam(purchaserTeam.getId()).size() == 4) {
			throw new NotAllowedNumberOfRecruitsException();
		} else {
			recruit.setTeam(purchaserTeam);
			recruit.setForSale(false);
			this.recruitRepository.save(recruit);
		}
		return true;
	}

	// Operaciones de Delete

	@Transactional
	public void deleteRecruit(Recruit recruit) {
		this.recruitRepository.delete(recruit);
	}

}

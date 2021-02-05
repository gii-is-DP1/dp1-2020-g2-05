package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.RecruitRepository;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
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

	@Transactional(readOnly = true)
	public Optional<Recruit> findRecruit(Integer recruitId) { // Encuentra un fichaje en base a su ID de fichaje
		return recruitRepository.findById(recruitId);
	}

	@Transactional(readOnly = true)
	public List<Pilot> getRecruits() throws DataAccessException { // Nos da todos los pilotos correspondientes a los
																	// fichajes que existen en el sistema
		return this.pilotService.getRecruits();
	}

	@Transactional(readOnly = true)
	public List<Pilot> getPilotsByTeam(int teamID) throws DataAccessException { // Nos da todos los pilotos que posee un
		// equipo concreto ahora mismo
		return this.pilotService.getRecruits(teamID);
	}

	@Transactional(readOnly = true)
	public List<Recruit> getRecruitsByTeam(int teamID) throws DataAccessException { // Muestra todos los fichajes de un
																					// mismo equipo
		return this.recruitRepository.findAllRecruits(teamID);
	}

	@Transactional(readOnly = true)
	public List<Recruit> getRecruitsOnSaleByTeam(int teamID) {
		return this.recruitRepository.findAllRecruitSOnSaleByTeam(teamID);
	}

	@Transactional(readOnly = true)
	public List<Recruit> getRecruitsNotOnSaleByTeam(int teamID) {
		return this.recruitRepository.findAllRecruitSNotOnSaleByTeam(teamID);
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

	@Transactional(rollbackFor = NotAllowedNumberOfRecruitsException.class)
	public void sellRecruit(Recruit recruit) throws DataAccessException, NotAllowedNumberOfRecruitsException {
		Team sellerTeam = recruit.getTeam();
		if (sellerTeam.getRecruits().size() == 2) {
			throw new NotAllowedNumberOfRecruitsException();
		} else {
			this.recruitRepository.delete(recruit);
		}
	}

	private Recruit createRecruit(Pilot pilot, Team team) {
		Recruit recruit = new Recruit();
		recruit.setForSale(false);
		recruit.setPilot(pilot);
		recruit.setTeam(team);
		return recruit;
	}

	public Optional<Recruit> getRecruitByPilotId(int pilotId, int leagueId) throws DataAccessException {
		return this.recruitRepository.findRecruitByPilotId(pilotId, leagueId);
	}

	public Iterable<Recruit> findAll() {
		return this.recruitRepository.findAll();
	}

	@Transactional(rollbackFor = NotAllowedNumberOfRecruitsException.class)
	public void purchaseRecruit(Pilot pilot, Team purchaserTeam)
			throws DataAccessException, NotAllowedNumberOfRecruitsException {
		if (purchaserTeam.getRecruits().size() == 4) {
			throw new NotAllowedNumberOfRecruitsException();
		} else {
			// Segundo añado el recruit al equipo que compra
			saveRecruit(pilot, purchaserTeam);
		}

	}

	@Transactional(rollbackFor = NotAllowedNumberOfRecruitsException.class)
	public void putOnSale(Recruit recruit) throws NotAllowedNumberOfRecruitsException {
		if (getRecruitsNotOnSaleByTeam(recruit.getTeam().getId()).size() == 2)
			throw new NotAllowedNumberOfRecruitsException();
		this.recruitRepository.putRecruitOnSale(recruit.getId());
	}

	@Transactional
	public void quitOnSale(Recruit recruit) {
		this.recruitRepository.quitRecruitOnSale(recruit.getId());
	}

	@Transactional
	public void deleteRecruit(Recruit r) {
		this.recruitRepository.delete(r);
	}

	@Transactional
	public void trade(Recruit recruit, Team purchaserTeam) {
		// Primero elimino el recruit del equipo que vende
		deleteRecruit(recruit);
		// Segundo añado el recruit al equipo que compra
		saveRecruit(recruit.getPilot(), purchaserTeam);
	}

}

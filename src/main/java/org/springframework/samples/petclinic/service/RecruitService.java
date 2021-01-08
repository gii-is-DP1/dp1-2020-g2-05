package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.RecruitRepository;
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

	public Optional<Recruit> findRecruit(Integer recruitId) { // Encuentra un fichaje en base a su ID de fichaje
		return recruitRepository.findById(recruitId);
	}

	public List<Pilot> getRecruits() throws DataAccessException { // Nos da todos los pilotos correspondientes a los
																	// fichajes que existen en el sistema
		return this.pilotService.getRecruits();
	}

	public List<Pilot> getPilotsByTeam(int teamID) throws DataAccessException { // Nos da todos los pilotos que posee un
		// equipo concreto ahora mismo
		return this.pilotService.getRecruits(teamID);
	}

	public List<Recruit> getRecruitsByTeam(int teamID) throws DataAccessException { // Muestra todos los fichajes de un
																					// mismo equipo
		return this.recruitRepository.findAllRecruits(teamID);
	}

	@Transactional
	public void saveRecruit(Pilot pilot, Team team) throws DataAccessException { // Para guardar nuevos fichajes en la
																					// base de datos
		Recruit recruit = createRecruit(pilot, team);
		this.recruitRepository.save(recruit);
	}

	@Transactional
	public void deleteRecruit(Recruit recruit) throws DataAccessException { // Para borrar fichajes de la BBDD
																			// al
		this.recruitRepository.deleteById(recruit.getId());

	}

	private Recruit createRecruit(Pilot pilot, Team team) {
		Recruit recruit = new Recruit();
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

	public void trade(Recruit recruit, Team sellerTeam, Team purchaserTeam) {
		// Primero elimino el recruit del equipo que vende
		deleteRecruit(recruit);
		// Segundo añado el recruit al equipo que compra
		saveRecruit(recruit.getPilot(), purchaserTeam);

	}
}

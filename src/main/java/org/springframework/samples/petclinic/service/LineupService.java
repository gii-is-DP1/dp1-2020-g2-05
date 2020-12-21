package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.repository.LineupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineupService {
	private LineupRepository lineupRepository;

	@Autowired
	public LineupService(LineupRepository lineupRepository) {
		this.lineupRepository = lineupRepository;
	}

	@Transactional
	public void saveLineup(Lineup lineup) throws DataAccessException {
		lineupRepository.save(lineup);
	}

	@Transactional
	public List<Lineup> findAll(){
		return (List<Lineup>) lineupRepository.findAll();
	}

	public Optional<Lineup> findLineup(Integer lineupId) {
		return lineupRepository.findById(lineupId);
	}
	
	public void delete(Lineup lineup) {
		lineupRepository.delete(lineup);
	}
	
	public List<Lineup> findByTeam(int team_id) {
		return this.lineupRepository.findByTeam(team_id);
	}
	
	
}

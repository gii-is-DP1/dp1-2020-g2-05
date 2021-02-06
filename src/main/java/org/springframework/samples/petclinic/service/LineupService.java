package org.springframework.samples.petclinic.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	public List<Lineup> findByTeam(int teamId) {
		return this.lineupRepository.findByTeam(teamId).stream().sorted(Comparator.comparing(x -> x.getGp().getDate0())).collect(Collectors.toList());
	}
	
	public List<Lineup> findByGpId(Integer gpId) {
		return this.lineupRepository.findByGp(gpId);
	}
	
}

package org.springframework.samples.dreamgp.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.repository.LineupRepository;
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
	
	public List<Lineup> findByRecruit(int recruitId) {
		return this.lineupRepository.findByRecruit(recruitId);
	}

	public List<Integer> calculaEstadisticas(Integer lineupsAntiguos) {
		List<Lineup> allLineups = this.findAll();
		Integer lineupsActuales = allLineups.size();
		
		if(lineupsAntiguos == null) {
			lineupsAntiguos = 2;
		}
		
		List<Integer> res = new ArrayList<Integer>();
		if (lineupsActuales > lineupsAntiguos) {
			res.add(1);
			res.add(lineupsActuales - lineupsAntiguos);
		} else if (lineupsActuales < lineupsAntiguos) {
			res.add(2);
			res.add(lineupsAntiguos - lineupsActuales);
		} else {
			res.add(0);
			res.add(0);
		}
		
		res.add(lineupsActuales);
		return res;
	}
}

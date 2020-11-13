package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeagueService {
	private LeagueRepository leagueRepository;
	//private TeamRepository teamRepository;

	@Autowired
	public LeagueService(LeagueRepository leagueRepository) {
		this.leagueRepository = leagueRepository;
	}
//	
//	@Autowired
//	public TeamService(TeamRepository teamRepository) {
//		this.leagueRepository = teamRepository;
//	}

	@Transactional
	public void saveLeague(League league) throws DataAccessException {
		leagueRepository.save(league);
	}
	
	@Transactional
	public Iterable<League> findAll(){
		return leagueRepository.findAll();
	}
	
//	@Transactional
//	public void saveTeam(Team team) throws DataAccessException {
//		leagueRepository.save(team);
//	}
	
	
	public Optional<League> findLeague(Integer leagueId) {
		return leagueRepository.findById(leagueId);
	}
	

//	public Optional<Team> findTeam(Integer teamId) {
//		return leagueRepository.findById(teamId);
//	}

}

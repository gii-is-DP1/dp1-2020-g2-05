package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeagueService {
	private LeagueRepository leagueRepository;
	private TeamRepository teamRepository;

	@Autowired
	public LeagueService(LeagueRepository leagueRepository, TeamRepository teamRepository) {
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
	}
	
//	@Autowired
//	public TeamService(TeamRepository teamRepository) {
//		this.teamRepository = teamRepository;
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



	public void saveTeam(Team team)  throws DataAccessException{
		System.out.println(team);
		teamRepository.save(team);
		}
	

//	public Optional<Team> findTeam(Integer teamId) {
//		return leagueRepository.findById(teamId);
//	}

}

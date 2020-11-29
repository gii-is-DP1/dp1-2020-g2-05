package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNameException;
import org.springframework.samples.petclinic.web.duplicatedLeagueNameException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeagueService {
	private LeagueRepository leagueRepository;
	private TeamRepository teamRepository;
	private UserService userService;

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
	public void saveLeague(League league) throws DataAccessException,duplicatedLeagueNameException{
		Iterable<League> ligas = leagueRepository.findAll();
		List<League> listLigas = new ArrayList<League>();
		ligas.forEach(listLigas::add);
		for(int i=0;i<listLigas.size();i++) {
			if(listLigas.get(i).getName().equals(league.getName())) throw new duplicatedLeagueNameException();
		}
		leagueRepository.save(league);
	}
	
	public void deleteLeague(League league) throws DataAccessException {
		leagueRepository.delete(league);
	}
	
	public Optional<League> findLeagueByLeagueCode(String leagueCode) throws DataAccessException {
		return leagueRepository.findLeagueByLeagueCode(leagueCode);
	}
	
	public Collection<Integer> findTeamsByUsername(String username) throws DataAccessException {
		return leagueRepository.findTeamsByUsername(username);
	}
	public Optional<User> findUserByUsername(String username) throws DataAccessException {
		return leagueRepository.findUserByUsername(username);
	}
	public Integer findLeaguesByUsername(String username) throws DataAccessException {
		return leagueRepository.findLeaguesByUsername(username);
	}
	
	@Modifying
	@Transactional
	public void activeMoto3(Integer leagueId) throws DataAccessException {
		leagueRepository.activeMoto3(leagueId);
	}
	
	@Modifying
	@Transactional
	public void activeMoto2(Integer leagueId) throws DataAccessException {
		leagueRepository.activeMoto2(leagueId);
	}
	
	@Modifying
	@Transactional
	public void activeMotogp(Integer leagueId) throws DataAccessException {
		leagueRepository.activeMotogp(leagueId);
	}
//	public void activeMoto2(Integer leagueId) throws DataAccessException {
//		leagueRepository.activeMoto2(leagueId);
//	}
//	public void activeMotogp(Integer leagueId) throws DataAccessException {
//		leagueRepository.activeMotogp(leagueId);
//	}
	
//	public Optional<League> incrementarCarrerasLiga(Integer leagueId) throws DataAccessException {
//		return leagueRepository.incrementarCarrerasLiga(leagueId);
//	}
//	
	

	@Transactional
	public Iterable<League> findAll(){
		return leagueRepository.findAll();
	}
	
	@Transactional
	public Iterable<Team> findAllTeams(){
		return teamRepository.findAll();
	}
	
//	@Transactional
//	public void saveTeam(Team team) throws DataAccessException {
//		leagueRepository.save(team);
//	}
	
	
	public Optional<League> findLeague(Integer leagueId) {
		return leagueRepository.findById(leagueId);
	}
	
	public Optional<Team> findTeamById(Integer teamId) {
		
		return teamRepository.findById(teamId);
	}

//	public League increaseLeagueRaces(Integer leagueId){
//		return leagueRepository.incrementarCarrerasLiga(leagueId);
//	}
	@Transactional(rollbackFor =  DuplicatedTeamNameException.class)
	public void saveTeam(Team team) {
		boolean igual =  false;
		Optional<League> league = findLeague(team.getLeague().getId());
		List<Team> list = league.get().getTeam().stream().collect(Collectors.toList());
		for(int i = 0; i<list.size(); i++) {
			Team t = list.get(i);
			if(t.getName().equals(team.getName())) {
				igual = true;
			}
		}
		
		if(!igual) {
			teamRepository.save(team);

		}else {
			System.out.println("hhhh");
		}
		
		}

	public void delete(Team team) {
		teamRepository.delete(team);
	}



	
	public List<Team> findTeamByUsername(String username){
		return teamRepository.findTeamByUsername(username );
	}
}
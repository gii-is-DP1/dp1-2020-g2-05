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
	public void saveLeague(League league) throws DataAccessException {
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
	
//	public void activeMoto3(Integer leagueId) throws DataAccessException {
//		leagueRepository.activeMoto3(leagueId);
//	}
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
	
	public User getUserSession() {
		User usuario = new User();  
		try {
			  Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			  
			  Integer index1 = auth.toString().indexOf("Username:");
			  Integer index2 = auth.toString().indexOf("; Password:"); // CON ESTO TENEMOS EL STRIN Username: user
			  
			  String nombreUsuario = auth.toString().substring(index1, index2).split(": ")[1]; //con esto hemos spliteado lo de arriba y nos hemos quedado con user.

			  Optional<User> user = findUserByUsername(nombreUsuario);
			  usuario =  user.get();
		  }catch (Exception e) {	
		  }
		return usuario;
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
	
	public Optional<Team> findTeamById(Integer teamId) {
		
		return teamRepository.findById(teamId);
	}

//	public League increaseLeagueRaces(Integer leagueId){
//		return leagueRepository.incrementarCarrerasLiga(leagueId);
//	}

	public void saveTeam(Team team)  throws DataAccessException{
		System.out.println(team);
		teamRepository.save(team);
		}

	public void delete(Team team) {
		teamRepository.delete(team);
	}



//	public Optional<Team> findTeam(Integer teamId) {
//		return leagueRepository.findById(teamId);
//	}

}

package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNameException;
import org.springframework.transaction.annotation.Transactional;

public class TeamService {
	
	private LeagueRepository leagueRepository;
	private TeamRepository teamRepository;
	private UserService userService;
	private PilotService pilotService;
	private RecruitService recruitService;
	
	private LeagueService leagueService;
	
	@Autowired
	public TeamService(LeagueRepository leagueRepository, TeamRepository teamRepository,
			UserService userService, PilotService pilotService, RecruitService recruitService) {
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
		this.userService = userService;
		this.pilotService = pilotService;
		this.recruitService = recruitService;
	}
	
	public List<Integer> findTeamsByUsername(String username) throws DataAccessException {
		return leagueRepository.findTeamsByUsername(username);
	}
	
	public Integer findTeamsByLeagueId(Integer id) throws DataAccessException {
		return leagueRepository.findTeamsByLeagueId(id);
	}
	
	@Transactional
	public Iterable<Team> findAllTeams(){
		return teamRepository.findAll();
	}
	
	public Optional<Team> findTeamById(Integer teamId) {
		return teamRepository.findById(teamId);
	}
	
	@Transactional(rollbackFor =  DuplicatedTeamNameException.class)
	public void saveTeam(Team team) {
		boolean igual =  false;
		Optional<League> league = this.leagueService.findLeague(team.getLeague().getId());
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
	
	public Optional<Team> findTeamByUsernameAndLeagueId(String username, Integer id){
		return teamRepository.findTeamByUsernameAndLeagueId(username, id);
	}
	
	public List<Team> findTeamByLeagueId(Integer id){
		return teamRepository.findTeamByLeagueId(id);
	}


}

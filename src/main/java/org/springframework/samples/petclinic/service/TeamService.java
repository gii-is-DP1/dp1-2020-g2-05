
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamService {
	
	private LeagueRepository leagueRepository;
	private TeamRepository teamRepository;
	private UserService userService;
	private PilotService pilotService;
	private RecruitService recruitService;
	private TablaConsultasService TCService;
	private OfferService offerService;
	
	private LeagueService leagueService;
	
	@Autowired
	public TeamService(LeagueRepository leagueRepository, TeamRepository teamRepository,
			UserService userService, PilotService pilotService, RecruitService recruitService, TablaConsultasService TCService, OfferService offerService) {
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
		this.userService = userService;
		this.pilotService = pilotService;
		this.recruitService = recruitService;
		this.TCService = TCService;
		this.offerService=offerService;
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
	
		League league = team.getLeague();
		List<Team> list = league.getTeam().stream().collect(Collectors.toList());
		for(int i = 0; i<list.size(); i++) {
			Team t = list.get(i);
			if(t.getName().equals(team.getName())) {
				igual = true;
			}
		}
		
		if(!igual) {
			teamRepository.save(team);

		}else {
			log.warn("No se ha podido guardar el equipo " + team);
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
	
	@Transactional
	public void saveTeamMoney(Team team, Integer price) throws DataAccessException {
		team.setMoney((team.getMoney() + price));
		saveTeam(team);
	}
	
	@Transactional
	public void saveSystemTeam(League league) {
		Team sysTeam = new Team();
		sysTeam.setName("Sistema");
		sysTeam.setLeague(league);
		sysTeam.setMoney(0);
		sysTeam.setPoints(0);
		sysTeam.setUser(userService.findUser("admin1").get());
		teamRepository.save(sysTeam);
		
		//Fichamos y ofertamos a todos los pilotos con la escudería sistema que estén en la categoría actual
		recruitAndOfferAll(sysTeam,TCService.getTabla().get().getCurrentCategory());
	}
	@Transactional
	public void recruitAndOfferAll(Team t,Category cat) {
		Iterable<Pilot> pilots = pilotService.findAll();
		List<Pilot> listPilots = new ArrayList<Pilot>();
		pilots.forEach(listPilots::add);
		for (int i=0;i<listPilots.size();i++) {
			if(listPilots.get(i).getCategory().equals(cat)) {
				recruitService.saveRecruit(listPilots.get(i),t);
				Pilot p = listPilots.get(i);
				offerService.putOnSale(recruitService.getRecruitByPilotId(p.getId()).get(), p.getBaseValue());
			}
		}
	}


}



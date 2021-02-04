
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNameException;
import org.springframework.samples.petclinic.util.Status;
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
		List<Team> list = this.findTeamByLeagueId(league.getId());
		for(int i = 0; i<list.size(); i++) {
			Team t = list.get(i);
			
			if(t.getName().equals(team.getName()) && t.getId()!=team.getId()) {
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
		team.setMoney(team.getMoney() + price);
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
		saveTeam(sysTeam);
		log.debug("Creada la escudería sisteama:" + sysTeam);
		
		log.info("Procedemos a fichar y ofertar a todos los pilotos con la escudería sistema que estén en la categoría actual");
		recruitAndOfferAll(sysTeam,TCService.getTabla().get().getCurrentCategory());
		log.debug("Fichados los pilotos de la categoría actual con la escudería sistema");
	}
	
	@Transactional
	public void recruitAndOfferAll(Team t,Category cat) {
		List<Pilot> pilots = pilotService.findAll();
		for (int i=0;i<pilots.size();i++) {
			if(pilots.get(i).getCategory().equals(cat)) {
				recruitService.saveRecruit(pilots.get(i),t);
				Pilot p = pilots.get(i);
				offerService.putOnSale(recruitService.getRecruitByPilotId(p.getId(),
						t.getLeague().getId()).get(), p.getBaseValue());
			}
		}
	}
	
	@Transactional
	public void sellAllTeamRecruits(Team t) {
		List<Recruit> recruits = recruitService.getRecruitsByTeam(t.getId());
		Integer valor = 0;
		for (int i=0;i<recruits.size();i++) {
			Recruit r = recruits.get(i);
			if(r.getForSale()){ //Si el fichaje esta en venta, ponemos su oferta asociada en denegada para evitar incoherencias
				Offer offer = r.getOffer().stream()
						.filter(o->o.getStatus().equals(Status.Outstanding)).findAny().get();
				offer.setStatus(Status.Denied);
				offerService.saveOffer(offer);
			}
			valor += r.getPilot().getBaseValue();
			recruitService.deleteRecruit(r);
		}
		saveTeamMoney(t, valor);
	}

}



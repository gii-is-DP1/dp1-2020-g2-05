package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
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
	private PilotService pilotService;
	private RecruitService recruitService;
	private TablaConsultasService TCService;
	private OfferService offerService;

	@Autowired
	public LeagueService(LeagueRepository leagueRepository, TeamRepository teamRepository,
			UserService userService, PilotService pilotService, RecruitService recruitService,
			TablaConsultasService TCService, OfferService offerService) {
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
		this.userService = userService;
		this.pilotService = pilotService;
		this.recruitService = recruitService;
		this.TCService = TCService;
		this.offerService = offerService;
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
	

	public Optional<User> findUserByUsername(String username) throws DataAccessException {
		return leagueRepository.findUserByUsername(username);
	}

	
	public String findAuthoritiesByUsername(String username) throws DataAccessException {
		return leagueRepository.findAuthoritiesByUsername(username);
	}
	
	public Integer findLeaguesByUsername(String username) throws DataAccessException {
		return leagueRepository.findLeaguesByUsername(username);
	}
	
//	@Modifying
//	@Transactional
//	public void activeCategory(Integer id, Category idCategory) {
//		 leagueRepository.activeCategory(id, idCategory);
//	}
	

//	public List<League> findAllLeaguesByCategory(Category idCategory) {
//		 return leagueRepository.findAllLeaguesByCategory(idCategory);
//	}
	
//	public void updateGPsFromLeagueWithCategory(Category category) throws DataAccessException, duplicatedLeagueNameException{
//		List<League> listaCategorias= leagueRepository.findAllLeaguesByCategory(category);
//		List<League> listaCategoriasActualizada = new ArrayList<League>();
//		for(League league:listaCategorias) {
//			Integer racesCompleted =league.getRacesCompleted()+1;
//			listaCategoriasActualizada.add(league);
//			this.leagueRepository.increaseRacesCompleted(league.getId(), racesCompleted);
//		}
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
	
	public String randomString(int longitud) {
		 String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		 String sb="";
		 Random random = new Random();
		 
	    for(int i = 0; i < longitud; i++) {

	      int index = random.nextInt(alphabet.length());

	      char randomChar = alphabet.charAt(index);

	      sb+=(randomChar);
	    }
	    return sb;
	}
	
	public <E> List<E> convertirIterableLista(Iterable<E> leagues){
		List<E> result = new ArrayList<E>();
	    leagues.forEach(result::add);
	    return result;
	}
	
	public boolean comprobarLigaVacia(List<League> result) {
		Boolean ret = false;   
		
		for(League league:result) {
			Set<Team> equipos = league.getTeam();
				
			if(equipos.size()==1) {
				if(equipos.stream().collect(Collectors.toList()).get(0).getName()=="Sistema") {
					this.deleteLeague(league);
					ret =  true;
				}
			}
			if(equipos.size()==0) {
				this.deleteLeague(league);
				ret =  true;
			}
			
		    }
		   return ret;
	}
	
//	public List<Integer> GPsPorCategoria(List<League> result) {
//	   			Integer moto2=0;
//	   			Integer moto3=0;
//	   			Integer motogp=0;
//			for(League league:result) {
//		    	if(league.getActiveCategory().equals(Category.MOTO2)) {
//		    		moto2=league.getRacesCompleted();
//		    	}else if(league.getActiveCategory().equals(Category.MOTO3)) {
//		    		moto3=league.getRacesCompleted();
//		    	}else if(league.getActiveCategory().equals(Category.MOTOGP)) {
//		    		motogp=league.getRacesCompleted();
//		    	}
//		    }
//			List<Integer> lista=new ArrayList<Integer>();
//			lista.add(moto2);
//			lista.add(moto3);
//			lista.add(motogp);
//			return lista;
//	}

	public List<League> obtenerLigasPorUsuario(Collection<Integer> collect){
	
//		List<Integer> idLeague = new ArrayList<Integer>();
//		
//		collect.forEach(idLeague::add);
	    
		List<League> myLeaguesList = collect.stream().map(x->this.findLeague(x).get()).collect(Collectors.toList());

	    
//		for(Integer i:idLeague) {
//			League league_i = this.findLeague(i).get();
//			myLeaguesList.add(league_i);
//		}
		return myLeaguesList;
	}
	
	
	@Transactional
	public Iterable<League> findAll(){
		return leagueRepository.findAll();
	}
	
	

	
	public Optional<League> findLeague(Integer leagueId) {
		return leagueRepository.findById(leagueId);
	}
	



}

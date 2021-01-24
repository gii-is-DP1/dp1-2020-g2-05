package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.LeagueRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.web.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j

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
	public LeagueService(LeagueRepository leagueRepository, TeamRepository teamRepository, UserService userService,
			PilotService pilotService, RecruitService recruitService, TablaConsultasService TCService,
			OfferService offerService) {
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
	public Boolean saveLeague(League league) throws DataAccessException, duplicatedLeagueNameException {
		log.info("Intentando guardar liga : " + league);
//		Iterable<League> ligas = leagueRepository.findAll();
		List<League> listLigas = this.findAll();
//		ligas.forEach(listLigas::add);
		for (int i = 0; i < listLigas.size(); i++) {
			if (listLigas.get(i).getName().equals(league.getName())) {
				log.warn("No se ha podido guardar la liga " + league);
				throw new duplicatedLeagueNameException("Duplicated league name");
			}
		}
		log.info("La liga '" + league + "' se ha guardado correctamente");
		leagueRepository.save(league);
		return true;
	}
	
	@Transactional
	public void deleteLeague(League league) throws DataAccessException {
		leagueRepository.delete(league);
	}
	 
	@Transactional(readOnly = true)
	public Optional<League> findLeagueByLeagueCode(String leagueCode) throws DataAccessException {
		return leagueRepository.findByLeagueCode(leagueCode);
	}

//	public Optional<User> findUserByUsername(String username) throws DataAccessException {
//		return leagueRepository.findUserByUsername(username);
//	}

	@Transactional(readOnly = true)
	public String findAuthoritiesByUsername(String username) throws DataAccessException {
		return leagueRepository.findAuthoritiesByUsername(username);
	}

	@Transactional(readOnly = true)
	public Integer findLeaguesByUsername(String username) throws DataAccessException {
		return leagueRepository.findLeaguesByUsername(username);
	}

	public String randomString(int longitud) {
		log.info("Autogenerando codigo para una liga");
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		String sb = "";
		Random random = new Random();

		for (int i = 0; i < longitud; i++) {

			int index = random.nextInt(alphabet.length());

			char randomChar = alphabet.charAt(index);

			sb += (randomChar);
		}
		return sb;
	}

	public <E> List<E> convertirIterableLista(Iterable<E> leagues) {
		List<E> result = new ArrayList<E>();
		leagues.forEach(result::add);
		return result;
	}

	@Transactional
	public boolean comprobarLigaVacia(List<League> result) {
		Boolean ret = false;

		for (League league : result) {
			Set<Team> equipos = league.getTeam();
			if (equipos.size() == 1) {
				if (equipos.stream().collect(Collectors.toList()).get(0).getName().equals("Sistema")) {
					this.deleteLeague(league);
					ret = true;
					log.warn("Se ha detectado una liga sin equipos : " + league);

				}
			}else if(equipos.isEmpty()) {
				this.deleteLeague(league);
				ret = true;
				log.warn("Se ha detectado una liga sin equipos : " + league);
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

	
	public List<League> obtenerListaIntegerToTeams(Collection<Integer> collect) {

		List<League> myLeaguesList = collect.stream().map(x -> this.findLeague(x).get()).collect(Collectors.toList());

		return myLeaguesList;
	}

	@Transactional
	public List<League> findAll() {
		List<League> listaReturn = new ArrayList<>();
		leagueRepository.findAll().forEach(listaReturn::add);
		return  listaReturn;
	}

	public Optional<League> findLeague(Integer leagueId) {
		return leagueRepository.findById(leagueId);
	}

	
	public ModelMap descifraUri(String pth,String code,ModelMap model) {
		//dependiendo del path descifro lo que la api no ha podido encontrar
		// el index 0 significa cuantos no ha podido encontrar(1 a 3)
		//los sigiuentes significan, 3 moto3,2 moto2,GP motoGP
		//por ejemplo el path 12 significa que no ha encontrado un resultado y que es moto2
		// el path 23G que no ha encontrado 2 y que es moto3 y moto gp
		//el path  332G que no ha encontrado 3 y que son moto3,moto2 y motogp
		//...	
		if(pth.charAt(0)=='1'){
			if(pth.charAt(1)=='3') {
				model.addAttribute("messageMoto3NotFound",
						"API has not found any result to code " + code + " for moto3");
			}else if(pth.charAt(1)=='2') {
				model.addAttribute("messageMoto2NotFound",
						"API has not found any result to code " + code + " for moto2");
			}else if(pth.charAt(1)=='G') {
				model.addAttribute("messageMotogpNotFound",
						"API has not found any result to code " + code + " for motogp");
			}
		}else if(pth.charAt(0)=='2') {
			if(pth.charAt(1)=='3' && pth.charAt(2)=='2') {
				model.addAttribute("messageMoto3NotFound",
						"API has not found any result to code " + code + " for moto3");
				model.addAttribute("messageMoto2NotFound",
						"API has not found any result to code " + code + " for moto2");
			}else if(pth.charAt(1)=='3' && pth.charAt(2)=='G') {
				model.addAttribute("messageMoto3NotFound",
						"API has not found any result to code " + code + " for moto3");
				model.addAttribute("messageMotogpNotFound",
						"API has not found any result to code " + code + " for motogp");
			}else if(pth.charAt(1)=='2' && pth.charAt(2)=='G') {
				model.addAttribute("messageMoto2NotFound",
						"API has not found any result to code " + code + " for moto2");
				model.addAttribute("messageMotogpNotFound",
						"API has not found any result to code " + code + " for motogp");
			}
		}else if(pth.charAt(0)=='3') {
			model.addAttribute("messageMoto2NotFound",
					"API has not found any result to code " + code + " for moto2");
			model.addAttribute("messageMotogpNotFound",
					"API has not found any result to code " + code + " for motogp");
			model.addAttribute("messageMoto3NotFound",
					"API has not found any result to code " + code + " for moto3");
		}
		return model;
	}
}
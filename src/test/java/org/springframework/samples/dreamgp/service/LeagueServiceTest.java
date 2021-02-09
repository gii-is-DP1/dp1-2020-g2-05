package org.springframework.samples.dreamgp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.service.LeagueService;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.service.exceptions.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LeagueServiceTest {
	 @Autowired
		protected LeagueService leagueService;
	 
	 @Autowired
	 	protected UserService userService;
	 
	 @Test
		void shouldFindLeaguesByCode() {
			Optional<League> league = this.leagueService.findLeagueByLeagueCode("QWEASDFRGT");
			assertThat(league.isPresent()).isTrue();
			
			
		}
	 
	 @Test
		void NegativeTestshouldFindLeaguesByCode() {
			Optional<League> league_fail = this.leagueService.findLeagueByLeagueCode("NEGATIVE_TEST");
			assertThat(league_fail.isPresent()).isFalse();
		}


	 @Test
	   void shouldFindLeaguesByUsernames() {
		 	Integer num_leagues = this.leagueService.findLeaguesByUsername("antcammar4");
			assertThat(num_leagues).isNotEqualTo(0);
			
		
	 }
	 @Test
	   void shouldNotFindAnyLeaguesByNoExistingUsername() {
			Integer num_leagues_zero = this.leagueService.findLeaguesByUsername("ZERO_TEST");
			assertThat(num_leagues_zero).isEqualTo(0);
	 }
	
	 
	 
	 @Test
	 void shouldFindLeaguesById() {
		 Optional<League> league = this.leagueService.findLeague(1);
		assertThat(league.isPresent()).isTrue();
		
	 }

	 @Test
	 void shouldNotFindLeaguesById() {
			Optional<League> league_fail = this.leagueService.findLeague(50);
			assertThat(league_fail.isPresent()).isFalse();
		
	 }
	  
	 @Test
	 @Transactional
	 void shouldInsertLeague()  throws DataAccessException, duplicatedLeagueNameException {
		Iterable<League> league = this.leagueService.findAll();
		List<League> found = new ArrayList<League>();
	    league.forEach(found::add);
		Integer found1=found.size();
		
		League newLeague = new League();
		newLeague.setLeagueCode("UDTQCSSOND");
		newLeague.setLeagueDate("22/12/2222");
		newLeague.setName("liga2222");
		
		this.leagueService.saveLeague(newLeague);
		
		assertThat(newLeague.getId().longValue()).isNotEqualTo(0);

		league = this.leagueService.findAll();
		found=new ArrayList<League>();
	    league.forEach(found::add);

		assertThat(found.size()).isEqualTo(found1 + 1);
	 }
	 
	  
//		 @Test
//		 @Transactional
//		 @Modifying
//		 void shouldUpdateLeague()  throws DataAccessException, duplicatedLeagueNameException {
//		
//			
//			League newLeague = new League();
//			newLeague.setLeagueCode("UDTQCSSOND");
//			newLeague.setLeagueDate("22/12/2222");
//			newLeague.setName("liga2222");
//			
//			this.leagueService.saveLeague(newLeague);
//			
//			this.leagueService.updateLeagueName(newLeague.getId(), "changuedName");
//			League league2 = this.leagueService.findLeague(newLeague.getId()).get();
//			assertThat(league2.getName()).isEqualTo("changuedName");
//
//		 }
//	 
	 @Test
	 @Transactional
	 void shouldNotInsertLeague()  throws DataAccessException, duplicatedLeagueNameException  {
	
		League newLeague = new League();
		newLeague.setLeagueCode("UDTQCSSOND");
		newLeague.setLeagueDate("22/12/2222");
		newLeague.setName("Liga1");
		try {
			this.leagueService.saveLeague(newLeague);
		}catch(duplicatedLeagueNameException e){
			assertThat(e.getMessage()).isEqualTo("Duplicated league name");
		}
	 }
	 
	 @Test
	 @Transactional
	 void shouldDeleteLeague() throws DataAccessException, duplicatedLeagueNameException {
		Iterable<League> league = this.leagueService.findAll();
		List<League> found = new ArrayList<League>();
	    league.forEach(found::add);
		Integer found1=found.size();
		
		League newLeague = new League();
		newLeague.setLeagueCode("UDTQCSSOND");
		newLeague.setLeagueDate("22/12/2222");
		newLeague.setName("liga2222");
		
		this.leagueService.saveLeague(newLeague);		
		this.leagueService.deleteLeague(newLeague);
		league = this.leagueService.findAll();
		found=new ArrayList<League>();
	    league.forEach(found::add);
	    
		assertThat(found.size()).isEqualTo(found1);
	 }
	 
//	 @Test
//		void shouldBeALeagueWithNoTeams() {
//		 	Set<Team> equipos = new HashSet<Team>();
//		 	List<League> leagues = new ArrayList<League>();
//		 	Set<Team> conj = new HashSet<Team>();
//		 	League newLeague = new League();
//			newLeague.setLeagueCode("UDTQCSSOND");
//			newLeague.setLeagueDate("22/12/2222");
//			newLeague.setName("liga2222");
//			newLeague.setTeam(equipos);
//			Team team = new Team();
//			team.setName("Sistema");
//			team.setLeague(newLeague);
//			conj.add(team);
//			newLeague.setTeam(conj);
//			leagues.add(newLeague);
//			
//			Boolean noTeams = this.leagueService.comprobarSiHayLigasVacias(leagues);
//			assertThat(noTeams).isTrue();
//		}

	 @Test
		void shouldBeALeagueWithTeams() {
		 	Set<Team> equipos = new HashSet<Team>();
		 	List<League> leagues = new ArrayList<League>();
		 	League newLeague = new League();
			newLeague.setLeagueCode("UDTQCSSOND");
			newLeague.setLeagueDate("22/12/2222");
			newLeague.setName("liga2222");
			 Team team_new = new Team();
			 team_new.setMoney(200);
			 team_new.setName("TEST");
			 team_new.setPoints(222);
			 Team team_new2= new Team();
			 team_new2.setMoney(200);
			 team_new2.setName("Sistema");
			 team_new2.setPoints(222);
			 equipos.add(team_new2);
			 equipos.add(team_new);
			newLeague.setTeam(equipos);
			leagues.add(newLeague);
			Set<Team> eq = newLeague.getTeam();
			 Boolean noTeams = this.leagueService.comprobarSiHayLigasVacias(leagues);
			assertThat(noTeams).isFalse();
		}
	 
	 @Test
		void shouldBeALeagueWithTeamNameSistema() {
		 	Set<Team> equipos = new HashSet<Team>();
		 	List<League> leagues = new ArrayList<League>();
		 	League newLeague = new League();
			newLeague.setLeagueCode("UDTQCSSOND");
			newLeague.setLeagueDate("22/12/2222");
			newLeague.setName("liga2222");
			 Team team_new = new Team();
			 team_new.setMoney(200);
			 team_new.setName("Sistema");
			 team_new.setPoints(222);
			 equipos.add(team_new);
			newLeague.setTeam(equipos);
			leagues.add(newLeague);
			Set<Team> eq = newLeague.getTeam();
			 Boolean noTeams = this.leagueService.comprobarSiHayLigasVacias(leagues);
			assertThat(noTeams).isTrue();
		}
	 
	 @Test
		void shouldBeALeagueWithNoTeams() {
		 	Set<Team> equipos = new HashSet<Team>();
		 	List<League> leagues = new ArrayList<League>();
		 	League newLeague = new League();
			newLeague.setLeagueCode("UDTQCSSOND");
			newLeague.setLeagueDate("22/12/2222");
			newLeague.setName("liga2222");
			newLeague.setTeam(equipos);
			leagues.add(newLeague);
			Set<Team> eq = newLeague.getTeam();
			 Boolean noTeams = this.leagueService.comprobarSiHayLigasVacias(leagues);
			assertThat(noTeams).isTrue();
		}
	 
	 
	 
	 @Test
		void shouldFindAuthorities() {
		 	String aut = this.leagueService.findAuthoritiesByUsername("antcammar4");
			assertThat(aut).isEqualTo("admin");
		}
	 @Test
		void shouldNotFindAuthorities() {
		 	String aut = this.leagueService.findAuthoritiesByUsername("notFoundUserqwertyuioawsdeftg");
			assertThat(aut).isEqualTo(null);
		}
	 @Test
		void shouldDecodeMoto2NotFound() {
		 String toDecode = "12";
		 String code="TEST";
		 ModelMap model = new ModelMap();
			model= this.leagueService.descifraUri(toDecode, code, model);
			assertThat(model.getAttribute("messageMoto2NotFound")).isEqualTo("API has not found any result to code " + code + " for moto2");
		}
	@Test
	void shouldDecodeMotoGPNotFound() {
	 String toDecode = "1G";
	 String code="TEST";
	 ModelMap model = new ModelMap();
		model= this.leagueService.descifraUri(toDecode, code, model);
		assertThat(model.getAttribute("messageMotogpNotFound")).isEqualTo("API has not found any result to code " + code + " for motogp");
	}
	 
	@Test
	void shouldDecodeMotoGPAndMoto3NotFound() {
	 String toDecode = "23G";
	 String code="TEST";
	 ModelMap model = new ModelMap();
		model= this.leagueService.descifraUri(toDecode, code, model);
		assertThat(model.getAttribute("messageMoto3NotFound")).isEqualTo("API has not found any result to code " + code + " for moto3");
		assertThat(model.getAttribute("messageMotogpNotFound")).isEqualTo("API has not found any result to code " + code + " for motogp");
	}

	@Test
	void shouldDecodeMoto3AndMotoGPNotFound() {
	 String toDecode = "232";
	 String code="TEST";
	 ModelMap model = new ModelMap();
		model= this.leagueService.descifraUri(toDecode, code, model);
		assertThat(model.getAttribute("messageMoto3NotFound")).isEqualTo("API has not found any result to code " + code + " for moto3");
		assertThat(model.getAttribute("messageMoto2NotFound")).isEqualTo("API has not found any result to code " + code + " for moto2");
	}

	@Test
	void shouldDecodeMoto3AndMoto2NotFound() {
	 String toDecode = "232";
	 String code="TEST";
	 ModelMap model = new ModelMap();
		model= this.leagueService.descifraUri(toDecode, code, model);
		assertThat(model.getAttribute("messageMoto3NotFound")).isEqualTo("API has not found any result to code " + code + " for moto3");
		assertThat(model.getAttribute("messageMoto2NotFound")).isEqualTo("API has not found any result to code " + code + " for moto2");
	}

	@Test
	void shouldDecodeMoto2AndMotoGPNotFound() {
	 String toDecode = "22GP";
	 String code="TEST";
	 ModelMap model = new ModelMap();
		model= this.leagueService.descifraUri(toDecode, code, model);
		assertThat(model.getAttribute("messageMoto2NotFound")).isEqualTo("API has not found any result to code " + code + " for moto2");
		assertThat(model.getAttribute("messageMotogpNotFound")).isEqualTo("API has not found any result to code " + code + " for motogp");
	}

	@Test
	void shouldDecodeMoto2AndMotoGPAndMoto3NotFound() {
	 String toDecode = "332GP";
	 String code="TEST";
	 ModelMap model = new ModelMap();
		model= this.leagueService.descifraUri(toDecode, code, model);
		assertThat(model.getAttribute("messageMoto3NotFound")).isEqualTo("API has not found any result to code " + code + " for moto3");
		assertThat(model.getAttribute("messageMoto2NotFound")).isEqualTo("API has not found any result to code " + code + " for moto2");
		assertThat(model.getAttribute("messageMotogpNotFound")).isEqualTo("API has not found any result to code " + code + " for motogp");
	}

}

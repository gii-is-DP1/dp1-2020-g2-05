package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.web.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 
	 
	 @Test
	 @Transactional
	 void shouldNotInsertLeague()  throws DataAccessException, duplicatedLeagueNameException  {
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
			
			Boolean noTeams = this.leagueService.comprobarLigaVacia(leagues);
			assertThat(noTeams).isTrue();
		}

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
			 equipos.add(team_new);
			newLeague.setTeam(equipos);
			leagues.add(newLeague);
			Set<Team> eq = newLeague.getTeam();
			 Boolean noTeams = this.leagueService.comprobarLigaVacia(leagues);
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
			 Boolean noTeams = this.leagueService.comprobarLigaVacia(leagues);
			assertThat(noTeams).isTrue();
		}
	
	 
}

package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
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
			
			
			Optional<League> league_fail = this.leagueService.findLeagueByLeagueCode("NEGATIVE_TEST");
			assertThat(league_fail.isPresent()).isFalse();
		}
	 
	 @Test
	   void shouldFindLeaguesByUsernames() {
		 	Integer num_leagues = this.leagueService.findLeaguesByUsername("antcammar4");
			assertThat(num_leagues).isNotEqualTo(0);
			
			
			Integer num_leagues_zero = this.leagueService.findLeaguesByUsername("ZERO_TEST");
			assertThat(num_leagues_zero).isEqualTo(0);
	 }
	 
	 @Test
	 void shouldFindLeaguesById() {
		 Optional<League> league = this.leagueService.findLeague(1);
		assertThat(league.isPresent()).isTrue();
		
		Optional<League> league_fail = this.leagueService.findLeague(12311);
		assertThat(league_fail.isPresent()).isFalse();
		
	 }
	 
	 @Test
	 void shouldCountTeamsByLeagueId() {
		Integer positive_test= this.leagueService.findTeamsByLeagueId(1);
		assertThat(positive_test).isNotEqualTo(0);
		
		Integer negative_test= this.leagueService.findTeamsByLeagueId(2323);
		assertThat(negative_test).isEqualTo(0);
		
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
		newLeague.setMoto2Active(false);
		newLeague.setMotogpActive(false);
		newLeague.setMoto3Active(false);
		newLeague.setName("liga2222");
		newLeague.setRacesCompleted(2);
		
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
		newLeague.setMoto2Active(false);
		newLeague.setMotogpActive(false);
		newLeague.setMoto3Active(false);
		newLeague.setName("liga2222");
		newLeague.setRacesCompleted(2);
		
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
		newLeague.setMoto2Active(false);
		newLeague.setMotogpActive(false);
		newLeague.setMoto3Active(false);
		newLeague.setName("liga2222");
		newLeague.setRacesCompleted(2);
		
		this.leagueService.saveLeague(newLeague);		
		this.leagueService.deleteLeague(newLeague);
		league = this.leagueService.findAll();
		found=new ArrayList<League>();
	    league.forEach(found::add);
	    
		assertThat(found.size()).isEqualTo(found1);
	 }
	 
	 
	 
	 
	 @Test
	 @Transactional
	 @Modifying
	 void shouldActiveLeagueMoto2() {
		this.leagueService.activeMoto2(1);
		League league = this.leagueService.findLeague(1).get();

	    assertThat(league.isMoto2Active()).isTrue();
	 }
	 
	 @Test
	 @Transactional
	 @Modifying
	 void shouldActiveLeagueMoto3() {
		 this.leagueService.activeMoto3(1);
		League league = this.leagueService.findLeague(1).get();

	    assertThat(league.isMoto3Active()).isTrue();
	 }
	 
	 @Test
	 @Transactional
	 @Modifying
	 void shouldActiveLeagueMotogp() {
		this.leagueService.activeMotogp(1);
		League league = this.leagueService.findLeague(1).get();
	    assertThat(league.isMotogpActive()).isTrue();
	 }
	 
	 @Test
		void shouldFindTeamsById() {
			Optional<Team> team = this.leagueService.findTeamById(1);
			assertThat(team.isPresent()).isTrue();
			
			
			Optional<Team> team_fail = this.leagueService.findTeamById(300);
			assertThat(team_fail.isPresent()).isFalse();
		}
	 
	 @Test
		void shouldFindTeamsByUsername() {
			Collection<Integer> teams = this.leagueService.findTeamsByUsername("migniearj");
			assertThat(teams.size()> 0).isTrue();
			

			
			Collection<Integer> teams_fail = this.leagueService.findTeamsByUsername("negative_test");
			assertThat(teams_fail.size()).isEqualTo(0);
		}
	 
	 
	 @Test
	 @Transactional
	 void shouldInsertTeam() {
		 
		 Iterable<Team> teams = this.leagueService.findAllTeams();
		 List<Team> team = new ArrayList<Team>();
		 teams.forEach(team::add);
		 Integer equipo1 = team.size();
		 
		 Team team_new = new Team();
		 team_new.setMoney("200");
		 team_new.setName("TEST");
		 team_new.setPoints("222");
		 team_new.setLeague(this.leagueService.findLeague(1).get());
		 team_new.setUser(this.userService.findUser("migniearj").get());
		 System.out.println(team_new);
		 this.leagueService.saveTeam(team_new);
		 assertThat(team_new.getId().longValue()).isNotEqualTo(0);
		 
		teams = this.leagueService.findAllTeams();
		team = new ArrayList<Team>();
		 	 teams.forEach(team::add);
		 
		 		assertThat(team.size()).isEqualTo(equipo1 + 1);
		 
	 
	 }
	 
	 @Test
	 @Transactional
	 void shouldDeleteTeam() {
		 
		 Iterable<Team> teams = this.leagueService.findAllTeams();
		 List<Team> team = new ArrayList<Team>();
		 teams.forEach(team::add);
		 Integer equipo1 = team.size();
		 
		 Team team_new = new Team();
		 team_new.setMoney("200");
		 team_new.setName("TEST");
		 team_new.setPoints("222");
		 team_new.setLeague(this.leagueService.findLeague(1).get());
		 team_new.setUser(this.userService.findUser("migniearj").get());
		 
		 this.leagueService.saveTeam(team_new);
		 assertThat(team_new.getId().longValue()).isNotEqualTo(0);
		 
		 Iterable<Team> teams1 = this.leagueService.findAllTeams();
		 List<Team> team1 = new ArrayList<Team>();
		 teams1.forEach(team1::add);
		 
		 
		 this.leagueService.delete(team_new);
		 assertThat(team_new.getId().longValue()).isNotEqualTo(0);
		 
		 teams = this.leagueService.findAllTeams();
			team = new ArrayList<Team>();
			 	 teams.forEach(team::add);
			 	 
		
			 
			 		assertThat(team.size()).isEqualTo(equipo1);

	 }
	 
	 @Test
	 @Transactional
	 void shouldFindTeamByLeagueId() {
		 List<Team> team = this.leagueService.findTeamByLeagueId(1);
		 Integer equipo1 = team.size();
			 
			 		assertThat(equipo1).isNotEqualTo(0);

	 }
	 
	 @Test
	 @Transactional
	 void shouldFindTeamByUsername() {
		 List<Team> team = this.leagueService.findTeamByUsername("migniearj");
		 Integer equipo1 = team.size();
			 
			 		assertThat(equipo1).isNotEqualTo(0);

	 }
	 
	 @Test
	 @Transactional
	 void shouldFindTeamByUsernameAndLeagueId() {
		 List<Team> team = this.leagueService.findTeamByUsernameAndLeagueId("migniearj", 2);
		 Integer equipo1 = team.size();
			 
			 		assertThat(equipo1).isNotEqualTo(0);

	 }
	 
	 
}

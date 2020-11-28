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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.League;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LeagueServiceTest {
	 @Autowired
		protected LeagueService leagueService;
	 
	 @Test
		void shouldFindLeaguesByCode() {
			Optional<League> league = this.leagueService.findLeagueByLeagueCode("QWEASDFRGT");
			assertThat(league.isPresent());
			
			
			Optional<League> league_fail = this.leagueService.findLeagueByLeagueCode("NEGATIVE_TEST");
			assertThat(league_fail.isPresent()).isFalse();
		}
	 
	 @Test
	   void shouldFindLeaguesByUsernames() {
		 	Integer num_leagues = this.leagueService.findLeaguesByUsername("antcammar4");
			assertThat(num_leagues!=0);
			
			
			Integer num_leagues_zero = this.leagueService.findLeaguesByUsername("ZERO_TEST");
			assertThat(num_leagues_zero==0).isTrue();
	 }
	 
	 @Test
	 void shouldFindLeaguesById() {
		 Optional<League> league = this.leagueService.findLeague(1);
		assertThat(league.isPresent());
		
		Optional<League> league_fail = this.leagueService.findLeague(12311);
		assertThat(league_fail.isPresent()).isFalse();
		
	 }
	 
	 @Test
	 @Transactional
	 void shouldInsertLeague() {
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
	 void shouldDeleteLeague() {
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
	 
	 
	 
}

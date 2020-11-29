package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LineupServiceTests {

	@Autowired
	protected LineupService lineupService;
	@Autowired
	protected RecruitService recruitService;
	@Autowired
	protected LeagueService leagueService;

//	@BeforeAll
//	League initLeague() {
//		League newLeague = new League();
//		newLeague.setName("initLeague");
//		newLeague.setLeagueCode("TEST_CODE");
//		newLeague.setLeagueDate("22/12/2222");
//		newLeague.setMotogpActive(true);
//		newLeague.setMoto2Active(false);
//		newLeague.setMoto3Active(false);
//		newLeague.setRacesCompleted(16);
//		return newLeague;
//	}
//	
//	@BeforeAll
//	Recruit initRecruit1() {
//		Recruit newRecruit = new Recruit();
//		newRecruit.setPilot(pilot);
//		newRecruit.setTeam(team);
//		return newRecruit;
//	}

	@Test
	void shouldFindLineupsById() {
		Optional<Lineup> lineup = this.lineupService.findLineup(1);
		assertThat(lineup.isPresent()).isTrue();

		Optional<Lineup> lineup_fail = this.lineupService.findLineup(12311);
		assertThat(lineup_fail.isPresent()).isFalse();

	}
	
	@Test
	void shouldFindLineupsByTeamId() {
		List<Lineup> lineup = this.lineupService.findByTeam(1);
		assertThat(lineup.size()).isGreaterThan(0);

		List<Lineup> lineup_fail = this.lineupService.findByTeam(12311);
		assertThat(lineup_fail.isEmpty()).isTrue();
	}

	@Test
	void shouldInsertLineup() {
		Iterable<Lineup> allLineups = this.lineupService.findAll();
		List<Lineup> lineupsFound = new ArrayList<Lineup>();
		allLineups.forEach(lineupsFound::add);
		Integer foundSize = lineupsFound.size();

		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(1).get().getGp());
		newLineup.setLeague(this.leagueService.findLeague(1).get());
		newLineup.setRecruit1(this.recruitService.findRecruit(1).get());
		newLineup.setRecruit2(this.recruitService.findRecruit(2).get());
		newLineup.setTeam(this.leagueService.findTeamById(1).get());

		this.lineupService.saveLineup(newLineup);
		assertThat(newLineup.getId()).isNotEqualTo(0);

		allLineups = this.lineupService.findAll();
		lineupsFound = new ArrayList<Lineup>();
		allLineups.forEach(lineupsFound::add);

		assertThat(lineupsFound.size()).isEqualTo(foundSize + 1);
	}

	@Test
	void shouldDeleteLineup() {
		Iterable<Lineup> allLineups = this.lineupService.findAll();
		List<Lineup> lineupsFound = new ArrayList<Lineup>();
		allLineups.forEach(lineupsFound::add);
		Integer foundSize = lineupsFound.size();

		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(1).get().getGp());
		newLineup.setLeague(this.leagueService.findLeague(1).get());
		newLineup.setRecruit1(this.recruitService.findRecruit(1).get());
		newLineup.setRecruit2(this.recruitService.findRecruit(2).get());
		newLineup.setTeam(this.leagueService.findTeamById(1).get());

		this.lineupService.saveLineup(newLineup);
		this.lineupService.delete(newLineup);
		allLineups = this.lineupService.findAll();
		lineupsFound = new ArrayList<Lineup>();
		allLineups.forEach(lineupsFound::add);

		assertThat(lineupsFound.size()).isEqualTo(foundSize);
	}

}

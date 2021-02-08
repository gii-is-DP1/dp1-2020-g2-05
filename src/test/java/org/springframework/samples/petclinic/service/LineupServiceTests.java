package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedRiderOnLineupException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LineupServiceTests {

	@Autowired
	protected LineupService lineupService;
	@Autowired
	protected RecruitService recruitService;
	@Autowired
	protected LeagueService leagueService;
	@Autowired
	protected TeamService teamService;

	@Test
	void shouldFindLineupById() {
		Optional<Lineup> lineup = this.lineupService.findLineup(1);
		assertThat(lineup.isPresent()).isTrue();
	}
	
	@Test
	void shouldNotFindLineupById() {
		Optional<Lineup> lineupFail = this.lineupService.findLineup(9999);
		assertThat(lineupFail.isPresent()).isFalse();
	}
	
	@Test
	void shouldFindLineupsByTeamId() {
		List<Lineup> lineup = this.lineupService.findByTeam(1);
		assertThat(lineup.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldNotFindLineupsByTeamId() {
		List<Lineup> lineupFail = this.lineupService.findByTeam(9999);
		assertThat(lineupFail.isEmpty()).isTrue();
	}
	
	@Test
	void shouldFindLineupsByGpId() {
		int GP_ID = 1;
		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(GP_ID).get().getGp());
		newLineup.setRecruit1(this.recruitService.findRecruitById(1).get());
		newLineup.setRecruit2(this.recruitService.findRecruitById(2).get());
		newLineup.setTeam(this.teamService.findTeamById(1).get());
		
		List<Lineup> allLineups = this.lineupService.findAll();
		List<Lineup> lineupsByGpId = this.lineupService.findByGpId(GP_ID);
		assertThat(lineupsByGpId.size()).isGreaterThan(0);
		assertThat(allLineups.containsAll(lineupsByGpId));  // Metamorphic relation
	}
	
	@Test
	void shouldNotFindLineupsByGpId() {
		List<Lineup> lineupsByGpId = this.lineupService.findByGpId(9999);
		assertThat(lineupsByGpId.isEmpty()).isTrue();
	}
	
	@Test
	void shouldFindLineupsByRecruitId() {
		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(1).get().getGp());
		newLineup.setRecruit1(this.recruitService.findRecruitById(2).get());
		newLineup.setRecruit2(this.recruitService.findRecruitById(4).get());
		newLineup.setTeam(this.teamService.findTeamById(1).get());
		
		List<Lineup> allLineups = this.lineupService.findAll();
		List<Lineup> lineupsByRecruitId = this.lineupService.findByRecruit(2);
		assertThat(lineupsByRecruitId.size()).isGreaterThan(0);
		assertThat(allLineups.containsAll(lineupsByRecruitId));  // Metamorphic relation
	}
	
	@Test
	void shouldNotFindLineupsByRecruitId() {
		List<Lineup> lineupsByRecruitId = this.lineupService.findByRecruit(9999);
		assertThat(lineupsByRecruitId.isEmpty()).isTrue();
	}
	
	@Test
	void shouldInsertLineup() throws DataAccessException, DuplicatedRiderOnLineupException {
		List<Lineup> allLineups = this.lineupService.findAll();

		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(1).get().getGp());
		newLineup.setRecruit1(this.recruitService.findRecruitById(1).get());
		newLineup.setRecruit2(this.recruitService.findRecruitById(2).get());
		newLineup.setTeam(this.teamService.findTeamById(1).get());

		this.lineupService.saveLineup(newLineup);
		assertThat(newLineup.getId()).isNotEqualTo(0);

		List<Lineup> allLineupsAfterInsert = this.lineupService.findAll();
		assertThat(allLineupsAfterInsert.size()).isEqualTo(allLineups.size() + 1);
		assertThat(allLineupsAfterInsert.containsAll(allLineups));  // Metamorphic relation
	}
	
	@Test
	void shouldDeleteLineup() throws DataAccessException, DuplicatedRiderOnLineupException {
		List<Lineup> allLineups = this.lineupService.findAll();

		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(1).get().getGp());
		newLineup.setRecruit1(this.recruitService.findRecruitById(1).get());
		newLineup.setRecruit2(this.recruitService.findRecruitById(2).get());
		newLineup.setTeam(this.teamService.findTeamById(1).get());

		this.lineupService.saveLineup(newLineup);
		this.lineupService.delete(newLineup);
		List<Lineup> allLineupsAfterInsertAndDelete = this.lineupService.findAll();

		assertThat(allLineupsAfterInsertAndDelete.size()).isEqualTo(allLineups.size()); // Metamorphic relation
	}
	
	@Test
	void metamorphicTestingLineupRiders() {
		List<Lineup> allLineups = this.lineupService.findAll();
		List<Recruit> allRecruits = this.recruitService.findAll();

		for (Lineup l : allLineups) {
			List<Recruit> recruitsInLineup = Stream.of(l.getRecruit1(), l.getRecruit2()).collect(Collectors.toList());
			assertTrue("Not a subset", allRecruits.containsAll(recruitsInLineup));  // Metamorphic relation
		}
	}

}

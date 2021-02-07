package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Lineup;
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
		Optional<Lineup> lineup_fail = this.lineupService.findLineup(12311);
		assertThat(lineup_fail.isPresent()).isFalse();
	}
	
	@Test
	void shouldFindLineupsByTeamId() {
		List<Lineup> lineup = this.lineupService.findByTeam(1);
		assertThat(lineup.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldNotFindLineupsByTeamId() {
		List<Lineup> lineup_fail = this.lineupService.findByTeam(12311);
		assertThat(lineup_fail.isEmpty()).isTrue();
	}
	
	@Test
	void shouldFindLineupsByGpId() {
		int GP_ID = 1;
		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(GP_ID).get().getGp());
		newLineup.setRecruit1(this.recruitService.findRecruit(1).get());
		newLineup.setRecruit2(this.recruitService.findRecruit(2).get());
		newLineup.setTeam(this.teamService.findTeamById(1).get());
		
		List<Lineup> lineupsByGpId = this.lineupService.findByGpId(GP_ID);
		assertThat(lineupsByGpId.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldNotFindLineupsByGpId() {
		List<Lineup> lineupsByGpId = this.lineupService.findByGpId(9999);
		assertThat(lineupsByGpId.isEmpty()).isTrue();
	}
	
	@Test
	void shouldFindLineupsByRecruitId() {
		int RECRUIT_ID = 1;
		Lineup newLineup = new Lineup();
		newLineup.setCategory(Category.MOTOGP);
		newLineup.setGp(this.lineupService.findLineup(1).get().getGp());
		newLineup.setRecruit1(this.recruitService.findRecruit(RECRUIT_ID).get());
		newLineup.setRecruit2(this.recruitService.findRecruit(RECRUIT_ID+1).get());
		newLineup.setTeam(this.teamService.findTeamById(1).get());
		
		List<Lineup> lineupsByGpId = this.lineupService.findByRecruit(RECRUIT_ID);
		assertThat(lineupsByGpId.size()).isGreaterThan(0);
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

		assertThat(allLineupsAfterInsertAndDelete.size()).isEqualTo(allLineups.size());
	}

}

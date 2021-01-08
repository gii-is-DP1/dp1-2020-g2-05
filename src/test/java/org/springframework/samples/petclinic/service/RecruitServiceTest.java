package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class RecruitServiceTest {
	@Autowired
	private PilotService pilotService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private RecruitService recruitService;

	@Test
	void shouldFindRecruitByCorrectPilotId() {
		Recruit recruit = recruitService.getRecruitByPilotId(1, 2).get();

		assertThat(recruit.getTeam().getId().equals(1));
		assertThat(recruit.getPilot().getDorsal().equals("93"));
	}

	@Test
	void shouldFindRecruitsByTeam() {
		List<Recruit> recruitsTeam1 = recruitService.getRecruitsByTeam(1);
		List<Recruit> recruitsTeam2 = recruitService.getRecruitsByTeam(2);

		assertThat(recruitsTeam1.size() == 2);
		assertThat(recruitsTeam2.size() == 0);
		assertThat(recruitsTeam1.get(0).getPilot().getDorsal().equals("93"));
	}

	@Test
	@Transactional
	void shouldSaveRecruit() {
		Long beforeList = StreamSupport.stream(recruitService.findAll().spliterator(), false).count();
		Pilot piloto = pilotService.findPilotById(3).get();
		Team equipo = teamService.findTeamById(3).get();

		recruitService.saveRecruit(piloto, equipo);

		Long afterList = StreamSupport.stream(recruitService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList - 1));
	}

	@Test
	@Transactional
	void shouldDeleteRecruit() {
		Long beforeList = StreamSupport.stream(recruitService.findAll().spliterator(), false).count();
		Pilot piloto = pilotService.findPilotById(2).get();
		Team equipo = teamService.findTeamById(1).get();

		recruitService.deleteRecruit(piloto, equipo);

		Long afterList = StreamSupport.stream(recruitService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList + 1));

	}
}

package org.springframework.samples.dreamgp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.service.PilotService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class TransactionServiceTest {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private PilotService pilotService;

	@Test
	@Transactional
	void shouldSaveTransaction() {

		Long beforeList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		Pilot pilot = pilotService.findPilotById(1).get();
		Team team = teamService.findTeamById(11).get();
		String fullName = pilot.getName() + " " + pilot.getLastName();
		Integer amount = 2000;

		transactionService.saveTransaction(amount, "Recompensa por buenos resultados de: " + fullName, team);

		Long afterList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList - 1)).isTrue();
		assertThat(this.transactionService.findTransactionsByTeamId(team.getId()).size() == 1).isTrue();
	}
	
	@Test
	@Transactional
	void shouldInsertTradeByCorrectParameters() {
		Long beforeList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		Pilot pilot = pilotService.findPilotById(1).get();
		Team purchaserTeam = teamService.findTeamById(11).get();
		Team sellerTeam = teamService.findTeamById(1).get();
		Integer price = 2000;

		transactionService.trade(price, pilot, sellerTeam, purchaserTeam);

		Long afterList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList - 2)).isTrue();
	}

	@Test
	@Transactional
	void shouldInsertResultsByCorrectParameters() {
		Long beforeList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		Pilot pilot = pilotService.findPilotById(1).get();
		Team purchaserTeam = teamService.findTeamById(11).get();
		Team sellerTeam = teamService.findTeamById(1).get();
		Integer price = 2000;

		transactionService.trade(price, pilot, sellerTeam, purchaserTeam);

		Long afterList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList - 2)).isTrue();
	}
}
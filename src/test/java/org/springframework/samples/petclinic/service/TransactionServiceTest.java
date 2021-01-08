package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class TransactionServiceTest {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private OfferService offerService;

	@Test
	@Transactional
	void shouldSaveTransaction() {

		Long beforeList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		Offer offer = offerService.findOfferById(1).get();
		Pilot pilot = offer.getRecruit().getPilot();
		Team team = teamService.findTeamById(11).get();
		String fullName = pilot.getName() + " " + pilot.getLastName();
		Integer price = offer.getPrice();

		transactionService.saveTransaction(price, TransactionType.BUY, "Compra de: " + fullName, team, offer);

		Long afterList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList - 1)).isTrue();
		assertThat(transactionService.getTeamTransactions(team.getId()).size() == 1).isTrue();
	}

	@Test
	@Transactional
	void shouldInsertTradeByCorrectParameters() {
		Long beforeList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		Offer offer = offerService.findOfferById(1).get();
		Team purchaserTeam = teamService.findTeamById(11).get();
		Team sellerTeam = offer.getRecruit().getTeam();
		Pilot pilot = offer.getRecruit().getPilot();
		Integer price = offer.getPrice();

		transactionService.trade(price, pilot, sellerTeam, purchaserTeam, offer);

		Long afterList = StreamSupport.stream(transactionService.findAll().spliterator(), false).count();
		assertThat(beforeList.equals(afterList - 2)).isTrue();

	}
}

package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
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
	void shouldFindRecruitByCorrectPilotId() {
		Recruit recruit = recruitService.getRecruitByPilotId(1, 2).get();

		assertThat(recruit.getTeam().getId().equals(1));
		assertThat(recruit.getPilot().getPoints().equals("93"));
	}

	@Test
	void shouldFindRecruitsOnSaleByCorrectTeamId() {
		long l = this.recruitService.getRecruitsByTeam(1).stream().filter(x -> x.getForSale().equals(true))
				.count();

		assertThat(l == 1L).isTrue();
	}

	@Test
	void shouldFindRecruitsNotOnSaleByCorrectTeamId() {
		long l = this.recruitService.getRecruitsByTeam(11).stream().filter(x -> x.getForSale().equals(true))
				.count();

		assertThat(l == 0L).isTrue();
	}

	@Test
	@Transactional
	void shouldPutOnSaleRecruit() {
		Recruit recruit = recruitService.findRecruitById(5).get();

		try {
			this.recruitService.putOnSale(recruit);
		} catch (NotAllowedNumberOfRecruitsException e) {
			e.printStackTrace();
		}

		long l = this.recruitService.getRecruitsByTeam(11).stream().filter(x -> x.getForSale().equals(true))
				.count();

		assertThat(l == 1L).isTrue();
	}

	@Test
	@Transactional
	void shouldThrowsExceptionWhenPutOnSaleRecruit() {
		Recruit recruit = recruitService.findRecruitById(8).get();
		int sellerTeam = recruit.getTeam().getId();

		try {
			this.recruitService.putOnSale(recruit);
		} catch (NotAllowedNumberOfRecruitsException e) {
			e.printStackTrace();
		}

		assertThat(this.recruitService.getRecruitsByTeam(sellerTeam).size() == 2).isTrue();
		Assertions.assertThrows(NotAllowedNumberOfRecruitsException.class, () -> {
			recruitService.putOnSale(recruit);
		});
	}

	@Test
	@Transactional
	void shouldQuitOnSaleRecruit() {
		Recruit recruit = recruitService.findRecruitById(1).get();
		this.recruitService.quitOnSale(recruit);

		long l = this.recruitService.getRecruitsByTeam(1).stream().filter(x -> x.getForSale().equals(true))
				.count();

		assertThat(l == 0L).isTrue();
	}

	@Test
	@Transactional
	void shouldPurchaseRecruit() {
		Recruit recruit = recruitService.findRecruitById(1).get();
		Team sellerTeam = recruit.getTeam();
		Team purchaserTeam = teamService.findTeamById(11).get();

		boolean perteneceAVendedorAntes = recruit.getTeam().equals(sellerTeam);
		boolean perteneceACompradorAntes = recruit.getTeam().equals(purchaserTeam);

		try {
			recruitService.purchaseRecruit(recruit, purchaserTeam);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NotAllowedNumberOfRecruitsException e) {
			e.printStackTrace();
		}

		recruit = recruitService.findRecruitById(1).get();
		boolean perteneceAVendedorDespues = recruit.getTeam().equals(sellerTeam);
		boolean perteneceACompradorDespues = recruit.getTeam().equals(purchaserTeam);

		assertThat(perteneceAVendedorAntes).isTrue();
		assertThat(perteneceACompradorDespues).isTrue();
		assertThat(perteneceAVendedorDespues).isFalse();
		assertThat(perteneceACompradorAntes).isFalse();
	}

	@Test
	@Transactional
	void shouldThrowExceptionWhenPurchaseRecruit() {
		Recruit recruit = recruitService.findRecruitById(7).get();
		Team purchaserTeam = teamService.findTeamById(1).get();
		assertThat(this.recruitService.getRecruitsByTeam(purchaserTeam.getId()).size() == 4).isTrue();
		Assertions.assertThrows(NotAllowedNumberOfRecruitsException.class, () -> {
			recruitService.purchaseRecruit(recruit, purchaserTeam);
		});
	}

}

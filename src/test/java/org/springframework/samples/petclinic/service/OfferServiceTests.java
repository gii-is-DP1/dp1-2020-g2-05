package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.util.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class OfferServiceTests {

	@Autowired
	protected OfferService offerService;
	
	@Autowired
	protected LeagueService leagueService;
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldFindOfferByCorrectId() {
		Offer offer = offerService.findOfferById(1).get();
		assertThat(offer.getPrice()).isEqualTo(2000);
		assertThat(offer.getStatus()).isEqualTo(Status.Outstanding);
		
		Optional<Offer> offerFail = offerService.findOfferById(0);
		assertThat(offerFail).isNotPresent();
	}
	
	@Test
	void shouldFindOffersByLeague() {
		Collection<Offer> offers = offerService.findOffersByLeague(2);
		assertThat(offers.size()).isEqualTo(2);
		
		Collection<Offer> offersFail = offerService.findOffersByLeague(0);
		assertThat(offersFail).isEmpty();
	}
	
//	Necesita estar Logueado!!
//	@Test
//	void shouldFindTeamByUsernameLeague() {
//		Team team = offerService.findTeamByUsernameLeague(1).get();
//		assertThat(team.getName()).startsWith("Miguelito");
//	}
//	
//	@Test
//	@Transactional
//	void shouldSaveTeamMoney() {
//		Team team = leagueService.findTeamById(9).get();
//		Double initialMoney = Double.parseDouble(team.getMoney());
//		
//		offerService.saveTeamMoney(team, 200);
//		
//		Double actualMoney = Double.parseDouble(team.getMoney());
//		assertThat(actualMoney).isEqualTo(initialMoney + 200);
//	}
	
	@Test
	@Transactional
	void shouldSaveOffer() {
		Offer offer = offerService.findOfferById(1).get();
		offer.setPrice(1000);
		offer.setStatus(Status.Accepted);
		
		offerService.saveOffer(offer);
		
		Offer editOffer = offerService.findOfferById(1).get();
		assertThat(editOffer.getPrice()).isEqualTo(1000);
		assertThat(editOffer.getStatus()).isEqualTo(Status.Accepted);
	}
	
	@Test
	@Transactional
	void shouldNotValidateWhenPriceNegative() {
		Offer offer = offerService.findOfferById(1).get();
		offer.setPrice(-1000);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Offer> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
		assertThat(violation.getMessage()).isNotEmpty();
	}
	
	@Test
	@Transactional
	void shouldInsertNewOffer() {
		Integer initialOffersNum =  offerService.findAllOffers().size();
		Offer offer = new Offer();
		offer.setPrice(1400);
		offer.setStatus(Status.Outstanding);
		Team team = leagueService.findTeamById(9).get();//Escuderia sistema de la liga 2
		offer.setTeam(team);
		
		offerService.saveOffer(offer);
		
		Integer actualOffersNum =  offerService.findAllOffers().size();
		assertThat(actualOffersNum).isEqualTo(initialOffersNum + 1);
		assertThat(offer.getId()).isNotNull();
		assertThat(offer.getTeam().getName()).isEqualTo(team.getName());
	}
	
	@Test
	@Transactional
	void shouldDeleteOffer() {
		Integer initialOffersNum =  offerService.findAllOffers().size();
		Offer offer = offerService.findOfferById(1).get();
		
		offerService.deleteOffer(offer);
		
		Integer actualOffersNum =  offerService.findAllOffers().size();
		assertThat(actualOffersNum).isEqualTo(initialOffersNum - 1);
		Optional<Offer> offerFail = offerService.findOfferById(1);
		assertThat(offerFail).isNotPresent();
	}
}

package org.springframework.samples.petclinic.service;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.web.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.stereotype.Service;

import motogpAPI.RaceCode;
import motogpAPI.Session;
import motogpApiV2.results.Competitor;
import motogpApiV2.results.Result;
import motogpApiV2.testing.testing;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PilotServiceTest {
	@Autowired
	protected PilotService pilotService;

	@Autowired
	protected ResultService resultService;
	
	
	
	

	
	@Test
	void shouldSavePilotAndDelete() {
		Pilot pilot = new Pilot();
		pilot.setBaseValue(200);
		pilot.setCategory(Category.MOTOGP);
		pilot.setPoints("27");
		pilot.setName("Ale");
		pilot.setLastName("Ruiz");
		pilot.setNationality("Spain");

		Integer numPilots = (Integer) this.pilotService.pilotCount();
		this.pilotService.savePilot(pilot);
		Pilot pilotSaved = this.pilotService.findPilotById(numPilots + 1).get();
		Integer numPilotsUpdated =(Integer) this.pilotService.pilotCount();

		assertThat(pilotSaved.getName()).isEqualTo("Ale");
		assertThat(numPilotsUpdated).isEqualTo(numPilots + 1);

		assertThat(pilotSaved.getName()).isEqualTo("Ale");
		assertThat(numPilotsUpdated).isEqualTo(numPilots + 1);

		this.pilotService.delete(pilotSaved);

		numPilotsUpdated = this.pilotService.pilotCount();

		assertThat(numPilots).isEqualTo(numPilotsUpdated);
	}

	
	@Test
	void shouldFindPilotById() {
		Pilot pilotSaved = this.pilotService.findPilotById(1).get();
		assertThat(pilotSaved.getName()).isEqualTo("Sergio");
	
	}
	
	@Test
	void shouldNotFindPilotById() {
		Optional<Pilot> pilotSaved = this.pilotService.findPilotById(this.pilotService.pilotCount()+5);
		assertThat(pilotSaved.isPresent()).isFalse();
	
	}
	
	@Test
	void shouldFindPilotByName() {
		Optional<Pilot> pilotSaved = this.pilotService.findByName("Rojas", "Antonio");
		assertThat(pilotSaved.isPresent()).isTrue();
	
	}
	@Test
	void shouldNotFindPilotByName() {
		Optional<Pilot> pilotSaved = this.pilotService.findByName("Test", "Negative");
		assertThat(pilotSaved.isPresent()).isFalse();
	
	}
	
	@Test
	void shouldGetRecruits() {
		List<Pilot> pilotSaved = this.pilotService.getRecruits();
		assertThat(pilotSaved.get(1).getName()).isEqualTo("Antonio");
		assertThat(pilotSaved.get(0).getName()).isEqualTo("Sergio");
		assertThat(pilotSaved.get(1).getLastName()).isEqualTo("Rojas");
		assertThat(pilotSaved.get(0).getLastName()).isEqualTo("Rojas");
	
	}
	
	
	@Test
	void shouldGetRecruitsByTeamId() {
		List<Pilot> pilotSaved = this.pilotService.getRecruits(1);
		assertThat(pilotSaved.get(1).getName()).isEqualTo("Antonio");
		assertThat(pilotSaved.get(0).getName()).isEqualTo("Sergio");
		assertThat(pilotSaved.get(1).getLastName()).isEqualTo("Rojas");
		assertThat(pilotSaved.get(0).getLastName()).isEqualTo("Rojas");
	}
	
	@Test
	void shouldNotGetRecruitsByTeamId() {
		List<Pilot> pilotSaved = this.pilotService.getRecruits(1223);
		assertThat(pilotSaved.isEmpty()).isTrue();
	
	}

	@Test
	@Transactional
	void shouldPage() {
		Pageable pageable = PageRequest.of(2, 5, Sort.by(Order.asc("category"), Order.asc("name"), Order.desc("lastName")));
		Page<Pilot> pilotSaved = this.pilotService.findAllPage(pageable);
		assertThat(pilotSaved.getSize()).isEqualTo(5);
	}
	
	@Test
	@Transactional
	void shouldUpdatePilot() {
		Result result = new Result();
		result.setPoints(0);
		result.setPosition(0);
		Competitor competitorToTest = new Competitor();
		competitorToTest.setName("Rojas, Antonio");
		competitorToTest.setResult(result);
		this.pilotService.updatePilot(competitorToTest, 15);
		
		assertThat(this.pilotService.findByName("Rojas", "Antonio").get().getPoints()).isEqualTo("15");
	}
	

	@Test
	@Transactional
	void assignResultsToAPilot() {
		org.springframework.samples.petclinic.model.Result resultModel = new org.springframework.samples.petclinic.model.Result();
		resultModel.setPosition(1);
		Competitor competitorToTest = new Competitor();
		competitorToTest.setName("Rojas, Antonio");
	
		this.pilotService.assignResultToAPilot(competitorToTest, resultModel);
		assertThat(this.pilotService.findByName("Rojas", "Antonio").get().getResults().stream().collect(Collectors.toList()).get(0).getPosition()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void assingSetResultsToAPilot() {
	org.springframework.samples.petclinic.model.Result resultModel = new org.springframework.samples.petclinic.model.Result();
		resultModel.setPosition(1);
		Competitor competitorToTest = new Competitor();
		competitorToTest.setName("Rojas, Antonio");
	
		this.pilotService.updatePilotResults(competitorToTest, resultModel);
		assertThat(this.pilotService.findByName("Rojas", "Antonio").get().getResults().stream().collect(Collectors.toList()).get(0).getPosition()).isEqualTo(1);
	}
	
}

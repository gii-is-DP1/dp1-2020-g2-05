package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import motogpAPI.RaceCode;
import motogpAPI.Session;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class PilotServiceTest {
	@Autowired
	protected PilotService pilotService;

	@Autowired
	protected ResultService resultService;

//	@Test
//	void shouldNotPopulatePilotsAndResultsRaceByRace() throws JSONException, IOException, ParseException {
//		BDCarrera form = new BDCarrera();
//		form.setCategory(Category.MOTOGP);
//		form.setRacecode(RaceCode.QAT);
//		form.setSession(Session.RACE);
//		form.setYear(2045);
//		
//		Assertions.assertThrows(NotFoundException.class, () -> {
//			this.pilotService.poblarBDCarreraACarrera(form, new GranPremio(), true);		});
//
//		}

	
	@Test
	void shouldPopulatePilotsAndResultsYearly() {
		FormRellenarBD form = new FormRellenarBD();
		form.setCategory(Category.MOTOGP);
		form.setAnyoInicial(2018);
		form.setAnyoFinal(2019);
		Integer num_pilots = this.pilotService.pilotCount();
		Integer num_results = this.resultService.findAll().size();
		
		Assertions.assertThrows(Exception.class, () -> {
				this.pilotService.poblarBD(form);
		});
		Integer num_pilots_updated = this.pilotService.pilotCount();
		Integer num_results_updated = this.resultService.findAll().size();

		assertThat(num_pilots_updated).isGreaterThan(num_pilots);
		assertThat(num_results_updated).isGreaterThan(num_results);
	}

	@Test
	void shouldNotPopulatePilotsAndResults() throws JSONException, IOException, ParseException {
		FormRellenarBD form = new FormRellenarBD();
		form.setCategory(Category.MOTOGP);
		form.setAnyoInicial(2045);
		form.setAnyoFinal(2047);
		
		Assertions.assertThrows(NotFoundException.class, () -> {
				this.pilotService.poblarBD(form);
			});

	}

	@Test
	void shouldPopulatePilotsAndResultsRaceByRace() throws JSONException, IOException, ParseException {
		BDCarrera form = new BDCarrera();
		form.setCategory(Category.MOTOGP);
		form.setRacecode(RaceCode.QAT);
		form.setSession(Session.RACE);
		form.setYear(2019);
		Integer num_pilots = this.pilotService.pilotCount();
		Integer num_results = this.resultService.findAll().size();
		GranPremio granPremio = new GranPremio();
		this.pilotService.poblarBDCarreraACarrera(form, granPremio, false);
		Integer num_pilots_updated = this.pilotService.pilotCount();
		Integer num_results_updated = this.resultService.findAll().size();

		assertThat(num_pilots_updated).isGreaterThan(num_pilots);
		assertThat(num_results_updated).isGreaterThan(num_results);
		assertThat(granPremio.getHasBeenRun()).isTrue();
	}

	

	@Test
	void shouldSavePilotAndDelete() {
		Pilot pilot = new Pilot();
		pilot.setBaseValue(200);
		pilot.setCategory(Category.MOTOGP);
		pilot.setDorsal("27");
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
}

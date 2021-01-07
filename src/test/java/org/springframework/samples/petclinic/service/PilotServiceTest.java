package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.validation.Valid;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.web.ResultFormatter;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.samples.petclinic.model.Category;
import motogpAPI.PeticionesGet;
import motogpAPI.RaceCode;
import motogpAPI.Session;
import motogpAPI.model.InfoCarrera;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
	 
public class PilotServiceTest {
	@Autowired
	protected PilotService pilotService;
 
	@Autowired
	protected ResultService resultService;
	
	 @Test
		void shouldPopulatePilotsAndResultsYearly() {
		 FormRellenarBD form = new FormRellenarBD();
		 form.setCategory(Category.MOTOGP);
		 form.setAnyoInicial(2018);
		 form.setAnyoFinal(2019);
		 Integer num_pilots = this.pilotService.findAllList().size();
		 Integer num_results = this.resultService.findAll().size();
		 try {
			 this.pilotService.poblarBD(form);

		 }catch(Exception e) {
			 
		 }
		 Integer num_pilots_updated = this.pilotService.findAllList().size();
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
		 try {
			 this.pilotService.poblarBD(form);

		 }catch(NotFoundException  e ) {
			assertThat(e.getMessage()).isEqualTo("No se han encontrado carreras para los anyos dados");
		 }

	 }
	 
	 @Test
		void shouldPopulatePilotsAndResultsRaceByRace() throws JSONException, IOException, ParseException {
		 BDCarrera form = new BDCarrera();
		 form.setCategory(Category.MOTOGP);
		 form.setRacecode(RaceCode.QAT);
		 form.setSession(Session.RACE);
		 form.setYear(2019);
		 Integer num_pilots = this.pilotService.findAllList().size();
		 Integer num_results = this.resultService.findAll().size();
		 GranPremio granPremio = new GranPremio();
		 this.pilotService.poblarBDCarreraACarrera(form, granPremio, false);
		 Integer num_pilots_updated = this.pilotService.findAllList().size();
		 Integer num_results_updated = this.resultService.findAll().size();

			assertThat(num_pilots_updated).isGreaterThan(num_pilots);
			assertThat(num_results_updated).isGreaterThan(num_results);
			assertThat(granPremio.getHasBeenRun()).isTrue();
	 }

	 
	 @Test
		void shouldNotPopulatePilotsAndResultsRaceByRace() throws JSONException, IOException, ParseException {
		 FormRellenarBD form = new FormRellenarBD();
		 form.setCategory(Category.MOTOGP);
		 form.setAnyoInicial(2045);
		 form.setAnyoFinal(2047);
		 try {
			 this.pilotService.poblarBD(form);

		 }catch(NotFoundException  e ) {
			assertThat(e.getMessage()).isEqualTo("No se han encontrado carreras para los parametros dados");
		 }

	 }
}

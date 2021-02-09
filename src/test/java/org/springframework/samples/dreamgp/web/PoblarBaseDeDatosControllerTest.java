package org.springframework.samples.dreamgp.web;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.configuration.SecurityConfiguration;
import org.springframework.samples.dreamgp.model.BDCarrera;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.model.Record;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.model.TablaConsultas;
import org.springframework.samples.dreamgp.service.AuthoritiesService;
import org.springframework.samples.dreamgp.service.GranPremioService;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.samples.dreamgp.service.PoblarBaseDeDatosService;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.samples.dreamgp.service.exceptions.duplicatedLeagueNameException;
import org.springframework.samples.dreamgp.web.PoblarBaseDeDatosController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import motogpApiV2.RaceCode;
import motogpApiV2.Session;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.any;

@WebMvcTest(controllers = PoblarBaseDeDatosController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PoblarBaseDeDatosControllerTest {

	@MockBean
	TeamService teamService;

	@MockBean
	GranPremioService GPService;

	@MockBean
	TablaConsultasService TCService;

	@MockBean
	PoblarBaseDeDatosService poblarBaseDeDatosService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private LineupService lineupService;

	@Autowired
	private MockMvc mockMvc;

	GranPremio gp = new GranPremio();
	List<GranPremio> gps = new ArrayList<GranPremio>();
	Set<Lineup> lineups = new HashSet<>();
	Record record = new Record();
	Lineup lineup = new Lineup();
	Set<Result> results = new HashSet<>();
	Result result = new Result();
	TablaConsultas TCConsulta = new TablaConsultas();
	BDCarrera form = new BDCarrera();

	@BeforeEach
	void setup() throws DataAccessException, duplicatedLeagueNameException, ParseException {
		TCConsulta.setCurrentCategory(Category.MOTO3);
		TCConsulta.setRacesCompleted(0);

		form.setCategory(Category.MOTO3);
		form.setRacecode(RaceCode.valueOf("QAT"));
		form.setSession(Session.RACE);
		form.setYear(2021);
		
		gp.setCalendar(true);
		gp.setCircuit("LUCENA");
		gp.setDate0(LocalDate.of(2021, 06, 12));
		gp.setHasBeenRun(false);
		gp.setRaceCode("QAT");
		gp.setSite("Espana");
		gps.add(gp);
		given( this.TCService.getTabla()).willReturn(Optional.of(this.TCConsulta));

	}

	@WithMockUser(value = "spring")
	@Test
	void testPoblarSinProblemas() throws Exception {

		mockMvc.perform(get("/BD/carrerasBD/{date}/{code}", gp.getDate0().toString(), 				gp.getRaceCode().toString())
				.with(csrf()).param("date", gp.getDate0().toString()).param("code", 				gp.getRaceCode().toString()))

				.andExpect(status().is3xxRedirection())
				
				.andExpect(view().name("redirect:/controlPanel/0/QAT"));
	}

	
	
}

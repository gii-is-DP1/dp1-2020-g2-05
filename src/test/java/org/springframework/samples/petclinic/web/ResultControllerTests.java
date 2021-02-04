package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.samples.petclinic.model.RecordCircuito;
import org.springframework.samples.petclinic.model.RecordMejorVuelta;
import org.springframework.samples.petclinic.model.RecordPole;
import org.springframework.samples.petclinic.model.RecordVueltaRapida;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.ResultService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ResultController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
class ResultControllerTests {

	private static final Category CAT = Category.MOTOGP;
	private static final int TEST_GP_ID = 1;
	private static final String RACE_CODE = "JEZ";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TablaConsultasService tablaConsultas;
	
	@MockBean
	private ResultService resultService;
	@MockBean
	private LineupService lineupService;

	
	@MockBean
	private GranPremioService GPService;
	
	private Pilot pilot = new Pilot();
	private GranPremio gp = new GranPremio();
	private Record record = new Record();
	private RecordMejorVuelta mejorVuelta = new RecordMejorVuelta();
	private RecordCircuito recordDelCircuito = new RecordCircuito();
	private RecordPole vueltaDePole = new RecordPole();
	private RecordVueltaRapida vueltaRapida = new RecordVueltaRapida();
	private Result result = new Result();
	private List<Result> resultList = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		pilot.setCategory(CAT);
		pilot.setPoints("27");
		pilot.setName("Ale");
		pilot.setLastName("Ruiz");
		pilot.setNationality("Spain");
		pilot.setBaseValue(200);
		
		mejorVuelta.setAnyo(2019);
		mejorVuelta.setKmh(100.0);
		mejorVuelta.setNombrePiloto("Ale");
		mejorVuelta.setTiempo(20);
		
		recordDelCircuito.setAnyo(2017);
		recordDelCircuito.setKmh(1000.0);
		recordDelCircuito.setNombrePiloto("Marc");
		recordDelCircuito.setTiempo(300);
		
		vueltaDePole.setKmh(300.0);
		vueltaDePole.setNombrePiloto("Marc");
		vueltaDePole.setTiempo(27);
	
		vueltaRapida.setVuelta(4);
		vueltaRapida.setKmh(200.0);
		vueltaRapida.setNombrePiloto("Rossi");
		vueltaRapida.setTiempo(12);
		
		record.setMejorVuelta(mejorVuelta);
		record.setRecordDelCircuito(recordDelCircuito);
		record.setVueltaDePole(vueltaDePole);
		record.setVueltaRapida(vueltaRapida);
		
		gp.setId(TEST_GP_ID);
		gp.setCalendar(true);
		gp.setHasBeenRun(false);
		gp.setRaceCode(RACE_CODE);
		gp.setCircuit("Gran Circuito Jerez");
		gp.setSite("Jerez");
		gp.setRecordMotoGP(record);
		gp.setRecordMoto2(record);
		gp.setRecordMoto3(record);
		
		result.setGp(gp);
		result.setPilot(pilot);
		result.setPole(false);
		result.setLap(false);
		result.setPosition(2);
		resultList.add(result);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testGetResultsAndRecords() throws Exception {
		given(GPService.findGPById(TEST_GP_ID)).willReturn(Optional.of(gp));
		given(resultService.findResultsByCategoryAndId(TEST_GP_ID,RACE_CODE,CAT)).willReturn(resultList);
		
		mockMvc.perform(get("/granPremios/{gpId}/results/{category}/{raceCode}",TEST_GP_ID,CAT,RACE_CODE))
		.andExpect(status().isOk())
		.andExpect(view().name("results/resultsByCategory"))
		.andExpect(model().attributeExists("gp"))
		.andExpect(model().attribute("gp",is(gp)))
		.andExpect(model().attributeExists("resultados"))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Result> hasProperty("pilot", is(pilot)))))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Result> hasProperty("gp", is(gp)))))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Result> hasProperty("position", is(result.getPosition())))))
		.andExpect(model().attributeExists("record"))
		.andExpect(model().attribute("record",is(record)));
	}
}

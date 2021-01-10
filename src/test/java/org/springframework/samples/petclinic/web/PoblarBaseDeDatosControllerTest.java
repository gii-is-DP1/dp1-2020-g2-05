package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.dao.DataAccessException;

import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;

public class PoblarBaseDeDatosControllerTest {
	
//	private static final Integer TEST_ACTUAL_YEAR = 2021;
//
//
//	 @MockBean
//	 @Autowired
//	 private UserService userService;
//	 
//	 @MockBean
//	 @Autowired
//	 private LeagueService leagueService;
//	 
//	 @MockBean
//	 @Autowired
//	 private GranPremioService GPService;
//	 
//
//	 @Autowired
//	private MockMvc mockMvc;
//	 
//	 GranPremio gp = new GranPremio();
//	 List<GranPremio>  gps = new ArrayList<GranPremio>();
//	 Set<Lineup> lineups = new HashSet<>();
//	 Record record = new Record();
//	 Lineup lineup = new Lineup();
//	 Set<Result> results = new HashSet<>();
//	 Result result = new Result();
//
//		@BeforeEach
//		void setup() throws DataAccessException, duplicatedLeagueNameException, ParseException {
//			
//			gp.setCalendar(true);
//			gp.setCircuit("LUCENA");
//			gp.setDate0(LocalDate.of(2021, 06, 12));
//			gp.setHasBeenRun(false);
////			gp.setId(1234567);
//			gp.setRaceCode("QAT");
//			gp.setSite("España");
////			gp.setRecord(record);
////			lineups.add(lineup);
////			gp.setLineups(lineups);
////			results.add(result);
////			gp.setResults(results);
//			System.out.println("awidnawd");
//			System.out.println(gp);
////			this.GPService.saveGP(gp);
//			this.GPService.findAllActualYear(2021);
//			gps.add(gp);
//			System.out.println(gps);
//			
//			given(GPService.findAllActualYear(2021)).willReturn(gps);
//		}
//	  @WithMockUser(value = "spring")
//	  @Test
//		void testPoblar() throws Exception {
//		  
//		 
//		  given(this.GPService.findAllActualYear(TEST_ACTUAL_YEAR)).willReturn(gps);
//			mockMvc.perform(get("/controlPanel"))
//					.andExpect(status().isOk())
//					.andExpect(model().attributeExists("listaGP"))
//					.andExpect(model().attribute("listaGP", Matchers.hasItem(Matchers.<GranPremio> hasProperty("site", is(gp.getSite())))))
//					.andExpect(view().name("/panelControl/panelDeControl"));
//		}
}
		


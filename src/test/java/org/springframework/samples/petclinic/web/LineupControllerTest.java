package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;


@WebMvcTest(controllers=LineupController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class LineupControllerTest {
	
	private static final Integer TEST_LINEUP_ID = 1;
	private static final Integer TEST_TEAM_ID = 1;
	private static final Integer TEST_LEAGUE_ID = 1;
	
	@MockBean
	@Autowired
	TablaConsultasService TCService;
	
	@MockBean
	@Autowired
	LineupService lineupService;
	
	@MockBean
	@Autowired
	LeagueService leagueService;
	
	@MockBean
	@Autowired
	TeamService teamService;
	
	@MockBean
	@Autowired
	RecruitService recruitService;
	
	@MockBean
	@Autowired
	GranPremioService granPremioService;
	
	@MockBean
	@Autowired
	UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private User user = new User();
	
	private List<Lineup> lista = new ArrayList<Lineup>();
	private	Lineup lineup = new Lineup();
	TablaConsultas TCConsulta = new TablaConsultas();
	private GranPremio gp = new GranPremio();
	private List<Recruit> listaPilotos = new ArrayList<Recruit>();
	Recruit recruit1 = new Recruit();
	Recruit recruit2 = new Recruit();

	@BeforeEach 
	void setup() throws DataAccessException {
		
		TCConsulta.setCurrentCategory(Category.MOTO3);
		TCConsulta.setRacesCompleted(0);
		TCConsulta.setActualRace(1);
		user.setUsername("antcammar4");
		user.setEnabled(true);
	
		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		user.setPassword("test");
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);
		
		user.setAuthorities(auth);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		League liga = new League();
	    Date date = new Date();  
	    liga.setId(TEST_LEAGUE_ID);
	    liga.setLeagueCode("UDTQCSSOND");
	    liga.setName("prueba");
	    liga.setLeagueDate(formatter.format(date));
		
		Set<Team> teams = new HashSet<Team>();
		Team team = new Team();
		team.setId(TEST_TEAM_ID);
		team.setLeague(liga);
		team.setMoney(2);
		team.setName("teamtest");
		team.setPoints(132);
		team.setUser(user);
		teams.add(team);
		user.setTeam(teams);
		
		Pilot pilot1 = new Pilot();
		pilot1.setId(1);
		pilot1.setCategory(Category.MOTO3);
		pilot1.setDorsal("27");
		pilot1.setName("Ale");
		pilot1.setLastName("Ruiz");
		pilot1.setNationality("Spain");
		
		Pilot pilot2 = new Pilot();
		pilot2.setId(2);
		pilot2.setCategory(Category.MOTO3);
		pilot2.setDorsal("7");
		pilot2.setName("Juan");
		pilot2.setLastName("Perez");
		pilot2.setNationality("Spain");
		
		listaPilotos.add(recruit1);
		listaPilotos.add(recruit2);

		recruit1.setId(1);
		recruit1.setPilot(pilot1);
		recruit1.setTeam(team);
		
		recruit2.setId(2);
		recruit2.setPilot(pilot2);
		recruit2.setTeam(team);
		
		
		gp.setId(1);
		gp.setCalendar(true);
		gp.setCircuit("Losail");
		gp.setDate0(LocalDate.of(2020, 3, 8));
		gp.setHasBeenRun(false);
		gp.setRaceCode("QAT");
		gp.setSite("Grand Prix Of Qatar");
		
	    lineup.setId(TEST_LINEUP_ID);
	    lineup.setCategory(Category.MOTO3);
	    lineup.setGp(gp);
	    lineup.setRecruit1(recruit1);
	    lineup.setRecruit2(recruit2);
	    lineup.setTeam(team);
	    
		
		this.lineupService.saveLineup(lineup);
		this.teamService.saveTeam(team);
		this.userService.saveUser(user);
		given(this.userService.getUserSession()).willReturn(user);

		lista.add(lineup);
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowLineups() throws Exception {
		given(lineupService.findAll()).willReturn(lista);

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/lineups", TEST_LEAGUE_ID, TEST_TEAM_ID)).andExpect(status().isOk())	
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Lineup> hasProperty("category", is(lineup.getCategory())))))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Lineup> hasProperty("recruit1", is(lineup.getRecruit1())))))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Lineup> hasProperty("recruit2", is(lineup.getRecruit2())))))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Lineup> hasProperty("gp", is(lineup.getGp())))))
		.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Lineup> hasProperty("team", is(lineup.getTeam())))))
		.andExpect(view().name("lineups/lineupsList"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testShowExistentLineupById() throws Exception {
		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.of(lineup));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/lineups/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID))
		.andExpect(status().isOk())	
		.andExpect(model().attributeHasNoErrors("lineup"))
		.andExpect(model().attribute("lineup", is(lineup)))
		.andExpect(view().name("lineups/lineupDetails"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowNonExistentLineupById() throws Exception {
		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/lineups/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID))
		.andExpect(status().is3xxRedirection())	
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}
		
	@WithMockUser(value = "spring")
	@Test
	void testCreateLineupGet() throws Exception {
		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
		given(granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasNoErrors("lineup"))
		.andExpect(model().attribute("lineup", hasProperty("category", is(lineup.getCategory()))))
		.andExpect(model().attribute("lineup", hasProperty("gp", is(lineup.getGp()))))
		.andExpect(model().attribute("leagueCategory", is(lineup.getCategory())))
		.andExpect(view().name("thymeleaf/lineupsEdit"));
	}
	
	
//	Una vez se corre un GP, entonces, ya no se podrá crear, editar o eliminar un lineup respecto a dicho GP.
//  Esto lo implementaremos en el próximo sprint, cuando sepamos como hacer el tema de la seguridad
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testCantCreateLineupGet() throws Exception {
//		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
//		given(granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));
//
//		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID)).andExpect(status().isOk())
//		.andExpect(model().attribute("lineup", hasProperty("category", is(lineup.getCategory()))))
//		.andExpect(model().attribute("lineup", hasProperty("gp", is(lineup.getGp()))))
//		.andExpect(model().attribute("leagueCategory", is(lineup.getCategory())))
//		.andExpect(view().name("lineups/lineupsEdit"));
//	}
	

	@WithMockUser(value = "spring")
	@Test
	void testCrearAlineacionPost() throws Exception {

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID)
				.with(csrf())
				.param("id", lineup.getId().toString())
				.param("category", lineup.getCategory().toString())
				.param("league", TEST_LEAGUE_ID.toString())
				.param("team", lineup.getTeam().getId().toString())
				.param("gp.id", lineup.getGp().getId().toString())
//				.param("gp.calendar", lineup.getGp().getCalendar().toString())
//				.param("gp.circuit", lineup.getGp().getCircuit().toString())
//				.param("gp.date0", lineup.getGp().getDate0().toString())
//				.param("gp.hasBeenRun", lineup.getGp().getHasBeenRun().toString())
//				.param("gp.raceCode", lineup.getGp().getRaceCode().toString())
//				.param("gp.site", lineup.getGp().getSite().toString())
				.param("recruit1.id", lineup.getRecruit1().getId().toString()) 
//				.param("recruit1.team.id", lineup.getRecruit1().getTeam().getId().toString())
//				.param("recruit1.pilot.id", lineup.getRecruit1().getPilot().getId().toString())
//				.param("recruit1.pilot.category", lineup.getRecruit1().getPilot().getCategory().toString())
//				.param("recruit1.pilot.name", lineup.getRecruit1().getPilot().getName().toString())
//				.param("recruit1.pilot.lastName", lineup.getRecruit1().getPilot().getLastName().toString())
//				.param("recruit1.pilot.dorsal", lineup.getRecruit1().getPilot().getDorsal().toString())
//				.param("recruit1.pilot.nationality", lineup.getRecruit1().getPilot().getNationality().toString())
				.param("recruit2.id", lineup.getRecruit2().getId().toString()) 
//				.param("recruit2.team.id", lineup.getRecruit2().getTeam().getId().toString())
//				.param("recruit2.pilot.id", lineup.getRecruit2().getPilot().getId().toString())
//				.param("recruit2.pilot.category", lineup.getRecruit2().getPilot().getCategory().toString())
//				.param("recruit2.pilot.name", lineup.getRecruit2().getPilot().getName().toString())
//				.param("recruit2.pilot.lastName", lineup.getRecruit2().getPilot().getLastName().toString())
//				.param("recruit2.pilot.dorsal", lineup.getRecruit2().getPilot().getDorsal().toString())
//				.param("recruit2.pilot.nationality", lineup.getRecruit2().getPilot().getNationality().toString())
				.flashAttr("recruitsSelection", listaPilotos)) 
		.andExpect(status().is3xxRedirection())
//		.andExpect(view().name("redirect:/leagues/" + TEST_LEAGUE_ID + "/teams/" + lineup.getTeam().getId() + "/details"));
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearAlineacionPostHasErrors() throws Exception {
		
		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
		given(granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID)
				.with(csrf())
				
				// Solo falla con los param de recruit1 y 2, con los demas, da igual si son null o no :(
				// Cuando hay typeMismatch, si da error, pero cuando algo es null, no.
				
				.param("id", "uno")//lineup.getId().toString())   // Error
				.param("category", "test_error_category")//lineup.getCategory().toString())   // Error
//				.param("team", lineup.getTeam().getId().toString())    // No da error
//				.param("gp.id", lineup.getGp().getId().toString())    // No da error
				.param("recruit1.id", "test_error_string"))   // Error
//				.param("recruit2.id", lineup.getRecruit2().getId().toString()))   // Error
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("lineup"))
				.andExpect(model().attributeErrorCount("lineup", 4))
				.andExpect(view().name("thymeleaf/lineupsEdit"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testEditLineupGet() throws Exception {
		given(this.lineupService.findLineup(lineup.getId())).willReturn(Optional.of(lineup));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				.header("Referer", "/leagues/1/teams/1/details")
				.param("id", lineup.getId().toString())
				.param("category", lineup.getCategory().toString())
				.param("team", lineup.getTeam().getId().toString())
				.param("gp.id", lineup.getGp().getId().toString())
				.param("gp.calendar", lineup.getGp().getCalendar().toString())
				.param("gp.circuit", lineup.getGp().getCircuit().toString())
				.param("gp.date0", lineup.getGp().getDate0().toString())
				.param("gp.hasBeenRun", lineup.getGp().getHasBeenRun().toString())
				.param("gp.raceCode", lineup.getGp().getRaceCode().toString())
				.param("gp.site", lineup.getGp().getSite().toString())
				.param("recruit1.id", lineup.getRecruit1().getId().toString()) 
				.param("recruit2.id", lineup.getRecruit2().getId().toString())
				.flashAttr("recruitsSelection", listaPilotos))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lineup", is(lineup)))
//		.andExpect(model().attribute("lineup", hasProperty("id", is(lineup.getId()))))
//		.andExpect(model().attribute("lineup", hasProperty("category", is(lineup.getCategory()))))
//		.andExpect(model().attribute("lineup", hasProperty("team", is(lineup.getTeam()))))
//		.andExpect(model().attribute("lineup", hasProperty("gp", is(lineup.getGp()))))
		.andExpect(view().name("thymeleaf/lineupsEdit"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testEditLineupGetError() throws Exception {
		given(this.lineupService.findLineup(lineup.getId())).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				.header("Referer", "/leagues/1/teams/1/details"))
		.andExpect(status().is3xxRedirection())
//		.andExpect(model().attribute("message", is("Lineup not found!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditLineupPost() throws Exception {
		given(this.lineupService.findLineup(lineup.getId())).willReturn(Optional.of(lineup));
		given(this.granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));
	   
		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
//				.header("Referer", "/leagues/1/teams/1/details")
				.param("id", lineup.getId().toString())
				.param("category", lineup.getCategory().toString())
				.param("team", lineup.getTeam().getId().toString())
				.param("gp.id", lineup.getGp().getId().toString())
				.param("gp.calendar", lineup.getGp().getCalendar().toString())
				.param("gp.circuit", lineup.getGp().getCircuit().toString())
				.param("gp.date0", lineup.getGp().getDate0().toString())
				.param("gp.hasBeenRun", lineup.getGp().getHasBeenRun().toString())
				.param("gp.raceCode", lineup.getGp().getRaceCode().toString())
				.param("gp.site", lineup.getGp().getSite().toString())
				.param("recruit1.id", lineup.getRecruit1().getId().toString()) 
				.param("recruit2.id", lineup.getRecruit2().getId().toString())
				.flashAttr("recruitsSelection", listaPilotos))
		.andExpect(status().is3xxRedirection())
//		.andExpect(model().attribute("message", is("Lineup successfully saved!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testEditLineupPostHasErrors() throws Exception {
		given(this.lineupService.findLineup(Mockito.any())).willReturn(Optional.of(lineup));
	   
		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				
				// Solo falla con los param de recruit1 y 2, con los demas, da igual si son null o no :(
				// Cuando hay typeMismatch, si da error, pero cuando algo es null, no.
				
				.param("id", "uno")//lineup.getId().toString())   // Error
				.param("category", "test_error_category")//lineup.getCategory().toString())   // Error
//				.param("team", lineup.getTeam().getId().toString())    // No da error
//				.param("gp.id", lineup.getGp().getId().toString())    // No da error
				.param("recruit1.id", "test_error_string"))   // Error
//				.param("recruit2.id", lineup.getRecruit2().getId().toString()))   // Error
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("lineup"))
				.andExpect(model().attributeErrorCount("lineup", 4))
				.andExpect(view().name("thymeleaf/lineupsEdit"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteLineup() throws Exception {
		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.of(lineup));
		
		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.header("Referer", "/leagues/1/teams/1/details"))
				.andExpect(status().is3xxRedirection())
//				.andExpect(model().attribute("message", is("Lineup successfully deleted!"))) --> Hace falta ponerlo como RedirectAttribute
				.andExpect(view().name("redirect:/leagues/1/teams/1/details"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteNonExistingLineup() throws Exception {
		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.empty());
		
		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.header("Referer", "/leagues/1/teams/1/details"))
				.andExpect(status().is3xxRedirection())
//				.andExpect(model().attribute("message", is("Lineup not found!"))) --> Hace falta ponerlo como RedirectAttribute
				.andExpect(view().name("redirect:/leagues/1/teams/1/details"));
	}
	

}
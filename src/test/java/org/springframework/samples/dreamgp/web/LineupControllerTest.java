package org.springframework.samples.dreamgp.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

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
import org.springframework.samples.dreamgp.configuration.SecurityConfiguration;
import org.springframework.samples.dreamgp.model.Authorities;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.samples.dreamgp.model.TablaConsultas;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.service.AuthoritiesService;
import org.springframework.samples.dreamgp.service.GranPremioService;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.samples.dreamgp.service.PoblarBaseDeDatosService;
import org.springframework.samples.dreamgp.service.RecruitService;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.service.exceptions.UserEmailEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserPasswordEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserUsernameEmptyOrNullException;
import org.springframework.samples.dreamgp.web.LineupController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=LineupController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class LineupControllerTest {

	private static final Integer TEST_LINEUP_ID = 1;
	private static final Integer TEST_TEAM_ID = 1;
	private static final Integer TEST_LEAGUE_ID = 1;

	@MockBean
	TablaConsultasService TCService;

	@MockBean
	LineupService lineupService;

	@MockBean
	TeamService teamService;

	@MockBean
	GranPremioService granPremioService;

	@MockBean
	UserService userService;

	@MockBean
	RecruitService recruitService;

	@MockBean
	TransactionService transactionService;

	@MockBean
	AuthoritiesService authoritiesService;

	@MockBean
	PoblarBaseDeDatosService poblarBaseDeDatosService;

	@Autowired
	private MockMvc mockMvc;

	private User user = new User();

	private List<Lineup> lista = new ArrayList<Lineup>();
	private	Lineup lineup = new Lineup();
	TablaConsultas TCConsulta = new TablaConsultas();
	private GranPremio gp = new GranPremio();
	Team team = new Team();
	private List<Recruit> listaPilotos = new ArrayList<Recruit>();
	Recruit recruit1 = new Recruit();
	Recruit recruit2 = new Recruit();

	@BeforeEach 
	void setup() throws DataAccessException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {

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
		pilot1.setPoints(27);
		pilot1.setName("Ale");
		pilot1.setLastName("Ruiz");
		pilot1.setNationality("Spain");

		Pilot pilot2 = new Pilot();
		pilot2.setId(2);
		pilot2.setCategory(Category.MOTO3);
		pilot2.setPoints(7);
		pilot2.setName("Juan");
		pilot2.setLastName("Perez");
		pilot2.setNationality("Spain");

		listaPilotos.add(recruit1);
		listaPilotos.add(recruit2);

		recruit1.setId(1);
		recruit1.setPilot(pilot1);
		recruit1.setTeam(team);
		recruit1.setForSale(false);

		recruit2.setId(2);
		recruit2.setPilot(pilot2);
		recruit2.setTeam(team);
		recruit2.setForSale(false);

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

		lista.add(lineup);

		this.lineupService.saveLineup(lineup);
		this.teamService.saveTeam(team);
		this.userService.saveUser(user);
		given(this.userService.getUserSession()).willReturn(user);
		given(this.teamService.findTeamById(Mockito.anyInt())).willReturn(Optional.of(team));
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

	@WithMockUser(value = "spring")
	@Test
	void testCantCreateLineupGet() throws Exception {

		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
		given(this.lineupService.findByTeam(Mockito.anyInt())).willReturn(lista);


		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("Solo puedes tener un lineup para cada GP!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCantCreateLineupPost() throws Exception {

		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
		given(this.lineupService.findByTeam(1)).willReturn(lista);

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID)
				.with(csrf())
				.param("id", lineup.getId().toString())
				.param("category", lineup.getCategory().toString())
				.param("team", lineup.getTeam().getId().toString())
				.param("gp.id", lineup.getGp().getId().toString())
				.param("recruit1.id", lineup.getRecruit1().getId().toString()) 
				.param("recruit2.id", lineup.getRecruit2().getId().toString()) 
				.flashAttr("recruitsSelection", listaPilotos)) 
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("Solo puedes tener un lineup para cada GP!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testCrearAlineacionPost() throws Exception {

		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID)
				.with(csrf())
				.param("id", lineup.getId().toString())
				.param("category", lineup.getCategory().toString())
				.param("team", lineup.getTeam().getId().toString())
				.param("gp.id", lineup.getGp().getId().toString())
				.param("recruit1.id", lineup.getRecruit1().getId().toString()) 
				.param("recruit2.id", lineup.getRecruit2().getId().toString()) 
				.param("recruit1.forSale", Boolean.FALSE.toString())
				.param("recruit2.forSale", Boolean.FALSE.toString())
				.flashAttr("recruitsSelection", listaPilotos)) 
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearAlineacionPostHasErrors() throws Exception {

		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
		given(granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/newLineup", TEST_LEAGUE_ID, TEST_TEAM_ID)
				.with(csrf())
				.param("id", "uno")
				.param("category", "test_error_category")
				.param("gp.id", "test_error_gp_id")
				.param("recruit1.id", "test_error_string")
				.param("recruit1.forSale", Boolean.FALSE.toString()))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("lineup"))
		.andExpect(model().attributeErrorCount("lineup", 6))
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
		.andExpect(model().attribute("lineup", hasProperty("id", is(lineup.getId()))))
		.andExpect(model().attribute("lineup", hasProperty("category", is(lineup.getCategory()))))
		.andExpect(model().attribute("lineup", hasProperty("team", is(lineup.getTeam()))))
		.andExpect(model().attribute("lineup", hasProperty("gp", is(lineup.getGp()))))
		.andExpect(view().name("thymeleaf/lineupsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditLineupAlreadyRunGet() throws Exception {

		Lineup lineupAlreadyRun = lineup;
		GranPremio gpAlreadyRun = lineup.getGp();
		gpAlreadyRun.setHasBeenRun(true);
		lineupAlreadyRun.setGp(gp);

		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.of(lineupAlreadyRun));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				.header("Referer", "/leagues/1/teams/1/details")
				.param("id", lineupAlreadyRun.getId().toString())
				.param("category", lineupAlreadyRun.getCategory().toString())
				.param("team", lineupAlreadyRun.getTeam().getId().toString())
				.param("gp.id", lineupAlreadyRun.getGp().getId().toString())
				.param("recruit1.id", lineupAlreadyRun.getRecruit1().getId().toString()) 
				.param("recruit2.id", lineupAlreadyRun.getRecruit2().getId().toString())
				.flashAttr("recruitsSelection", listaPilotos))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("No se puede modificar una alineacion para un GP que ya se ha disputado!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditLineupGetError() throws Exception {

		given(this.lineupService.findLineup(lineup.getId())).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				.header("Referer", "/leagues/1/teams/1/details"))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("Lineup not found!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditLineupPost() throws Exception {

		given(this.lineupService.findLineup(lineup.getId())).willReturn(Optional.of(lineup));
		given(this.granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
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
				.param("recruit1.forSale", Boolean.FALSE.toString())
				.param("recruit2.forSale", Boolean.FALSE.toString())
				.flashAttr("recruitsSelection", listaPilotos))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("Lineup successfully saved!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditLineupAlreadyRunPost() throws Exception {

		Lineup lineupAlreadyRun = lineup;
		GranPremio gpAlreadyRun = lineup.getGp();
		gpAlreadyRun.setHasBeenRun(true);
		lineupAlreadyRun.setGp(gp);

		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.of(lineupAlreadyRun));
		given(granPremioService.findGPById(Mockito.anyInt())).willReturn(Optional.of(gp));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				.param("id", lineup.getId().toString())
				.param("category", lineup.getCategory().toString())
				.param("team", lineup.getTeam().getId().toString())
				.param("gp.id", lineup.getGp().getId().toString())
				.param("recruit1.id", lineup.getRecruit1().getId().toString()) 
				.param("recruit2.id", lineup.getRecruit2().getId().toString())
				.param("recruit1.forSale", Boolean.FALSE.toString())
				.param("recruit2.forSale", Boolean.FALSE.toString())
				.flashAttr("recruitsSelection", listaPilotos))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("No se puede modificar una alineacion para un GP que ya se ha disputado!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEditLineupPostHasErrors() throws Exception {

		given(this.lineupService.findLineup(Mockito.any())).willReturn(Optional.of(lineup));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/editLineup/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.with(csrf())
				.param("id", "uno")
				.param("category", "test_error_category")
				.param("gp.id", "test_error_gp_id")
				.param("recruit1.id", "test_error_string")
				.param("recruit1.forSale", Boolean.FALSE.toString()))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("lineup"))
		.andExpect(model().attributeErrorCount("lineup", 6))
		.andExpect(view().name("thymeleaf/lineupsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteLineup() throws Exception {

		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.of(lineup));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.header("Referer", "/leagues/1/teams/1/details"))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("Lineup successfully deleted!")))
		.andExpect(view().name("redirect:/leagues/1/teams/1/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteNonExistingLineup() throws Exception {

		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.header("Referer", "/leagues/1/teams/1/details"))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("Lineup not found!")))
		.andExpect(view().name("redirect:/leagues/1/teams/1/details"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteAlreadyRunLineup() throws Exception {

		Lineup lineupAlreadyRun = lineup;
		GranPremio gpAlreadyRun = lineup.getGp();
		gpAlreadyRun.setHasBeenRun(true);
		lineupAlreadyRun.setGp(gp);


		given(lineupService.findLineup(TEST_LINEUP_ID)).willReturn(Optional.of(lineupAlreadyRun));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete/{lineupId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_LINEUP_ID)
				.header("Referer", "/leagues/1/teams/1/details"))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("No se puede modificar una alineacion para un GP que ya se ha disputado!")))
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams/{teamId}/details"));
	}
}
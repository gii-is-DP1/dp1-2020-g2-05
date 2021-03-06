package org.springframework.samples.dreamgp.web;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.dreamgp.configuration.SecurityConfiguration;
import org.springframework.samples.dreamgp.model.Authorities;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.model.Result;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.service.AuthoritiesService;
import org.springframework.samples.dreamgp.service.LeagueService;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.samples.dreamgp.service.OfferService;
import org.springframework.samples.dreamgp.service.PilotService;
import org.springframework.samples.dreamgp.service.PoblarBaseDeDatosService;
import org.springframework.samples.dreamgp.service.RecruitService;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.service.exceptions.UserEmailEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserPasswordEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.UserUsernameEmptyOrNullException;
import org.springframework.samples.dreamgp.service.exceptions.duplicatedLeagueNameException;
import org.springframework.samples.dreamgp.web.PilotController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(controllers=PilotController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)



public class PilotControllerTest {

	private static final Integer TEST_LEAGUE_ID = 1;
	private static final Integer TEST_TEAM_ID = 1;
	private static final Integer PILOT_TEST_ID = 2345;

	@MockBean
	private UserService userService;

	@MockBean
	private TablaConsultasService tablaConsultas;

	@MockBean
	private RecruitService recruitService;

	@MockBean
	private PilotService pilotService;

	@MockBean
	private LineupService lineupService;

	@MockBean
	private OfferService offerService;

	@MockBean
	private TeamService teamService;

	@MockBean
	private LeagueService leagueService;
	
	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private PoblarBaseDeDatosService PBDService;

	@Autowired
	private MockMvc mockMvc;

	private User user = new User();
	private List<Team> list1 = new ArrayList<>();
	League liga = new League();
	Team team = new Team();
	Pilot pilot = new Pilot();
	private Pageable pageable = PageRequest.of(0, 5, Sort.by(Order.asc("category"), Order.asc("name"), Order.desc("lastName")));
	private Page<Pilot> paginaPilotos;
	private String pageNumber="0";
	private String pageSize="5";

	@BeforeEach
	void setup() throws DataAccessException, duplicatedLeagueNameException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {
		user.setUsername("migue");
		user.setPassword("asd");
		user.setEmail("migue@mail.com");
		user.setEnabled(true);
		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);
		user.setAuthorities(auth);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		liga.setId(TEST_LEAGUE_ID);
		liga.setLeagueCode("IOKASXISAU");

		team.setId(TEST_TEAM_ID);
		team.setName("Migue");
		team.setLeague(liga);
		team.setMoney(200);
		team.setPoints(100);
		team.setUser(user);

		Set<Team> teams = new HashSet<>();
		teams.add(team);
		list1.add(team);

		liga.setTeam(teams);
		user.setTeam(teams);

		this.leagueService.saveLeague(liga);
		this.teamService.saveTeam(team);
		this.userService.saveUser(user);

		List<League> list = new ArrayList<League>();
		list.add(liga);

		pilot.setBaseValue(2000);
		pilot.setCategory(Category.MOTO2);
		pilot.setPoints(2);
		pilot.setId(PILOT_TEST_ID);
		pilot.setLastName("Nieva");
		pilot.setName("Migue");
		pilot.setNationality("Spain");
		pilot.setResults(new HashSet<Result>());

		List<Pilot> listaPilotosPagina = new ArrayList<Pilot>();
		listaPilotosPagina.add(pilot);
		paginaPilotos= new PageImpl<>(listaPilotosPagina, pageable, 0);

		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
		given(this.userService.findAuthoritiesByUsername(team.getUser().getUsername())).willReturn("admin");
		given(this.pilotService.findPilotById(PILOT_TEST_ID)).willReturn(Optional.of(pilot));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		given(this.pilotService.findPilotById(PILOT_TEST_ID)).willReturn(Optional.of(pilot));

		mockMvc.perform(get("/pilots/new"))
		.andExpect(status()
				.isOk())
		.andExpect(view().name("pilots/pilotsEdit"))
		.andExpect(model().attributeExists("pilot"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		given(this.pilotService.findPilotById(PILOT_TEST_ID)).willReturn(Optional.of(pilot));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));


		mockMvc.perform(post("/pilots/save")
				.with(csrf())
				.param("name", "aaa")
				.param("lastName", "aaa")
				.param("points", "!!")
				.param("category", "h")
				.param("nationality" , "@@@" ))
		.andExpect(model().attributeHasErrors("pilot"))
		.andExpect(model().attributeHasFieldErrors("pilot", "category"))
		.andExpect(status().isOk())
		.andExpect(view().name("pilots/pilotsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccesfull() throws Exception {
		given(this.pilotService.findPilotById(PILOT_TEST_ID)).willReturn(Optional.of(pilot));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));


		mockMvc.perform(post("/pilots/save")
				.with(csrf())
				.param("name", "Antonio")
				.param("lastName", "Perez")
				.param("points", "123")
				.param("baseValue", pilot.getBaseValue().toString())
				.param("category", pilot.getCategory().toString())
				.param("nationality" , "Spain" ))
		.andExpect(model().attributeHasNoErrors("pilot"))
		.andExpect(status().isOk())
		.andExpect(view().name("pilots/pilotsListPaged"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeletePilot() throws Exception {
		given(this.pilotService.findPilotById(PILOT_TEST_ID)).willReturn(Optional.of(pilot));

		mockMvc.perform(get("/pilots/delete/{pilotId}", PILOT_TEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/pilotsPaged"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeletePilotErrors() throws Exception {
		given(this.pilotService.findPilotById(PILOT_TEST_ID)).willReturn(Optional.empty());

		mockMvc.perform(get("/pilots/delete/{pilotId}", PILOT_TEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/pilotsPaged"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPagePilot() throws Exception {
		Integer maxPageNumber = (int) Math.ceil((double) paginaPilotos.getTotalElements()/paginaPilotos.getSize());
		when(this.pilotService.findAllPage(any())).thenReturn(paginaPilotos);
		mockMvc.perform(get("/pilotsPaged")
				.param("pageNumber", pageNumber)
				.param("pageSize", pageSize))
		.andExpect(status().isOk())
		.andExpect(model().attribute("pageNumber", is(Integer.parseInt(pageNumber))))
		.andExpect(model().attribute("pageSize", is(Integer.parseInt(pageSize))))
		.andExpect(model().attribute("maxPageNumber", is(maxPageNumber)))
		.andExpect(model().attribute("resultadosPaginados", is(paginaPilotos.getContent())))
		.andExpect(view().name("pilots/pilotsListPaged"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPagePilotPageNumberGreaterThanMaxPageNumber() throws Exception {
		Integer maxPageNumber = (int) Math.ceil((double) paginaPilotos.getTotalElements()/paginaPilotos.getSize());
		Integer cuenta = maxPageNumber+1;
		when(this.pilotService.findAllPage(any())).thenReturn(paginaPilotos);
		mockMvc.perform(get("/pilotsPaged")
				.param("pageNumber", cuenta.toString())
				.param("pageSize", pageSize))
		.andExpect(status().isOk())
		.andExpect(model().attribute("pageNumber", is(maxPageNumber-1)))
		.andExpect(model().attribute("pageSize", is(Integer.parseInt(pageSize))))
		.andExpect(model().attribute("maxPageNumber", is(maxPageNumber)))
		.andExpect(model().attribute("resultadosPaginados", is(paginaPilotos.getContent())))
		.andExpect(view().name("pilots/pilotsListPaged"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPagePilotNumberFormatException() throws Exception {
		pageNumber="test";
		pageSize="test";
		Integer maxPageNumber = (int) Math.ceil((double) paginaPilotos.getTotalElements()/paginaPilotos.getSize());

		when(this.pilotService.findAllPage(any())).thenReturn(paginaPilotos);
		mockMvc.perform(get("/pilotsPaged")
				.param("pageNumber", pageNumber)
				.param("pageSize", pageSize))
		.andExpect(status().isOk())
		.andExpect(model().attribute("pageNumber", is(0))) //modificar si se modifica en el controller
		.andExpect(model().attribute("pageSize", is(5))) //modificar si se modifica en el controller
		.andExpect(model().attribute("maxPageNumber", is(maxPageNumber)))
		.andExpect(model().attribute("resultadosPaginados", is(paginaPilotos.getContent())))
		.andExpect(view().name("pilots/pilotsListPaged"));
	}

}

package org.springframework.samples.petclinic.web;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedRiderOnLineupException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.samples.petclinic.service.exceptions.duplicatedLeagueNameException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
@WebMvcTest(controllers=TeamController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class TeamControllerTest {

	private static final Integer TEST_LEAGUE_ID = 1;
	
	private static final Integer TEST_TEAM_ID = 1;

	private static final Integer TEST_TEAM1_ID = 2;

	private static final Integer TEST_RECRUIT_ID = 1;

	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	@Autowired
	private UserService userService;


	@MockBean
	@Autowired
	private RecruitService recruitService;

	@MockBean
	@Autowired
	private LineupService lineupService;

	@MockBean
	@Autowired
	private TransactionService transactionService;

	@MockBean
	@Autowired
	private OfferService offerService;

	@MockBean
	private TablaConsultasService tablaConsultas;

	@MockBean
	@Autowired
	private TeamService teamService;

	@MockBean
	private LeagueService leagueService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private MockMvc mockMvc;
	
    private User user = new User();
    private User user1 = new User();
    private List<Team> list1 = new ArrayList<>();
    private List<Team> list2 = new ArrayList<>();
	League liga = new League();
	League liga1 = new League();
	Team team = new Team();
	Team team1 = new Team();
	Recruit recruit1 = new Recruit();
	Recruit recruit2 = new Recruit();
	Recruit recruit3 = new Recruit();
	Recruit recruit4 = new Recruit();
	List<Recruit> listRecruits = new ArrayList<>();
	List<Recruit> listRecruits2 = new ArrayList<>();
	
    private Lineup lineup = new Lineup();
    private List<Lineup> listaLineups = new ArrayList<Lineup>();
    private List<Recruit> listaRecruitsNoEnVenta = new ArrayList<Recruit>();
    private List<Recruit> listaRecruitsEnVenta = new ArrayList<Recruit>();
	
	@BeforeEach
	void setup() throws DataAccessException, duplicatedLeagueNameException, DuplicatedRiderOnLineupException {
		
	
		user1.setUsername("miguel");
		user1.setPassword("asdd");
		user1.setEmail("miguel@mail.com");
		user1.setEnabled(true);
		Set<Authorities> auth1 = new HashSet<Authorities>();
		Authorities autho1 = new Authorities();
		autho1.setAuthority("user");
		autho1.setUser(user);
		autho1.setId(1);
		auth1.add(autho1);
		user1.setAuthorities(auth1);
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
		liga1.setId(TEST_LEAGUE_ID);
		liga1.setLeagueCode("IOKASXISAX");
		liga1.setName("Ligue2");
		liga1.setLeagueDate(formatter.format(date));
		liga.setId(TEST_LEAGUE_ID);
		liga.setLeagueCode("IOKASXISAU");
		liga.setName("Ligue1");
		liga.setLeagueDate(formatter.format(date));
		team.setId(TEST_TEAM_ID);
		team.setName("Migue");
		team.setLeague(liga);
		team.setMoney(200);
		team.setPoints(100);
		team.setUser(user);

		team1.setId(TEST_TEAM1_ID);
		team1.setName("Miguel");
		team1.setLeague(liga);
		team1.setMoney(200);
		team1.setPoints(100);
		team1.setUser(user1);

		Set<Team> teams = new HashSet<>();
		Set<Team> teams2 = new HashSet<>();
		Set<Team> teams1 = new HashSet<>();
		teams.add(team);
		teams1.add(team1);
		list1.add(team);
		list1.add(team1);
		
		liga.setTeam(teams);
		liga1.setTeam(teams2);
		user.setTeam(teams);

		this.leagueService.saveLeague(liga);
		this.leagueService.saveLeague(liga1);
		this.teamService.saveTeam(team);
		this.userService.saveUser(user);
        List<League> list = new ArrayList<League>();
		list.add(liga);
		
		recruit1.setTeam(team);
		listRecruits.add(recruit1);
		Recruit recruit5 = new Recruit();
		listaRecruitsNoEnVenta.add(recruit1);
		listaRecruitsNoEnVenta.add(recruit5);

		recruit2.setTeam(team1);
		recruit3.setTeam(team1);
		recruit4.setTeam(team1);
		listRecruits2.add(recruit2);
		listRecruits2.add(recruit3);
		listRecruits2.add(recruit4);
		user.setUsername("antcammar4");
		user.setEnabled(true);

		Pilot pilot1 = new Pilot();
		pilot1.setId(1);
		pilot1.setCategory(Category.MOTO3);
		pilot1.setPoints("27");
		pilot1.setName("Ale");
		pilot1.setLastName("Ruiz");
		pilot1.setNationality("Spain");

		Pilot pilot2 = new Pilot();
		pilot2.setId(2);
		pilot2.setCategory(Category.MOTO3);
		pilot2.setPoints("7");
		pilot2.setName("Juan");
		pilot2.setLastName("Perez");
		pilot2.setNationality("Spain");

		recruit1.setId(1);
		recruit1.setPilot(pilot1);
		recruit1.setTeam(team);

		Recruit recruit2 = new Recruit();
		recruit2.setId(2);
		recruit2.setPilot(pilot2);
		recruit2.setTeam(team);

		GranPremio gp = new GranPremio();
		gp.setId(1);
		gp.setCalendar(true);
		gp.setCircuit("Losail");
		gp.setDate0(LocalDate.of(2020, 3, 8));
		gp.setHasBeenRun(false);
		gp.setRaceCode("QAT");
		gp.setSite("Grand Prix Of Qatar");

		lineup.setId(1);
		lineup.setCategory(Category.MOTO3);
		lineup.setGp(gp);
		lineup.setRecruit1(recruit1);
		lineup.setRecruit2(recruit2);
		lineup.setTeam(team);

		this.lineupService.saveLineup(lineup);

		listaLineups.add(lineup);
		listaRecruitsEnVenta.add(recruit1);
		listaRecruitsNoEnVenta.add(recruit2);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
		given(this.leagueService.findAuthoritiesByUsername(team.getUser().getUsername())).willReturn("admin");
		given(this.teamService.findTeamById(TEST_TEAM1_ID)).willReturn(Optional.of(team1));
//		given(this.teamService.findTeamByUsernameAndLeagueId(user.getUsername(), TEST_LEAGUE_ID)).willReturn(Optional.of(team));
	}

//	@GetMapping(path = "/leagues/{leagueId}/teams/{teamId}/details")
//	public String mostrarDetallesEscuderia(@PathVariable("leagueId") int leagueId, @PathVariable("teamId") int teamID,
//			ModelMap model) {
//		Optional<Team> team = this.teamService.findTeamById(teamID);
//
//			model.addAttribute("message", "Team found!");
//			model.addAttribute("team", team.get());
//			List<Recruit> fichajesEnVenta = recruitService.getRecruitsOnSaleByTeam(teamID);
//			List<Recruit> fichajes = recruitService.getRecruitsNotOnSaleByTeam(teamID);
//			model.addAttribute("misFichajes", fichajes);
//			model.addAttribute("fichajesEnVenta", fichajesEnVenta);
//			model.addAttribute("misAlineaciones", lineupService.findByTeam(teamID));
//		return "/leagues/teamDetails";
//	}

	@WithMockUser(value = "spring")
	@Test
	void testShowExistentTeamDetails() throws Exception {

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(recruitService.getRecruitsOnSaleByTeam(TEST_TEAM_ID)).willReturn(listaRecruitsEnVenta);
		given(recruitService.getRecruitsNotOnSaleByTeam(TEST_TEAM_ID)).willReturn(listaRecruitsNoEnVenta);
		given(lineupService.findByTeam(TEST_TEAM_ID)).willReturn(listaLineups);

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/details", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(model().attribute("team", is(team)))
				.andExpect(model().attribute("misFichajes", is(listaRecruitsNoEnVenta)))
				.andExpect(model().attribute("fichajesEnVenta", is(listaRecruitsEnVenta)))
				.andExpect(model().attribute("misAlineaciones", is(listaLineups))).andExpect(status().isOk())
				.andExpect(model().attribute("message", is("Team found!")))
				.andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowNonExistentTeamDetails() throws Exception {

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.empty());
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/details", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(status().isOk()).andExpect(model().attribute("message", is("Team not found!")))
				.andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {

		mockMvc.perform(get("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).header("Referer", "http://localhost:8090/leagues/join"))
		.andExpect(status()
				.isOk())
		.andExpect(view().name("/leagues/TeamsEdit"))
		.andExpect(model().attributeExists("team"));
	}	

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormNegative() throws Exception {
		given(this.teamService.findTeamsByLeagueId(TEST_LEAGUE_ID)).willReturn(6);

		mockMvc.perform(get("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).header("Referer", "http://localhost:8090/leagues/join"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/leagues/{leagueId}/teams"));
	}	

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormException() throws Exception {
		mockMvc.perform(get("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).header("Referer", "http://localhost:8090/leagues"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/leagues"));
	}	

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);

		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).with(csrf()).param("name", "Betty")
				.param("points", "aaa").param("money", "!!")).andExpect(model().attributeHasErrors("team"))
				.andExpect(model().attributeHasFieldErrors("team", "points"))
				.andExpect(model().attributeHasFieldErrors("team", "money")).andExpect(status().isOk())
				.andExpect(view().name("/leagues/TeamsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasSuccess() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));

		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).with(csrf()).param("name", team.getName())
				.param("points", team.getPoints().toString()).param("money", team.getMoney().toString())
				.param("user.username", user.getUsername()).param("league.id", TEST_LEAGUE_ID.toString()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("/leagues/TeamList"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testExistsATeamWithThisName() throws Exception {
		given(this.userService.getUserSession()).willReturn(user1);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.teamService.findTeamByUsernameAndLeagueId(user.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team));
		given(this.teamService.findTeamByLeagueId(TEST_LEAGUE_ID)).willReturn(list1);

		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).with(csrf()).param("name", team.getName())
				.param("points", team1.getPoints().toString()).param("money", team1.getMoney().toString())
				.param("user.username", user.getUsername()).param("league.id", TEST_LEAGUE_ID.toString()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/leagues/{leagueId}/teams"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testUserHaveATeamInThisLeague() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.teamService.findTeamByUsernameAndLeagueId(user.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team));
		given(this.teamService.findTeamByLeagueId(TEST_LEAGUE_ID)).willReturn(list1);

		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID).with(csrf()).param("name", team.getName())
				.param("points", team.getPoints().toString()).param("money", team.getMoney().toString())
				.param("user.username", user.getUsername()).param("league.id", TEST_LEAGUE_ID.toString()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/leagues/{leagueId}/teams"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		given(this.leagueService.findAuthoritiesByUsername(team.getUser().getUsername())).willReturn("admin");
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("team"))
				.andExpect(model().attributeExists("Editar")).andExpect(view().name("leagues/TeamsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID)
				.with(csrf())
				.param("name", "Migue")
				.param("points", "124")
				.param("money", "300")
				.param("team.id", TEST_TEAM_ID.toString())
				.param("user.username" , user.getUsername())
				.param("league.id", TEST_LEAGUE_ID.toString()))
		.andExpect(status().isOk())
		.andExpect(view().name("Perfil/Perfil"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm1() throws Exception {
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user1));
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team1));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.leagueService.findAuthoritiesByUsername(user1.getUsername())).willReturn("user");
		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("team"))
				.andExpect(model().attributeExists("Editar")).andExpect(view().name("leagues/TeamsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.leagueService.findAuthoritiesByUsername(user.getUsername()))
				.willReturn(user.getAuthorities().toString());

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID).with(csrf())
				.param("name", "125").param("points", "aaa").param("money", "aaa")
				.param("user.username", user.getUsername()).param("league.id", TEST_LEAGUE_ID.toString()))
				.andExpect(model().attributeHasErrors("team"))
				.andExpect(model().attributeHasFieldErrors("team", "points"))
				.andExpect(model().attributeHasFieldErrors("team", "name"))
				.andExpect(model().attributeHasFieldErrors("team", "money")).andExpect(status().isOk())
				.andExpect(view().name("/leagues/TeamsEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSetPriceSuccess() throws Exception {
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.recruitService.findRecruitById(TEST_RECRUIT_ID)).willReturn(Optional.of(recruit1));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/details/{recruitId}", TEST_LEAGUE_ID, TEST_TEAM_ID,
				TEST_RECRUIT_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("offer"))
				.andExpect(model().attributeExists("recruitToSale")).andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSetPriceError() throws Exception {
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.recruitService.findRecruitById(10)).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/details/{recruitId}", TEST_LEAGUE_ID, TEST_TEAM_ID, 10))
				.andExpect(status().isOk()).andExpect(model().attributeExists("message"))
				.andExpect(model().attribute("message", is("Recruit not found!")))
				.andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPutOnSaleSuccess() throws Exception {
		given(this.recruitService.getRecruitsByTeam(team1.getId())).willReturn(listRecruits2);
		given(this.teamService.findTeamById(TEST_TEAM1_ID)).willReturn(Optional.of(team1));
		given(this.recruitService.findRecruitById(2)).willReturn(Optional.of(recruit2));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/details/{recruitId}", TEST_LEAGUE_ID, TEST_TEAM1_ID, 2)
				.with(csrf())
				.param("status", Status.Outstanding.toString())
				.param("price", "1500"))
			.andExpect(status().isOk())
			.andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPutOnSaleWith2RecruitsOrLess() throws Exception {
		given(this.recruitService.getRecruitsNotOnSaleByTeam(TEST_TEAM_ID)).willReturn(listaRecruitsNoEnVenta);
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.recruitService.findRecruitById(TEST_RECRUIT_ID)).willReturn(Optional.of(recruit1));
		when(this.recruitService.putOnSale(any())).thenThrow(NotAllowedNumberOfRecruitsException.class);
		
		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/details/{recruitId}", TEST_LEAGUE_ID, TEST_TEAM_ID, TEST_RECRUIT_ID)
				.with(csrf())
				.param("status", Status.Outstanding.toString())
				.param("price", "1500"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("message",is("You must own at least 2 riders not on sale to perform this action")))
			.andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testPutOnSaleError() throws Exception {
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.recruitService.findRecruitById(TEST_RECRUIT_ID)).willReturn(Optional.of(recruit1));

		mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/details/{recruitId}", TEST_LEAGUE_ID, TEST_TEAM_ID,
				TEST_RECRUIT_ID).with(csrf()).param("status", "").param("price", "")).andExpect(status().isOk())
				.andExpect(model().attributeExists("message")).andExpect(view().name("/leagues/teamDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteTeam() throws Exception {
		given(this.teamService.findTeamByLeagueId(TEST_LEAGUE_ID)).willReturn(list1);

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/leagues/{leagueId}/teams"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteTeam1() throws Exception {
		given(this.teamService.findTeamByLeagueId(TEST_LEAGUE_ID)).willReturn(list2);

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/myTeams"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteTeamError() throws Exception {

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.empty());
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));

		mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete", TEST_LEAGUE_ID, TEST_TEAM_ID))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/leagues"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testMyTeams() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(this.teamService.findTeamByUsername(user.getUsername())).willReturn(list1);
		mockMvc.perform(get("/myTeams")).andExpect(status().isOk()).andExpect(model().attributeExists("teams"))
				.andExpect(model().attribute("teams",
						Matchers.hasItem(Matchers.<Team>hasProperty("name", is(team.getName())))))
				.andExpect(view().name("Perfil/Perfil"));
	}

	@WithMockUser(value = "spring")
	@Test
	void TestShowTeams() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.teamService.findTeamByLeagueId(TEST_LEAGUE_ID)).willReturn(list1);

		mockMvc.perform(get("/leagues/{leagueId}/teams", TEST_LEAGUE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("teams")).andExpect(model().attributeExists("league"))
				.andExpect(model().attributeExists("user")).andExpect(view().name("/leagues/TeamList"));

	}

	@WithMockUser(value = "spring")
	@Test
	void TestShowTeamsError() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/teams", TEST_LEAGUE_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/leagues"));

	}
}
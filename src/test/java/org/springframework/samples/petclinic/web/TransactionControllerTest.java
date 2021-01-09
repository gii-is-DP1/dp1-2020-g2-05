package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LeagueController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class TransactionControllerTest {
	class TestNest {
		@MockBean
		@Autowired
		TransactionController transactionController;

		@MockBean
		@Autowired
		TransactionService transactionService;

		@MockBean
		@Autowired
		TeamService teamService;

		@MockBean
		@Autowired
		UserService userService;

		@Autowired
		private MockMvc mockMvc;
//
//		private User user = new User();
//		private List<League> lista = new ArrayList<League>();
//		private League liga = new League();

//		@BeforeEach
//		void setup(TestInfo testInfo) throws DataAccessException, duplicatedLeagueNameException {
//			if (testInfo.getTags().contains("SkipCleanup")) {
//				return;
//			}
//			TCConsulta.setCurrentCategory(Category.MOTO3);
//			TCConsulta.setRacesCompleted(0);
//			user.setUsername("antcammar4");
//			user.setEnabled(true);
//
//			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//			Date date = new Date();
//			liga.setId(TEST_LEAGUE_ID);
//			liga.setLeagueCode("UDTQCSSOND");
//			liga.setName("prueba");
//			liga.setLeagueDate(formatter.format(date));
//
//			Set<Authorities> auth = new HashSet<Authorities>();
//			Authorities autho = new Authorities();
//			user.setPassword("test");
//			autho.setAuthority("admin");
//			autho.setUser(user);
//			autho.setId(1);
//			auth.add(autho);
//
//			user.setAuthorities(auth);
//
//			Set<Team> teams = new HashSet<Team>();
//			Team team = new Team();
//			team.setId(TEST_TEAM_ID);
//			team.setLeague(liga);
//			team.setMoney(2);
//			team.setName("teamtest");
//			team.setPoints(132);
//			team.setUser(user);
//			teams.add(team);
//			user.setTeam(teams);
//			liga.setTeam(teams);
//
//			this.leagueService.saveLeague(liga);
//			this.teamService.saveTeam(team);
//
//			lista.add(liga);
//
//			this.userService.saveUser(user);
//			given(this.userService.getUserSession()).willReturn(user);
//
//		}
//
//		@WithMockUser(value = "spring")
//		@Test
//		void testShowTransactionList() throws Exception {
//			given(this.transactionService.getTeamTransactions(TEST_TEAM_ID).willReturn(transactions);
//			given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("admin");
//			given(this.TCService.getTabla()).willReturn(Optional.of(this.TCConsulta));
////	    		given( this.TCService.getTabla().get().getCurrentCategory()).willReturn(Category.MOTO2);
////	    		given( this.TCService.getTabla().get().getRacesCompleted()).willReturn(0);
//
//			mockMvc.perform(get("/leagues")).andExpect(status().isOk()).andExpect(view().name("leagues/leagueList"))
//					.andExpect(model().attribute("admin", is(true)))
//					.andExpect(model().attribute("categoriaActual", is(Category.MOTO3)))
//					.andExpect(model().attribute("carrerasCompletadas", is(0))).andExpect(model().attribute("ligas",
//							Matchers.hasItem(Matchers.<League>hasProperty("leagueCode", is(liga.getLeagueCode())))));
//
//		}
	}
}

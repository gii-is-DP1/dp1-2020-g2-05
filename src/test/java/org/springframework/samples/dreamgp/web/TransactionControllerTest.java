package org.springframework.samples.dreamgp.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.dreamgp.configuration.SecurityConfiguration;
import org.springframework.samples.dreamgp.model.Authorities;
import org.springframework.samples.dreamgp.model.League;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.model.Transaction;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.service.AuthoritiesService;
import org.springframework.samples.dreamgp.service.LineupService;
import org.springframework.samples.dreamgp.service.PoblarBaseDeDatosService;
import org.springframework.samples.dreamgp.service.TablaConsultasService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.web.TransactionController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = TransactionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class TransactionControllerTest {

	private static final int TEST_TEAM_ID = 11;

	@MockBean
	private TransactionService transactionService;

	@MockBean
	private TeamService teamService;

	@MockBean
	private UserService userService;

	@MockBean
	private TablaConsultasService tablaConsultas;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private PoblarBaseDeDatosService PBDService;
	
	@MockBean
	private LineupService lineupService;

	@Autowired
	private MockMvc mockMvc;

	private User user = new User();
	private League league = new League();
	private Team team = new Team();
	private List<Transaction> transactions;

	@BeforeEach
	void setUp() {

		user.setUsername("alvcorcas");
		user.setEnabled(true);
		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		user.setPassword("test");
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);
		user.setAuthorities(auth);

		league.setId(1);
		league.setLeagueCode("QWEASDFRGT");
		league.setLeagueDate("12/12/2020");
		league.setName("Test");

		team.setId(TEST_TEAM_ID);
		team.setLeague(league);
		team.setMoney(2000);
		team.setName("polloRebozado");
		team.setPoints(0);
		team.setUser(user);

		Set<Team> teamSet = Stream.of(team).collect(Collectors.toSet());

		league.setTeam(teamSet);

		Transaction transaction = new Transaction();
		transaction.setId(1);
		transaction.setAmount(2000);
		transaction.setConcept("Recompensa por buenos resultados de Test Ramirez");
		transaction.setDate(LocalDate.now());
		transaction.setRemainingMoney(team.getMoney());
		transaction.setTeam(team);

		transactions = Stream.of(transaction).collect(Collectors.toList());

		given(this.userService.getUserSession()).willReturn(user);

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		
		given(this.transactionService.findTransactionsByTeamId(TEST_TEAM_ID)).willReturn(transactions);

	}

	@WithMockUser(value = "spring")
	@Test
	void testGetTransactionList() throws Exception {
		mockMvc.perform(get("/myTeams/{teamID}/transactions", TEST_TEAM_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("transactions"))
				.andExpect(model().attribute("transactions", transactions)).andExpect(model().attributeExists("money"));
	}

}

package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=OwnerController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class LeagueControllerTest {

	private static final int TEST_LEAGUE_ID = 1;
	private static final int TEST_TEAM_ID = 1;

//	@Autowired
//	private LeagueController leagueController;

	@MockBean
	private LeagueService leagueService;
        
        @MockBean
	private UserService userService;
        
        @MockBean
        private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private League liga;
	
	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("usertest");
		
		
		Set<Team> teams = new HashSet<Team>();
		Team team = new Team();
		team.setId(TEST_TEAM_ID);
		team.setLeague(liga);
		team.setMoney("2");
		team.setName("teamtest");
		team.setPoints("132");
		team.setUser(user);
		teams.add(team);
		user.setTeam(teams);

		
		liga.setId(TEST_LEAGUE_ID);
		liga.setLeagueCode("IOKASXISAU");
		liga.setLeagueDate("15/10/2020");
		liga.setMoto2Active(true);
		liga.setMoto3Active(false);
		liga.setMotogpActive(false);
		liga.setName("liga");
		liga.setRacesCompleted(2);
		liga.setTeam(teams);

		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.userService.findUser("usertest")).willReturn(Optional.of(user));
		given(this.leagueService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));

	}

//	@WithMockUser(value = "spring")
//        @Test
//	void testTableLeagues() throws Exception {
//		mockMvc.perform(get("/leagues")).andExpect(status().isOk()).andExpect(model().attributeExists("ligas"))
//				.andExpect(view().name("leagues/leagueList"));
//	}
}

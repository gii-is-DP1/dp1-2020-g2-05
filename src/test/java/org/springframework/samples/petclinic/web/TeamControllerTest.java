package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest(controllers = TeamController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class TeamControllerTest {
	
	private static final int TEST_LEAGUE_ID = 1;

	private static final int TEST_TEAM_ID = 1;

	@Autowired
	private TeamController teamController;
	
	@MockBean
	private LeagueService leagueService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		
		org.springframework.samples.petclinic.model.User user = new org.springframework.samples.petclinic.model.User();
		League liga = new League();
		Team team = new Team();
		
		user.setUsername("migue");
		user.setPassword("asd");
		user.setEmail("migue@mail.com");
		
		
		
		liga.setId(TEST_LEAGUE_ID);
		liga.setLeagueCode("IOKASXISAU");
		liga.setLeagueDate("15/10/2020");
		liga.setMoto2Active(true);
		liga.setMoto3Active(false);
		liga.setMotogpActive(false);
		liga.setName("liga");
		liga.setRacesCompleted(2);
		
		
		
		team.setId(1);
		team.setName("Migue");
		team.setLeague(liga);
		team.setMoney("200");
		team.setPoints("100");
		team.setUser(user);
		
		
		Set<Team> teams = new HashSet<>();
		teams.add(team);
		
		liga.setTeam(teams);
		user.setTeam(teams);

		given(this.leagueService.findLeague(1).get().getTeam()).willReturn(teams);
		given(this.leagueService.findLeague(TEST_LEAGUE_ID).get()).willReturn(liga);
		given(this.leagueService.findTeamById(TEST_TEAM_ID).get()).willReturn(team);
		
	}
	
	@WithMockUser(value = "team")
  @Test
void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID)).andExpect(status().isOk())
			.andExpect(view().name("leagues/TeamsEdit")).andExpect(model().attributeExists("team"));
}	
	
	@WithMockUser(value = "team")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("points", "aaa"))
				.andExpect(model().attributeHasNoErrors("league"))
				.andExpect(model().attributeHasErrors("team"))
				.andExpect(status().isOk())
				.andExpect(view().name("leagues/TeamsEdit"));
	}


}

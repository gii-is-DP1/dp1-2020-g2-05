package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;


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

import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.is;


@WebMvcTest(controllers=TeamController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

public class TeamControllerTest {
	
	private static final int TEST_LEAGUE_ID = 1;

	private static final int TEST_TEAM_ID = 1;
	

	

//	@Autowired
//	private TeamController teamController;
//	
	
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
	private LeagueService leagueService;
	
	@Autowired
	private MockMvc mockMvc;
	
    private User user = new User();
    private List<Team> list1 = new ArrayList<>();
	League liga = new League();
	Team team = new Team();


	
	@BeforeEach
	void setup() throws DataAccessException, duplicatedLeagueNameException {
		
//		org.springframework.samples.petclinic.model.User user = new org.springframework.samples.petclinic.model.User();
//		Team team = new Team();
//		

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
		liga.setLeagueDate(formatter.format(date));
		liga.setMoto2Active(true);
		liga.setMoto3Active(false);
		liga.setMotogpActive(false);
		liga.setName("liga");
		liga.setRacesCompleted(0);

		team.setId(TEST_TEAM_ID);
		team.setName("Migue");
		team.setLeague(liga);
		team.setMoney("200");
		team.setPoints("100");
		team.setUser(user);
		
		
		Set<Team> teams = new HashSet<>();
		teams.add(team);
		list1.add(team);
		
		liga.setTeam(teams);
		user.setTeam(teams);
		
		this.leagueService.saveLeague(liga);
		this.leagueService.saveTeam(team);
		this.userService.saveUser(user);

        List<League> list = new ArrayList<League>();
		list.add(liga);
		


		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(this.leagueService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));

	}
	
	@WithMockUser(value = "spring")
  @Test
  	void testInitCreationForm() throws Exception {
		
		mockMvc.perform(get("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID)).andExpect(status().isOk())
			.andExpect(view().name("/leagues/TeamsEdit")).andExpect(model().attributeExists("team"));
}	
	
//	
//	@WithMockUser(value = "spring")
//    @Test
//	void testProcessCreationFormHasErrors() throws Exception {
//		 given(this.userService.getUserSession())
//		  .willReturn(user);
//
//		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID)
//							.with(csrf())
//							.param("name", "Betty")
//							.param("points", "aaa")
//							.param("money", "!!"))
//				.andExpect(model().attributeHasErrors("team"))
//				.andExpect(model().attributeHasFieldErrors("team", "points"))
//				.andExpect(model().attributeHasFieldErrors("team", "money"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("/leagues/TeamsEdit"));
//	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormHasNoErrors() throws Exception {
		 given(this.userService.getUserSession())
		  .willReturn(user);

		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID)
							.with(csrf())
							.param("name", "prueba")
							.param("points", "123")
							.param("money", "12"))
							.andExpect(status().isOk())
							.andExpect(view().name("redirect:/leagues/{leagueId}/teams"));
				
	}
//	  @WithMockUser(value = "spring")
//		@Test
//		void testInitUpdateForm() throws Exception {
//			mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID))
//					.andExpect(status().isOk()).andExpect(model().attributeExists("team"))
//					.andExpect(view().name("leagues/TeamsEdit"));
//		}
	  
//	  @WithMockUser(value = "spring")
//		@Test
//		void testProcessUpdateFormSuccess() throws Exception {
//			given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
//
//			mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID)
//								.with(csrf())
//								.param("name", "Migue")
//								.param("points", "124")
//								.param("money", "300"))
//					.andExpect(status().is3xxRedirection())
//					.andExpect(view().name("/leagues/TeamList"));
//		}
//	  
//	  @WithMockUser(value = "spring")
//		@Test
//		void testProcessUpdateFormHasErrors() throws Exception {
//		  given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
//			mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID)
//								.with(csrf())
//								.param("name", "@@@@")
//								.param("points", "aaaa")
//								.param("money", "awe"))
//					.andExpect(model().attributeHasErrors("team"))
//					.andExpect(model().attributeHasFieldErrors("team", "points"))
//					.andExpect(model().attributeHasFieldErrors("team", "money"))
//					.andExpect(status().isOk())
//					.andExpect(view().name("leagues/TeamsEdit"));
//		}
	  
//	  
//	  @WithMockUser(value = "spring")
//			@Test
//			void testDeleteTeam() throws Exception {
//				mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete", TEST_LEAGUE_ID, TEST_TEAM_ID))
//						.andExpect(status().isOk()).andExpect(model().attributeExists("team"))
//						.andExpect(view().name("redirect:/leagues/{leagueId}/teams"));
//			}
//	  
//	  @WithMockUser(value = "spring")
//	  @Test
//		void testMyTeams() throws Exception {
//		  given(this.userService.getUserSession())
//		  .willReturn(user);
//		  given(this.leagueService.findTeamByUsername(user.getUsername())).willReturn(list1);
//			mockMvc.perform(get("/myTeams"))
//					.andExpect(status().isOk())
//					.andExpect(model().attributeExists("teams"))
//					.andExpect(model().attribute("teams", Matchers.hasItem(Matchers.<Team> hasProperty("name", is(team.getName())))))
//					.andExpect(view().name("leagues/myTeams"));
//		}
}

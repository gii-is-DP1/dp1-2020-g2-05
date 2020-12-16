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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.hamcrest.collection.HasItemInArray;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers=LeagueController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class LeagueControllerTest {
	
	private static final int TEST_LEAGUE_ID = 1;

	private static final int TEST_TEAM_ID = 1;


	@MockBean
	@Autowired	
	LeagueService leagueService;
	
	@MockBean
	@Autowired
	UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private User user = new User();
	private List<League> lista = new ArrayList<League>();
	private	League liga = new League();
	
	@BeforeEach 
	void setup() throws DataAccessException, duplicatedLeagueNameException {

	
//		
		user.setUsername("antcammar4");
		user.setEnabled(true);
	
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    liga.setId(TEST_LEAGUE_ID);
	    liga.setLeagueCode(leagueService.randomString(10));
	    liga.setName("prueba");
	    liga.setLeagueDate(formatter.format(date));
	    liga.setMoto2Active(false);
	    liga.setMoto3Active(true);
	    liga.setMotogpActive(false);
	    liga.setRacesCompleted(0);
	    
		
		
		
		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		user.setPassword("test");
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);
		
		user.setAuthorities(auth);
		
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
		liga.setTeam(teams);
		
		this.leagueService.saveLeague(liga);
		this.leagueService.saveTeam(team);
		
		////		liga.setId(TEST_LEAGUE_ID);
//		liga.setLeagueCode("IOKASXISAU");
//		liga.setLeagueDate("15/10/2020");
//		liga.setMoto2Active(true);
//		liga.setMoto3Active(false);
//		liga.setMotogpActive(false);
//		liga.setName("liga");
//		liga.setRacesCompleted(2);
//		liga.setTeam(teams);

		
		lista.add(liga);
		
		this.userService.saveUser(user);
//		given(this.userService.getUserSession()).willReturn(user);
//	  	given(this.leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
//		given(this.userService.findUser(user.getUsername())).willReturn(Optional.of(user));
//		given(this.leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
//		given(this.userService.findUser("antcammar4")).willReturn(Optional.of(user));
//		given(this.leagueService.findTeamById(TEST_TEAM_ID)).willReturn(Optional.of(team));
//		given(this.leagueService.obtenerLigasPorUsuario(this.leagueService.findTeamsByUsername("antcammar4" ))).willReturn(lista);
//		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).will();

	}
	@WithMockUser(value = "spring")
	@Test
	void testShowLeagues() throws Exception {
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
//		given(leagueService.findAuthoritiesByUsername(this.userService.getUserSession().getUsername())).willReturn("admin");
		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
			.andExpect(view().name("/leagues/leagueList"))
			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
					       		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDontShowLeagues() throws Exception {
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(new ArrayList<League>());
		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
		.andExpect(model().attribute("ligas", is(new ArrayList<League>())))	
		.andExpect(view().name("/leagues/leagueList"));
					       		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowMyLeagues() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
		.andExpect(model().attribute("noTengoLigas", is(false)))
		.andExpect(model().attribute("misLigas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	

	@WithMockUser(value = "spring")
	@Test
	void testDontParticipatesInAnyLeagues() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(new ArrayList<League>());
		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
		.andExpect(model().attribute("noTengoLigas", is(true)))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAlreadyParticipatesInLeague() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
		mockMvc.perform(get("/leagues/myLeagues").flashAttr("yaTienesEquipo", true).flashAttr("leagueYaEquipoId", 1))
		.andExpect(model().attribute("yaTienesEquipo", is(true)))
		.andExpect(model().attribute("noTengoLigas", is(false)))
		.andExpect(model().attribute("leagueYaEquipoId", is(1)))
		.andExpect(view().name("leagues/myLeagues"));
		

		
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testIncreaseGPsInLeague() throws Exception {
//		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("admin");
//		given(leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
//		mockMvc.perform(get("/leagues/{leagueId}/increase" , TEST_LEAGUE_ID))
//		.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))))
//		.andExpect(view().name("leagues/myLeagues"));
//		
//	}
//		
	@WithMockUser(value = "spring")
	@Test
	void testNewLeague() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(1);
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
		.andExpect(model().attribute("league",hasProperty("id", is(liga.getId()+1))))
		.andExpect(view().name("/leagues/createLeagueName"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCantCreateNewLeague() throws Exception {
		given(this.userService.getUserSession()).willReturn(user);
		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(5);
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
		.andExpect(model().attribute("yaTieneMaxTeams",is(true)))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	

	
//	
//	@WithMockUser(value = "spring")
//    @Test
//	void testProcessCreationFormHasErrors() throws Exception {
//		mockMvc.perform(post("/leagues/{leagueId}/teams/new", TEST_LEAGUE_ID)
//							.with(csrf())
//							.param("name", "Betty")
//							.param("points", "aaa"))
//				.andExpect(model().attributeHasNoErrors("league"))
//				.andExpect(model().attributeHasErrors("team"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("leagues/TeamsEdit"));
//	}
//
//	  @WithMockUser(value = "spring")
//		@Test
//		void testInitUpdateForm() throws Exception {
//			mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID))
//					.andExpect(status().isOk()).andExpect(model().attributeExists("team"))
//					.andExpect(view().name("leagues/TeamsEdit"));
//		}
//	  
//	  @WithMockUser(value = "spring")
//		@Test
//		void testProcessUpdateFormSuccess() throws Exception {
//			mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID)
//								.with(csrf())
//								.param("name", "scudery")
//								.param("points", "1254")
//								.param("money", "30000"))
//					.andExpect(status().is3xxRedirection())
//					.andExpect(view().name("redirect:/leagues/{leagueId}/teams"));
//		}
//	  
//	  @WithMockUser(value = "spring")
//		@Test
//		void testProcessUpdateFormHasErrors() throws Exception {
//			mockMvc.perform(post("/leagues/{leagueId}/teams/{teamId}/edit", TEST_LEAGUE_ID, TEST_TEAM_ID)
//								.with(csrf())
//								.param("name", "@@@@")
//								.param("points", "aaaa"))
//					.andExpect(model().attributeHasNoErrors("league"))
//					.andExpect(model().attributeHasErrors("team")).andExpect(status().isOk())
//					.andExpect(view().name("leagues/TeamsEdit"));
//		}
//	  
//	  
//	  @WithMockUser(value = "spring")
//			@Test
//			void testDeleteTeam() throws Exception {
//				mockMvc.perform(get("/leagues/{leagueId}/teams/{teamId}/delete", TEST_LEAGUE_ID, TEST_TEAM_ID))
//						.andExpect(status().isOk()).andExpect(model().attributeExists("team"))
//						.andExpect(view().name("redirect:/leagues/{leagueId}/teams"));
//			}
	  
//	  @WithMockUser(value = "antcammar4")
//		@Test
//		void testLeagues() throws Exception {
//		 
//			mockMvc.perform(get("/leagues"))
//					.andExpect(status().isOk()).andExpect(model().attributeExists("ligas"))
//					.andExpect(view().name("/leagues/leagueList"));
//		}
	  
//	  
//	  @WithMockUser(value = "spring")
//	  @Test
//		void testMyLeaguesWhenNoLeagues() throws Exception {
//
//
//		  mockMvc.perform(get("/leagues/myLeagues"))
//					.andExpect(status().isOk()).andExpect(model().attributeExists("noTengoLigas"))
//					.andExpect(view().name("leagues/myLeagues"));
//		}
//	  
	  
//	    @WithMockUser(value = "antcammar4")
//		@Test
//		void testNewLeagueWhenUserNull() throws Exception {
//	    	mockMvc.perform(get("/leagues/new"))
//					.andExpect(view().name("/leagues/leagueList"));
//		}
//	  @WithMockUser(value = "spring")
//		@Test
//		void testJoinLeagueWhenUserNull() throws Exception {
//			mockMvc.perform(get("/leagues/join"))
//					.andExpect(view().name("/leagues/leagueList"));
//		}

}
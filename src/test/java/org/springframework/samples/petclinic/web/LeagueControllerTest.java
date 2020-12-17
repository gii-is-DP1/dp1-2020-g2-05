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
import java.util.Collection;
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
import org.springframework.samples.petclinic.model.Category;
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

		user.setUsername("antcammar4");
		user.setEnabled(true);
	
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    liga.setId(TEST_LEAGUE_ID);
	    liga.setLeagueCode("UDTQCSSOND");
	    liga.setName("prueba");
	    liga.setLeagueDate(formatter.format(date));
	    liga.setActiveCategory(Category.MOTO3);
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

		lista.add(liga);
		
		this.userService.saveUser(user);
		given(this.userService.getUserSession()).willReturn(user);

	}
	@WithMockUser(value = "spring")
	@Test
	void testShowLeagues() throws Exception {
		given(this.leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("admin");
		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
			.andExpect(view().name("/leagues/leagueList"))
			.andExpect(model().attribute("admin", is(true)))
			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
					       		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testLimitedShowLeagues() throws Exception {
		given(this.leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("user");
		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
			.andExpect(view().name("/leagues/leagueList"))
			.andExpect(model().attribute("user", is(true)))
			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
					       		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowMyLeagues() throws Exception {
		given(leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
		.andExpect(model().attribute("noTengoLigas", is(false)))
		.andExpect(model().attribute("misLigas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	

	@WithMockUser(value = "spring")
	@Test
	void testDontParticipatesInAnyLeagues() throws Exception {
		given(leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(new ArrayList<League>());
		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
		.andExpect(model().attribute("noTengoLigas", is(true)))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAlreadyParticipatesInLeague() throws Exception {
		given(leagueService.obtenerLigasPorUsuario(leagueService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
		mockMvc.perform(get("/leagues/myLeagues").flashAttr("yaTienesEquipo", true).flashAttr("leagueYaEquipoId", 1))
		.andExpect(status().isOk())
		.andExpect(model().attribute("yaTienesEquipo", is(true)))
		.andExpect(model().attribute("noTengoLigas", is(false)))
		.andExpect(model().attribute("leagueYaEquipoId", is(1)))
		.andExpect(view().name("leagues/myLeagues"));
		

		
	}

	@WithMockUser(value = "spring")
	@Test
	void testIncreaseGPsInLeague() throws Exception {
		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("admin");
		given(leagueService.findLeague(TEST_LEAGUE_ID)).willReturn(Optional.of(liga));
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		mockMvc.perform(get("/leagues/{leagueId}/increase" , TEST_LEAGUE_ID))
		.andExpect(status().is3xxRedirection())
//Comentar lo del null por la redireccion		.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))))
		.andExpect(view().name("redirect:/leagues"));

	}
		
	@WithMockUser(value = "spring")
	@Test
	void testCreateNewLeagueGet() throws Exception {
		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(1);
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
		.andExpect(model().attribute("league",hasProperty("id", is(liga.getId()+1))))
		.andExpect(view().name("/leagues/createLeagueName"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCantCreateNewLeague() throws Exception {
		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(5);
		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
		.andExpect(model().attribute("yaTieneMaxTeams",is(true)))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateNewLeaguePostSuccess() throws Exception {
		mockMvc.perform(post("/leagues/new")
				.with(csrf())
				.param("id", liga.getId().toString())
				.param("name", liga.getName())
				.param("leagueCode", liga.getLeagueCode())
				.param("leagueDate", liga.getLeagueDate())
				.param("racesCompleted", liga.getRacesCompleted().toString())
				.param("activeCategory", liga.getActiveCategory().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/leagues/"+liga.getId()+"/teams/new"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCreateNewLeaguePostHasErrors() throws Exception {
		mockMvc.perform(post("/leagues/new")
				.with(csrf())
				.param("id", liga.getId().toString())
				.param("name", "prueba!!!")
				.param("leagueCode", "1231as")
				.param("leagueDate", liga.getLeagueDate())
				.param("racesCompleted", "sad")
				.param("activeCategory", liga.getActiveCategory().toString()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("league"))
				.andExpect(model().attributeHasFieldErrors("league", "name"))
				.andExpect(model().attributeHasFieldErrors("league", "racesCompleted"))
				.andExpect(model().attributeHasFieldErrors("league", "leagueCode"))
				.andExpect(view().name("/leagues/createLeagueName"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testJoinNewLeagueGet() throws Exception {
		given(this.leagueService.findLeaguesByUsername(user.getUsername())).willReturn(1);
		mockMvc.perform(get("/leagues/join").flashAttr("noLeagueFound", false)).andExpect(status().isOk())
		.andExpect(model().attribute("league", hasProperty("name", Matchers.nullValue()))) 
		.andExpect(model().attribute("league", hasProperty("leagueCode", Matchers.nullValue()))) 
		.andExpect(model().attribute("league", hasProperty("leagueDate", Matchers.nullValue()))) 
		.andExpect(model().attribute("league", hasProperty("activeCategory", Matchers.nullValue()))) 
		.andExpect(model().attribute("league", hasProperty("racesCompleted", Matchers.nullValue()))) 
		.andExpect(view().name("/leagues/createLeague"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCantJoinNewLeagueGet() throws Exception {
		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(5);
		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
		.andExpect(model().attribute("yaTieneMaxTeams", true))
		.andExpect(view().name("leagues/myLeagues"));
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testJoinAlreadyTeamInLeaguePost() throws Exception {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(liga.getId());
		given(this.leagueService.findTeamsByUsername(user.getUsername())).willReturn(lista);
		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
		given(this.leagueService.findTeamsByLeagueId(liga.getId())).willReturn(1);
		mockMvc.perform(post("/leagues/join")
				.with(csrf())
				.param("id", liga.getId().toString())
				.param("name", liga.getName())
				.param("leagueCode", liga.getLeagueCode())
				.param("leagueDate", liga.getLeagueDate())
				.param("racesCompleted", liga.getRacesCompleted().toString())
				.param("activeCategory", liga.getActiveCategory().toString()))
				.andExpect(model().attribute("yaTienesEquipo", true))
				.andExpect(model().attribute("leagueYaEquipoId", liga.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("leagues/myLeagues"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testJoinNoLeagueFoundPost() throws Exception {
		List<Integer> lista = new ArrayList<Integer>();
		given(this.leagueService.findTeamsByUsername(user.getUsername())).willReturn(lista);
		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.empty());
		mockMvc.perform(post("/leagues/join")
				.with(csrf())
				.param("id", liga.getId().toString())
				.param("name", liga.getName())
				.param("leagueCode", liga.getLeagueCode())
				.param("leagueDate", liga.getLeagueDate())
				.param("racesCompleted", liga.getRacesCompleted().toString())
				.param("activeCategory", liga.getActiveCategory().toString()))
				.andExpect(model().attribute("noLeagueFound", "Any league has been found with the code provided :("))
				.andExpect(status().isOk())
				.andExpect(view().name("/leagues/createLeague"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testJoinToFullLeaguePost() throws Exception {
		List<Integer> lista = new ArrayList<Integer>();
		given(this.leagueService.findTeamsByUsername(user.getUsername())).willReturn(lista);
		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
		given(this.leagueService.findTeamsByLeagueId(liga.getId())).willReturn(6);
		mockMvc.perform(post("/leagues/join")
				.with(csrf())
				.param("id", liga.getId().toString())
				.param("name", liga.getName())
				.param("leagueCode", liga.getLeagueCode())
				.param("leagueDate", liga.getLeagueDate())
				.param("racesCompleted", liga.getRacesCompleted().toString())
				.param("activeCategory", liga.getActiveCategory().toString()))
				.andExpect(model().attribute("yaTieneMaxTeams", true))
				.andExpect(status().isOk())
				.andExpect(view().name("leagues/myLeagues"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testJoinToLeaguePost() throws Exception {
		List<Integer> lista = new ArrayList<Integer>();
		given(this.leagueService.findTeamsByUsername(user.getUsername())).willReturn(lista);
		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
		given(this.leagueService.findTeamsByLeagueId(liga.getId())).willReturn(1);
		mockMvc.perform(post("/leagues/join")
				.with(csrf())
				.param("id", liga.getId().toString())
				.param("name", liga.getName())
				.param("leagueCode", liga.getLeagueCode())
				.param("leagueDate", liga.getLeagueDate())
				.param("racesCompleted", liga.getRacesCompleted().toString())
				.param("activeCategory", liga.getActiveCategory().toString()))
//				.andExpect(model().attribute("yaTienesEquipo", false))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/leagues/"+liga.getId()+"/teams/new"));
	}
}
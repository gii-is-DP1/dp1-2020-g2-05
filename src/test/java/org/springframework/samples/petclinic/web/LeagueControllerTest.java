package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
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
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.LeagueHasMaximumNumberOfTeamsException;
import org.springframework.samples.petclinic.service.exceptions.MaximumNumberOfLeaguesPerUserException;
import org.springframework.samples.petclinic.service.exceptions.NoLeagueFoundException;
import org.springframework.samples.petclinic.service.exceptions.YouAlreadyParticipateInALeagueException;
import org.springframework.samples.petclinic.service.exceptions.duplicatedLeagueNameException;
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
    	TablaConsultasService TCService;
    	
    	@MockBean
    	TeamController teamController;

    	@MockBean
    	LeagueService leagueService;
    	
    	@MockBean
    	UserService userService;
    	
    	@MockBean
    	TeamService teamService;
    	
    	@MockBean
    	private TransactionService transactionService;
    	
    	@MockBean
    	private AuthoritiesService authoritiesService;
    	
    	@Autowired
    	private MockMvc mockMvc;
    	
    	
    	private User user = new User();
    	private List<League> lista = new ArrayList<League>();
    	private	League liga = new League();
    	TablaConsultas TCConsulta = new TablaConsultas();

    	@BeforeEach 
    	void setup(TestInfo testInfo) throws DataAccessException, duplicatedLeagueNameException {
    		if(testInfo.getTags().contains("SkipCleanup")) {
                return;
            }
    		TCConsulta.setCurrentCategory(Category.MOTO3);
    		TCConsulta.setRacesCompleted(0);
    		user.setUsername("antcammar4");
    		user.setEnabled(true);
    	
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
    	    Date date = new Date();  
    	    liga.setId(TEST_LEAGUE_ID);
    	    liga.setLeagueCode("UDTQCSSOND");
    	    liga.setName("prueba");
    	    liga.setLeagueDate(formatter.format(date));
    	   
    	    

    		
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
    		team.setMoney(2);
    		team.setName("teamtest");
    		team.setPoints(132);
    		team.setUser(user);
    		teams.add(team);
    		user.setTeam(teams);
    		liga.setTeam(teams);
    		
    		this.leagueService.saveLeague(liga);
    		this.teamService.saveTeam(team);

    		lista.add(liga);
    		
    		this.userService.saveUser(user);
    		given(this.userService.getUserSession()).willReturn(user);

    	}
    	@WithMockUser(value = "spring")
    	@Test
    	void testShowLeagues() throws Exception {
    		given(leagueService.findAll()).willReturn(lista);
    		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("admin");
    		given( this.TCService.getTabla()).willReturn(Optional.of(this.TCConsulta));

    		mockMvc.perform(get("/leagues").flashAttr("categoriaActual", Category.MOTO3)).andExpect(status().isOk())
    			.andExpect(view().name("leagues/leagueList"))
    			.andExpect(model().attribute("carrerasCompletadas", is(0)))
    			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
    					       		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testLimitedShowLeagues() throws Exception {
    		given(leagueService.findAll()).willReturn(lista);
    		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("user");
    		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
    		
    		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
    			.andExpect(view().name("leagues/leagueList"))
    			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
    					       		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testShowMyLeagues() throws Exception {
    		given(leagueService.obtenerListaDeLigasDeUnaListaDeIntegers(teamService.findTeamsIntByUsername(user.getUsername()))).willReturn(lista);
    		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
    		.andExpect(model().attribute("misLigas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))))
    		.andExpect(view().name("leagues/myLeagues"));
    		
    	}
    	

    	@WithMockUser(value = "spring")
    	@Test
    	void testDontParticipatesInAnyLeagues() throws Exception {
    		given(leagueService.obtenerListaDeLigasDeUnaListaDeIntegers(teamService.findTeamsIntByUsername(user.getUsername()))).willReturn(new ArrayList<League>());
    		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
    		.andExpect(model().attribute("noTengoLigas", is(true)))
    		.andExpect(view().name("leagues/myLeagues"));
    		
    	}
    
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testAlreadyParticipatesInLeague() throws Exception{
    		List<Integer> lista = new ArrayList<Integer>();
    		lista.add(TEST_LEAGUE_ID);
    		given(this.teamService.findTeamsIntByUsername(user.getUsername())).willReturn(lista);
    		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
    		given(this.teamService.findTeamsIntByUsername(user.getUsername())).willReturn(lista);
    		mockMvc.perform(post("/leagues/join")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", liga.getName())
    				.param("leagueCode", liga.getLeagueCode())
    				.param("leagueDate", liga.getLeagueDate()))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(result -> assertTrue(result.getResolvedException() instanceof YouAlreadyParticipateInALeagueException))
    				.andExpect(view().name("redirect:/leagues/join"));
    	}

    	@WithMockUser(value = "spring")
    	@Test
    	void testIncreaseGPsInLeague() throws Exception {
    		mockMvc.perform(get("/leagues/increase/{category}" , "MOTO3"))
    		.andExpect(status().is3xxRedirection())
    		.andExpect(view().name("redirect:/leagues"));

    	}
    		
    	@WithMockUser(value = "spring")
    	@Test
    	void testCreateNewLeagueGet() throws Exception {
    		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(1);
    		given(leagueService.findAll()).willReturn(lista);
    		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
    		.andExpect(model().attribute("league",hasProperty("leagueDate", is(liga.getLeagueDate()))))
    		.andExpect(view().name("/leagues/createLeagueName"));
    		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testCantCreateNewLeague() throws Exception {
    		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(5);
    		given(leagueService.findAll()).willReturn(lista);
    		mockMvc.perform(get("/leagues/new")).andExpect(status().is3xxRedirection())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MaximumNumberOfLeaguesPerUserException))
    		.andExpect(view().name("redirect:/leagues/myLeagues"));
    		
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
    				.param("racesCompleted", TCConsulta.getRacesCompleted().toString())
    				.param("activeCategory", TCConsulta.getCurrentCategory().toString()))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(view().name("redirect:/leagues/myLeagues"));
    	}

    	@WithMockUser(value = "spring")
    	@Test
    	void testCreateNewLeaguePostHasErrors() throws Exception {
    		mockMvc.perform(post("/leagues/new")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", "prueba!!!")
    				.param("leagueCode", "1231as")
    				.param("leagueDate", liga.getLeagueDate()))
    				.andExpect(status().isOk())
    				.andExpect(model().attributeHasErrors("league"))
    				.andExpect(model().attributeHasFieldErrors("league", "name"))
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
    		.andExpect(view().name("/leagues/createLeague"));
    		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testCantJoinNewLeagueGet() throws Exception {
    		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(5);
    		mockMvc.perform(get("/leagues/join")).andExpect(status().is3xxRedirection())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MaximumNumberOfLeaguesPerUserException))
    		.andExpect(view().name("redirect:/leagues/myLeagues"));
    		
    	}
    	
    	
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinAlreadyTeamInLeaguePost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		lista.add(liga.getId());
    		given(this.teamService.findTeamsIntByUsername(user.getUsername())).willReturn(lista);
    		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
    		given(this.teamService.findTeamsByLeagueId(liga.getId())).willReturn(1);
    		mockMvc.perform(post("/leagues/join")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", liga.getName())
    				.param("leagueCode", liga.getLeagueCode())
    				.param("leagueDate", liga.getLeagueDate())
    				.param("racesCompleted", TCConsulta.getRacesCompleted().toString())
    				.param("activeCategory", TCConsulta.getCurrentCategory().toString()))
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof YouAlreadyParticipateInALeagueException))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(view().name("redirect:/leagues/join"));
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinNoLeagueFoundPost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		given(this.teamService.findTeamsIntByUsername(user.getUsername())).willReturn(lista);
    		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.empty());
    		mockMvc.perform(post("/leagues/join")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", liga.getName())
    				.param("leagueCode", liga.getLeagueCode())
    				.param("leagueDate", liga.getLeagueDate())
    				.param("racesCompleted", TCConsulta.getRacesCompleted().toString())
    				.param("activeCategory", TCConsulta.getCurrentCategory().toString()))
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoLeagueFoundException))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(view().name("redirect:/leagues/join"));
    	}

    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinToFullLeaguePost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		given(this.teamService.findTeamsIntByUsername(user.getUsername())).willReturn(lista);
    		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
    		given(this.teamService.findTeamsByLeagueId(liga.getId())).willReturn(6);
    		mockMvc.perform(post("/leagues/join")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", liga.getName())
    				.param("leagueCode", liga.getLeagueCode())
    				.param("leagueDate", liga.getLeagueDate())
    				.param("racesCompleted", TCConsulta.getRacesCompleted().toString())
    				.param("activeCategory", TCConsulta.getCurrentCategory().toString()))
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof LeagueHasMaximumNumberOfTeamsException))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(view().name("redirect:/leagues/join"));
    	}
    	
    	@Test		
    	@WithMockUser(value = "spring")
    	void testNewLeaguePostTrhowException() throws Exception {
    		when(leagueService.saveLeague(any())).thenThrow(duplicatedLeagueNameException.class);
    		mockMvc.perform(post("/leagues/new")
    			.with(csrf())
    			.param("id", liga.getId().toString())
    			.param("name", liga.getName())
    			.param("leagueCode", liga.getLeagueCode())
    			.param("leagueDate", liga.getLeagueDate())
    			)
    			.andExpect(status().is3xxRedirection())
    			.andExpect(view().name("redirect:/leagues/new"));
    }

    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinToLeaguePost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		given(this.teamService.findTeamsIntByUsername(user.getUsername())).willReturn(lista);
    		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.of(liga));
    		given(this.teamService.findTeamsByLeagueId(liga.getId())).willReturn(1);
    		mockMvc.perform(post("/leagues/join")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", liga.getName())
    				.param("leagueCode", liga.getLeagueCode())
    				.param("leagueDate", liga.getLeagueDate())
    				.param("racesCompleted", TCConsulta.getRacesCompleted().toString())
    				.param("activeCategory", TCConsulta.getCurrentCategory().toString()))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(view().name("redirect:/leagues/"+liga.getId()+"/teams/new"));
    	}
    	



	 }
	
		

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

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Any;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.hamcrest.collection.HasItemInArray;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.TablaConsultasRepository;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.LineupService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doThrow;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;




@WebMvcTest(controllers=LeagueController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class LeagueControllerTest {
	 @Nested
	 @WebMvcTest(controllers=LeagueController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
	    class TestNest{
    	private static final int TEST_LEAGUE_ID = 1;

    	private static final int TEST_TEAM_ID = 1;
    	
    	@MockBean
    	@Autowired
    	TablaConsultasService TCService;
    	
    	@MockBean
    	@Autowired	
    	TeamController teamController;

    	
    	@MockBean
    	@Autowired	
    	LeagueService leagueService;
    	
    	@MockBean
    	@Autowired
    	UserService userService;
    	
    	@MockBean
    	@Autowired
    	TeamService teamService;
    	
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
    		given(this.leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
    		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("admin");
    		given( this.TCService.getTabla()).willReturn(Optional.of(this.TCConsulta));
//    		given( this.TCService.getTabla().get().getCurrentCategory()).willReturn(Category.MOTO2);
//    		given( this.TCService.getTabla().get().getRacesCompleted()).willReturn(0);
    		
    		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
    			.andExpect(view().name("leagues/leagueList"))
    			.andExpect(model().attribute("admin", is(true)))
    			.andExpect(model().attribute("categoriaActual", is(Category.MOTO3)))
    			.andExpect(model().attribute("carrerasCompletadas", is(0)))
    			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
    					       		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testLimitedShowLeagues() throws Exception {
    		given(this.leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
    		given(this.leagueService.findAuthoritiesByUsername(user.getUsername())).willReturn("user");
    		given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
    		
    		mockMvc.perform(get("/leagues")).andExpect(status().isOk())
    			.andExpect(view().name("leagues/leagueList"))
    			.andExpect(model().attribute("user", is(true)))
    			.andExpect(model().attribute("ligas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))));
    					       		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testShowMyLeagues() throws Exception {
    		given(leagueService.obtenerListaIntegerToTeams(teamService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
    		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
    		.andExpect(model().attribute("noTengoLigas", is(false)))
    		.andExpect(model().attribute("misLigas", Matchers.hasItem(Matchers.<League> hasProperty("leagueCode", is(liga.getLeagueCode())))))
    		.andExpect(view().name("leagues/myLeagues"));
    		
    	}
    	

    	@WithMockUser(value = "spring")
    	@Test
    	void testDontParticipatesInAnyLeagues() throws Exception {
    		given(leagueService.obtenerListaIntegerToTeams(teamService.findTeamsByUsername(user.getUsername()))).willReturn(new ArrayList<League>());
    		mockMvc.perform(get("/leagues/myLeagues")).andExpect(status().isOk())
    		.andExpect(model().attribute("noTengoLigas", is(true)))
    		.andExpect(view().name("leagues/myLeagues"));
    		
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testAlreadyParticipatesInLeague() throws Exception {
    		given(leagueService.obtenerListaIntegerToTeams(teamService.findTeamsByUsername(user.getUsername()))).willReturn(lista);
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
    		mockMvc.perform(get("/leagues//increase" , TEST_LEAGUE_ID).flashAttr("category", "MOTO3"))
    		.andExpect(status().is3xxRedirection())
    		.andExpect(view().name("redirect:/leagues"));

    	}
    		
    	@WithMockUser(value = "spring")
    	@Test
    	void testCreateNewLeagueGet() throws Exception {
    		given(leagueService.findLeaguesByUsername(user.getUsername())).willReturn(1);
    		given(leagueService.convertirIterableLista(leagueService.findAll())).willReturn(lista);
    		mockMvc.perform(get("/leagues/new")).andExpect(status().isOk())
    		.andExpect(model().attribute("league",hasProperty("leagueDate", is(liga.getLeagueDate()))))
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
    		mockMvc.perform(get("/leagues/join")).andExpect(status().isOk())
    		.andExpect(model().attribute("yaTieneMaxTeams", true))
    		.andExpect(view().name("leagues/myLeagues"));
    		
    	}
    	
    	
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinAlreadyTeamInLeaguePost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		lista.add(liga.getId());
    		given(this.teamService.findTeamsByUsername(user.getUsername())).willReturn(lista);
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
    				.andExpect(model().attribute("yaTienesEquipo", true))
    				.andExpect(model().attribute("leagueYaEquipoId", liga.getId()))
    				.andExpect(status().isOk())
    				.andExpect(view().name("leagues/myLeagues"));
    	}
    	
    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinNoLeagueFoundPost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		given(this.teamService.findTeamsByUsername(user.getUsername())).willReturn(lista);
    		given(this.leagueService.findLeagueByLeagueCode(liga.getLeagueCode())).willReturn(Optional.empty());
    		mockMvc.perform(post("/leagues/join")
    				.with(csrf())
    				.param("id", liga.getId().toString())
    				.param("name", liga.getName())
    				.param("leagueCode", liga.getLeagueCode())
    				.param("leagueDate", liga.getLeagueDate())
    				.param("racesCompleted", TCConsulta.getRacesCompleted().toString())
    				.param("activeCategory", TCConsulta.getCurrentCategory().toString()))
    				.andExpect(model().attribute("noLeagueFound", "Any league has been found with the code provided :("))
    				.andExpect(status().isOk())
    				.andExpect(view().name("/leagues/createLeague"));
    	}

    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinToFullLeaguePost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		given(this.teamService.findTeamsByUsername(user.getUsername())).willReturn(lista);
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
    				.andExpect(model().attribute("yaTieneMaxTeams", true))
    				.andExpect(status().isOk())
    				.andExpect(view().name("leagues/myLeagues"));
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
    			.andExpect(model().attributeHasErrors("league"))
    		    .andExpect(model().attributeHasFieldErrors("league", "name"))			
    			.andExpect(view().name("/leagues/createLeagueName"))
    			.andExpect(status().isOk());
    }

    	@WithMockUser(value = "spring")
    	@Test
    	void testJoinToLeaguePost() throws Exception {
    		List<Integer> lista = new ArrayList<Integer>();
    		given(this.teamService.findTeamsByUsername(user.getUsername())).willReturn(lista);
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
//    				.andExpect(model().attribute("yaTienesEquipo", false))
    				.andExpect(status().is3xxRedirection())
    				.andExpect(view().name("redirect:/leagues/"+liga.getId()+"/teams/new"));
    	}
    	



	 }
	 @MockBean
 	@Autowired
 	TablaConsultasService TCService;
 	
 	@MockBean
 	@Autowired	
 	TeamController teamController;

 	
 	@MockBean
 	@Autowired	
 	LeagueService leagueService;
 	
 	@MockBean
 	@Autowired
 	UserService userService;
 	
 	@MockBean
 	@Autowired
 	TeamService teamService;
 	
 	@Autowired
 	private MockMvc mockMvc;
 	
	 @WithMockUser(value = "spring")
		@Test
		void testJoinNoUserFound() throws Exception {
			mockMvc.perform(get("/leagues/join")
					.with(csrf()))
					.andExpect(status().isOk())
					.andExpect(view().name("/leagues/leagueList"));
		}
		
		
}
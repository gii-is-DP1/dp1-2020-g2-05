package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;


import org.apache.taglibs.standard.tag.common.fmt.SetBundleSupport;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;

@WebMvcTest(controllers = GranPremioController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class GPControllerTest {
	

	@MockBean
	@Autowired
	private GranPremioService GPService;
	
	@MockBean
	@Autowired
	private TablaConsultasService TCService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	private GranPremio gp = new GranPremio();
	private GranPremio gpvacio = new GranPremio();
	private List<GranPremio> listagps = new ArrayList<GranPremio>();
	private TablaConsultas TCConsulta = new TablaConsultas();
	private User user = new User();
	
	@BeforeEach
	void setup() {
		
		gp.setId(1);
		gp.setCalendar(true);
		gp.setCircuit("Losail");
		gp.setDate0(LocalDate.of(2020, 3, 8));
		gp.setHasBeenRun(false);
		gp.setRaceCode("QAT");
		gp.setSite("Grand Prix Of Qatar");
		listagps.add(gp);
		
		TCConsulta.setCurrentCategory(Category.MOTO3);
		TCConsulta.setRacesCompleted(0);
		TCConsulta.setActualRace(1);
		user.setUsername("antcammar4");
		user.setEnabled(true);
	
		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		user.setPassword("test");
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);
		
		user.setAuthorities(auth);
		
	}
	
@WithMockUser(value = "spring")
@Test
void testDetallesLiga() throws Exception {
	given(this.TCService.getTabla()).willReturn(Optional.of(TCConsulta));
	given(GPService.findAllActualYear(2020)).willReturn(listagps);

	mockMvc.perform(get("/granPremios"))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("racesCompleted", is(0)))
			.andExpect(model().attribute("listaGP", is(listagps)))
			.andExpect(view().name("/gp/gpList"));
}	
	

//@WithMockUser(value = "spring")
//@Test
//void testCrearGranPremio() throws Exception {
//given( new GranPremio()).willReturn(gpvacio);
//
//	mockMvc.perform(get("/granPremios/new"))	
//			.andExpect(status().isOk())
//			.andExpect(model().attribute("GranPremio", is(gpvacio)))
//			.andExpect(view().name("/gp/nuevoGP"));
//}

@WithMockUser(value = "spring")
@Test
void testCrearGranPremio2SinErrores() throws Exception {
	mockMvc.perform(post("/granPremios/new") 
			.with(csrf())	
			.param("site",gp.getSite())
			.param("date0",gp.getDate0().toString())
			.param("circuit",gp.getCircuit())
			.param("raceCode",gp.getRaceCode())
			.param("hasBeenRun",gp.getHasBeenRun().toString())
			.param("calendar",gp.getCalendar().toString()))
			.andExpect(status().is3xxRedirection())		
			.andExpect(view().name("redirect:/controlPanel"));
}

@WithMockUser(value = "spring")
@Test
void testCrearGranPremio2ConErrores() throws Exception {
	
	//El error es que no se le pasa raceCode, por lo que es null y no puede ser.
	mockMvc.perform(post("/granPremios/new") 
			.with(csrf())	
			.param("site",gp.getSite())
			.param("date0",gp.getDate0().toString())
			.param("circuit",gp.getCircuit())
			.param("hasBeenRun",gp.getHasBeenRun().toString())
			.param("calendar",gp.getCalendar().toString()))
			.andExpect(status().isOk())		
			.andExpect(view().name("/gp/nuevoGP"));
}

	
//@WithMockUser(value = "spring")
//@Test
//void testListadoAmigos() throws Exception {
////	given( userService.findFriendByUser(user.getUsername())).willReturn(lista);
//	mockMvc.perform(get("/friends")).andExpect(status().isOk())	
////	.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<User> hasProperty("username", is(amigo1.getUsername())))))
//	.andExpect(view().name("friends/friendsList"));
//}









}

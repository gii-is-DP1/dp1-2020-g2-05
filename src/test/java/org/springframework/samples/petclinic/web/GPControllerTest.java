package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
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
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	private static final Integer TEST_GP_ID = 1;

	@MockBean
	@Autowired
	private GranPremioService GPService;
	
	@MockBean
	@Autowired
	private TablaConsultasService TCService;
	
	@Autowired
	private MockMvc mockMvc;
	
	

	private GranPremio gp = new GranPremio();

	
	@BeforeEach
	void setup() {
		
		gp.setId(TEST_GP_ID);
		gp.setCalendar(true);
		gp.setCircuit("Losail");
		gp.setDate0(LocalDate.of(2020, 3, 8));
		gp.setHasBeenRun(false);
		gp.setRaceCode("QAT");
		gp.setSite("Grand Prix Of Qatar");
		
	}
	

@WithMockUser(value = "spring")
@Test
void testCrearUser() throws Exception {
	mockMvc.perform(get("/users/new"))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("userr", is(new User())))
			.andExpect(view().name("users/createUserForm"));
}

@WithMockUser(value = "spring")
@Test
void testCrearUser2SinErrores() throws Exception {
	mockMvc.perform(post("/users/new") 
			.with(csrf())	
	
			.param("enabled","True"))
			.andExpect(status().is3xxRedirection())		
			.andExpect(view().name("redirect:/"));
}

	
@WithMockUser(value = "spring")
@Test
void testListadoAmigos() throws Exception {
//	given( userService.findFriendByUser(user.getUsername())).willReturn(lista);
	mockMvc.perform(get("/friends")).andExpect(status().isOk())	
//	.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<User> hasProperty("username", is(amigo1.getUsername())))))
	.andExpect(view().name("friends/friendsList"));
}






//@RequestMapping(path="/granPremios/setRecords/{gpId}")
//public String populateRecords(@PathVariable("gpId") int gpId, ModelMap model) throws IOException {
//	GranPremio gp = this.GPService.findGPById(gpId).get();
//	this.GPService.populateRecord(gp);
//	this.GPService.saveGP(gp);
//	return "gp/gpList";
//}

//@GetMapping(path="/granPremios/{id}/delete")
//public String eliminarGranPremio(@PathVariable("id") String id,ModelMap model) {	
//	this.GPService.delete(this.GPService.findGPById(Integer.parseInt(id)).get());
//	return "redirect:/controlPanel";
//}

@WithMockUser(value = "spring")
@Test
void testDeleteGP() throws Exception {
	given(GPService.findGPById(TEST_GP_ID)).willReturn(Optional.of(gp));
	
	mockMvc.perform(get("/granPremios/{id}/delete", TEST_GP_ID))
			.andExpect(status().is3xxRedirection())
//			.andExpect(model().attribute("message", is("Lineup successfully deleted!"))) --> Hace falta ponerlo como RedirectAttribute
			.andExpect(view().name("redirect:/controlPanel"));
}

@WithMockUser(value = "spring")
@Test
void testDeleteNonExistingGP() throws Exception {
	given(GPService.findGPById(TEST_GP_ID)).willReturn(Optional.empty());
	
	mockMvc.perform(get("/granPremios/{id}/delete", TEST_GP_ID))
			.andExpect(status().is3xxRedirection())
//			.andExpect(model().attribute("message", is("Lineup not found!"))) --> Hace falta ponerlo como RedirectAttribute
			.andExpect(view().name("redirect:/controlPanel"));
}

}

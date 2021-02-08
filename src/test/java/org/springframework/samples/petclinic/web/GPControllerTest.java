package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.GranPremio;
import org.springframework.samples.petclinic.model.TablaConsultas;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GranPremioService;
import org.springframework.samples.petclinic.service.PoblarBaseDeDatosService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GranPremioController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class GPControllerTest {

	private static final Integer TEST_GP_ID = 1;

	@MockBean
	private GranPremioService GPService;

	@MockBean
	private TablaConsultasService TCService;
	
	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private PoblarBaseDeDatosService PBDService;
	
	@Autowired
	private MockMvc mockMvc;

	private GranPremio gp = new GranPremio();
	private List<GranPremio> listagps = new ArrayList<GranPremio>();
	private TablaConsultas TCConsulta = new TablaConsultas();
	private User user = new User();

	@BeforeEach
	void setup() {

		gp.setId(TEST_GP_ID);
		gp.setCalendar(true);
		gp.setCircuit("Losail");
		gp.setDate0(LocalDate.of(2020, 3, 8));
		gp.setHasBeenRun(false);
		gp.setRaceCode("QAT");
		gp.setSite("Grand Prix Of Qatar");

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
		.andExpect(model().attribute("listaGP", is(listagps)))
		.andExpect(view().name("gp/gpList"));
	}	


	@WithMockUser(value = "spring")
	@Test
	void testCrearGranPremio() throws Exception {

		mockMvc.perform(get("/granPremios/new"))	
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("GranPremio"))
		.andExpect(view().name("gp/nuevoGP"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearGranPremio2SinErrores() throws Exception {
		mockMvc.perform(post("/granPremios/new") 
				.with(csrf())	
				.param("site", gp.getSite())
				.param("date0", gp.getDate0().toString())
				.param("circuit", gp.getCircuit())
				.param("raceCode", gp.getRaceCode())
				.param("hasBeenRun", gp.getHasBeenRun().toString())
				.param("calendar", gp.getCalendar().toString()))
		.andExpect(status().is3xxRedirection())		
		.andExpect(view().name("redirect:/controlPanel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearGranPremio2ConErrores() throws Exception {
		mockMvc.perform(post("/granPremios/new") 
				.with(csrf())	
				.param("site", gp.getSite())
				.param("date0", gp.getDate0().toString())
				.param("circuit", gp.getCircuit())
				.param("hasBeenRun", gp.getHasBeenRun().toString())
				.param("calendar", gp.getCalendar().toString()))
		.andExpect(status().isOk())		
		.andExpect(model().hasErrors()) // El error es que no se le pasa raceCode, por lo que es null y no puede ser.
		.andExpect(view().name("gp/nuevoGP"));
	}




	@WithMockUser(value = "spring")
	@Test
	void testDeleteGP() throws Exception {
		given(GPService.findGPById(TEST_GP_ID)).willReturn(Optional.of(gp));

		mockMvc.perform(get("/granPremios/{id}/delete", TEST_GP_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("GP successfully deleted!")))
		.andExpect(view().name("redirect:/controlPanelSP"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteNonExistingGP() throws Exception {
		given(GPService.findGPById(TEST_GP_ID)).willReturn(Optional.empty());

		mockMvc.perform(get("/granPremios/{id}/delete", TEST_GP_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("message", is("GP not found!")))
		.andExpect(view().name("redirect:/controlPanelSP"));
	}
}

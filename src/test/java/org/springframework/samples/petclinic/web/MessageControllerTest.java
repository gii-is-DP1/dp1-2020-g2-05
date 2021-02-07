package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.PoblarBaseDeDatosService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MessageController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class MessageControllerTest {
	private static final int TEST_MSG_ID = 1;

	@MockBean
	@Autowired
	private UserService userService;

	@MockBean
	private TablaConsultasService tablaConsultas;

	@MockBean
	@Autowired
	private MessageService messageService;
	
	@MockBean
	@Autowired
	private PoblarBaseDeDatosService poblarBaseDeDatosService;
	
	@MockBean
	@Autowired
	private TransactionService transactionService;
	
	@MockBean
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private MockMvc mockMvc;

	private org.springframework.samples.petclinic.model.User userrec = new org.springframework.samples.petclinic.model.User();
	private org.springframework.samples.petclinic.model.User user = new org.springframework.samples.petclinic.model.User();
	private Message message = new Message();
	private Message messagenew = new Message();
	private Message messagelimpio = new Message();

	private Message messagenewpred = new Message();
	private List<Message> lista = new ArrayList<Message>();

	@BeforeEach
	void setup() {

		user.setUsername("serrojjim");
		user.setEnabled(true);

		userrec.setUsername("Antonio");
		user.setEnabled(true);

		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		user.setPassword("test");
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);

		user.setAuthorities(auth);
		userrec.setAuthorities(auth);

		// Mensaje con errores
		message.setId(1);
		message.setUsernamesend(user);
		message.setUsernamereceive(user);
		message.setVisto(0);
		message.setAsunto("PRUEBA");
		message.setCuerpo("PRUEBA CUERPO");

		// Mensaje sin errores
		messagenew.setId(2);
		messagenew.setUsernamesend(user);
		messagenew.setUsernamereceive(userrec);
		messagenew.setVisto(0);
		messagenew.setAsunto("PRUEBA");
		messagenew.setCuerpo("PRUEBA CUERPO");

		// Mensaje que manda al form el controller
		messagelimpio.setId(4);
		messagelimpio.setVisto(0);
		messagelimpio.setUsernamesend(user);

		messagenewpred.setId(3);
		messagenewpred.setUsernamesend(user);
		messagenewpred.setUsernamereceive(userrec);
		messagenewpred.setVisto(0);

		lista.add(message);
		this.userService.saveUser(user);
		this.userService.saveUser(userrec);
		this.messageService.saveMessage(message);
		this.messageService.saveMessage(messagenew);
		this.messageService.saveMessage(messagenewpred);
		given(this.userService.getUserSession()).willReturn(user);

	}

	@WithMockUser(value = "spring")
	@Test
	void testListadoMensajes() throws Exception {
		given(messageService.findAllUsernameReceive(user.getUsername())).willReturn(lista);
		mockMvc.perform(get("/messages")).andExpect(status().isOk())
				.andExpect(model().attribute("resultados",
						Matchers.hasItem(Matchers.<Message>hasProperty("asunto", is(message.getAsunto())))))
				.andExpect(view().name("messages/messagesList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testBorrarMensaje() throws Exception {
		given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.of(message));
		mockMvc.perform(get("/messages/delete/{messageId}", TEST_MSG_ID)).andExpect(status().is3xxRedirection())
				.andExpect(model().attribute("message", is("message successfully deleted!")))
				.andExpect(view().name("redirect:/messages"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testBorrarMensajeFallido() throws Exception {
		given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.empty());
		mockMvc.perform(get("/messages/delete/{messageId}", TEST_MSG_ID)).andExpect(status().is3xxRedirection())
				.andExpect(model().attribute("message", is("message not found!")))
				.andExpect(view().name("redirect:/messages"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearMensaje() throws Exception {
		mockMvc.perform(get("/messages/new")).andExpect(status().isOk())
				.andExpect(model().attribute("messagee", is(messagelimpio)))
				.andExpect(model().attribute("usersend", is(user))).andExpect(view().name("messages/messagesEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearMensaje2SinErrores() throws Exception {
		mockMvc.perform(post("/messages/new").with(csrf()).param("asunto", messagenew.getAsunto())
				.param("cuerpo", messagenew.getCuerpo()).param("id", messagenew.getId().toString())
				.param("usernamesend.username", messagenew.getUsernamesend().getUsername())
				.param("usernamesend.password", messagenew.getUsernamesend().getPassword())
				.param("usernamesend.email", messagenew.getUsernamesend().getEmail())
				.param("usernamesend.enabled", "True")

				.param("usernamereceive.username", messagenew.getUsernamereceive().getUsername())
				.param("usernamereceive.password", messagenew.getUsernamereceive().getPassword())
				.param("usernamereceive.email", messagenew.getUsernamereceive().getEmail())
				.param("usernamereceive.enabled", "True").param("visto", "0")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/messages/"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearMensaje2ConErrores() throws Exception {

		// El error es que no te puedes enviar un mensaje a ti mismo
		mockMvc.perform(post("/messages/new").with(csrf()).param("asunto", message.getAsunto())
				.param("cuerpo", message.getCuerpo()).param("id", message.getId().toString())
				.param("usernamesend.username", message.getUsernamesend().getUsername())
				.param("usernamesend.password", message.getUsernamesend().getPassword())
				.param("usernamesend.email", message.getUsernamesend().getEmail()).param("usernamesend.enabled", "True")

				.param("usernamereceive.username", message.getUsernamereceive().getUsername())
				.param("usernamereceive.password", message.getUsernamereceive().getPassword())
				.param("usernamereceive.email", message.getUsernamereceive().getEmail())
				.param("usernamereceive.enabled", "True")

				.param("visto", "0")).andExpect(status().isOk()).andExpect(view().name("messages/messagesEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearMensajePredefinido() throws Exception {
		given(userService.findUser(userrec.getUsername())).willReturn(Optional.of(userrec));
		mockMvc.perform(get("/messages/new/{username}", userrec.getUsername())).andExpect(status().isOk())
				.andExpect(model().attribute("messagee", is(messagenewpred)))
				.andExpect(view().name("messages/messagesEdit"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCrearMensajePredefinidoConErrores() throws Exception {
		given(userService.findUser(userrec.getUsername())).willReturn(Optional.of(userrec));
		mockMvc.perform(get("/messages/new/{username}", "testeando")).andExpect(status().isOk())
				.andExpect(view().name("errors/exception"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearMensajePredefinido2SinErrores() throws Exception {
		mockMvc.perform(post("/messages/new/{username}", userrec.getUsername()).with(csrf())
				.param("asunto", messagenew.getAsunto()).param("cuerpo", messagenew.getCuerpo())
				.param("id", messagenew.getId().toString())
				.param("usernamesend.username", messagenew.getUsernamesend().getUsername())
				.param("usernamesend.password", messagenew.getUsernamesend().getPassword())
				.param("usernamesend.email", messagenew.getUsernamesend().getEmail())
				.param("usernamesend.enabled", "True")
				.param("usernamereceive.username", messagenew.getUsernamereceive().getUsername())
				.param("usernamereceive.password", messagenew.getUsernamereceive().getPassword())
				.param("usernamereceive.email", messagenew.getUsernamereceive().getEmail())
				.param("usernamereceive.enabled", "True")

				.param("visto", "0")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/messages/"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearMensajePredefinido2ConErrores() throws Exception {
		// El error es que no te puedes enviar un mensaje a ti mismo

		mockMvc.perform(post("/messages/new/{username}", userrec.getUsername()).with(csrf())
				.param("asunto", message.getAsunto()).param("cuerpo", message.getCuerpo())
				.param("id", message.getId().toString())
				.param("usernamesend.username", message.getUsernamesend().getUsername())
				.param("usernamesend.password", message.getUsernamesend().getPassword())
				.param("usernamesend.email", message.getUsernamesend().getEmail()).param("usernamesend.enabled", "True")
				.param("usernamereceive.username", message.getUsernamereceive().getUsername())
				.param("usernamereceive.password", message.getUsernamereceive().getPassword())
				.param("usernamereceive.email", message.getUsernamereceive().getEmail())
				.param("usernamereceive.enabled", "True")

				.param("visto", "0")).andExpect(status().isOk()).andExpect(view().name("messages/messagesEdit"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testMuestraMensajePorIdSinErrores() throws Exception {
		given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.of(message));
		mockMvc.perform(get("/messages/view/{messageId}", TEST_MSG_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("messagee", is(message)))
				.andExpect(view().name("messages/messageDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testMuestraMensajePorIdConErrores() throws Exception {
		given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.empty());
		mockMvc.perform(get("/messages/view/{messageId}", TEST_MSG_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("encontrado", is(false)))
				.andExpect(view().name("messages/messageDetails"));
	}

}

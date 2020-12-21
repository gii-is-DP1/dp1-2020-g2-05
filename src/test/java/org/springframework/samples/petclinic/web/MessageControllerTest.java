package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

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
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.PetService;
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

@WebMvcTest(controllers = MessageController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class MessageControllerTest {
	private static final int TEST_MSG_ID = 1;


	@MockBean
	@Autowired
	private UserService userService;
	
	@MockBean
	@Autowired
	private MessageService messageService;
	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	private org.springframework.samples.petclinic.model.User userrec = new org.springframework.samples.petclinic.model.User();
	private org.springframework.samples.petclinic.model.User user = new org.springframework.samples.petclinic.model.User();
	private Message message = new Message();
	private Message messagenew = new Message();
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
		
		message.setId(1);
		message.setUsernamesend(user);
		message.setUsernamereceive(user);
		message.setVisto(0);
		message.setAsunto("PRUEBA");
		message.setCuerpo("PRUEBA CUERPO");
		
		messagenew.setId(2);
		messagenew.setUsernamesend(user);
		messagenew.setVisto(0);
		
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
	
//	@WithMockUser(value = "spring")
//  @Test
//  void testInitCreationForm() throws Exception {
//	mockMvc.perform(get("/owners/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
//			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
//	}
	
@WithMockUser(value = "spring")
@Test
void testListadoMensajes() throws Exception {
	given(messageService.findAllUsernameReceive(user.getUsername())).willReturn(lista);
	mockMvc.perform(get("/messages")).andExpect(status().isOk())	
	.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<Message> hasProperty("asunto", is(message.getAsunto())))))
	.andExpect(view().name("messages/messagesList"));
}

@WithMockUser(value = "spring")
@Test
void testBorrarMensaje() throws Exception {
	given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.of(message));
	mockMvc.perform(get("/messages/delete/{messageId}", TEST_MSG_ID))	
			.andExpect(status().is3xxRedirection())
			.andExpect(model().attribute("message", is("message successfully deleted!")))
			.andExpect(view().name("redirect:/messages"));
}

@WithMockUser(value = "spring")
@Test
void testBorrarMensajeFallido() throws Exception {
	given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.empty());
	mockMvc.perform(get("/messages/delete/{messageId}", TEST_MSG_ID))	
			.andExpect(status().is3xxRedirection())
			.andExpect(model().attribute("message", is("message not found!")))
			.andExpect(view().name("redirect:/messages"));
}

@WithMockUser(value = "spring")
@Test
void testCrearMensaje() throws Exception {
	mockMvc.perform(get("/messages/new"))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("messagee", is(messagenew)))
			.andExpect(model().attribute("usersend", is(user)))
			.andExpect(view().name("messages/messagesEdit"));
}

@WithMockUser(value = "spring")
@Test
void testCrearMensajePredefinido() throws Exception {
	given(userService.findUser(userrec.getUsername())).willReturn(Optional.of(userrec));
	mockMvc.perform(get("/messages/new/{username}", userrec.getUsername()))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("messagee", is(messagenewpred)))
			.andExpect(view().name("messages/messagesEdit"));
}

@WithMockUser(value = "spring")
@Test
void testCrearMensaje2() throws Exception {
//	given(userService.findUser(userrec.getUsername())).willReturn(Optional.of(userrec));
	mockMvc.perform(post("/messages/new") 
			.with(csrf())	
			.param("asunto", message.getAsunto())
			.param("cuerpo", message.getCuerpo())
			.param("id", message.getId().toString())
			.param("usernamesend", String.valueOf(message.getUsernamesend()))
			.param("usernamereceive", String.valueOf(message.getUsernamereceive()))
			.param("visto", "0"))	
			.andExpect(status().is3xxRedirection())			
			.andExpect(view().name("redirect:/messages/"));
}

@WithMockUser(value = "spring")
@Test
void testCrearMensajePredefinido2ConErrores() throws Exception {
	mockMvc.perform(post("/messages/new") 
			.with(csrf())	
			.param("asunto", message.getAsunto())
			.param("cuerpo", message.getCuerpo())
			.param("id", message.getId().toString())
			.param("usernamesend", message.getUsernamesend().toString())
			.param("usernamereceive", "martorsan13")
			.param("visto", "0"))	
			.andExpect(status().isOk())			
			.andExpect(view().name("messages/messagesEdit"));
}

@WithMockUser(value = "spring")
@Test
void testMuestraMensajePorIdSinErrores() throws Exception {
	given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.of(message));
	mockMvc.perform(get("/messages/view/{messageId}", TEST_MSG_ID))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("messagee", is(message)))
			.andExpect(view().name("messages/messageDetails"));
}

@WithMockUser(value = "spring")
@Test
void testMuestraMensajePorIdConErrores() throws Exception {
	given(messageService.findMessageById(TEST_MSG_ID)).willReturn(Optional.empty());
	mockMvc.perform(get("/messages/view/{messageId}", TEST_MSG_ID))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("encontrado", is(false)))
			.andExpect(view().name("messages/messageDetails"));
}




}

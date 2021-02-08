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
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PoblarBaseDeDatosService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.UserEmailEmptyOrNullException;
import org.springframework.samples.petclinic.service.exceptions.UserPasswordEmptyOrNullException;
import org.springframework.samples.petclinic.service.exceptions.UserUsernameEmptyOrNullException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class UserControllerTest {
	

	@MockBean
	private UserService userService;
	
	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private TablaConsultasService tablaConsultas;
	 
	@MockBean
	private PoblarBaseDeDatosService poblarBaseDeDatosService;
		
	@MockBean
	private TransactionService transactionService;
		
	@Autowired
	private MockMvc mockMvc;
	
	
	private org.springframework.samples.petclinic.model.User user = new org.springframework.samples.petclinic.model.User();
	private org.springframework.samples.petclinic.model.User amigo1 = new org.springframework.samples.petclinic.model.User();
	private org.springframework.samples.petclinic.model.User amigo2 = new org.springframework.samples.petclinic.model.User();


//	private Message message = new Message();

	private List<User> lista = new ArrayList<User>();

	
	@BeforeEach
	void setup() throws DataAccessException, UserEmailEmptyOrNullException, UserPasswordEmptyOrNullException, UserUsernameEmptyOrNullException {
		
		user.setUsername("serrojjim");
		user.setPassword("123456");
		user.setEmail("prueba@gmail.com");
		user.setEnabled(true);
		user.setImgperfil("userimg");
		
		amigo1.setUsername("amigo1");
		amigo1.setEnabled(true);
		amigo1.setImgperfil("amigo1");
		amigo1.setEmail("email1");
		amigo1.setPassword("pass1");
		
		amigo2.setUsername("amigo2");
		amigo2.setEnabled(true);
		amigo2.setImgperfil("amigo2");
		
		Set<Authorities> auth = new HashSet<Authorities>();
		Authorities autho = new Authorities();
		user.setPassword("test");
		autho.setAuthority("admin");
		autho.setUser(user);
		autho.setId(1);
		auth.add(autho);
		
		user.setAuthorities(auth);
		amigo1.setAuthorities(auth);
		amigo2.setAuthorities(auth);
		lista.add(amigo1);
		user.setFriends(lista);
	
//		
//		message.setId(1);
//		message.setUsernamesend(user);
//		message.setUsernamereceive(user);
//		message.setVisto(0);
//		message.setAsunto("PRUEBA");
//		message.setCuerpo("PRUEBA CUERPO");
//		
	
	
		
		
		this.userService.saveUser(user);
		this.userService.saveUser(amigo1);
//		this.userService.saveUser(amigo2);

		
//		this.messageService.saveMessage(message);

		given(this.userService.getUserSession()).willReturn(user);


		
		
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
			.param("username", user.getUsername() )
			.param("email", user.getEmail())
			.param("password", user.getPassword())
			.param("imgperfil", user.getImgperfil())
			.param("enabled","True"))
			
			.andExpect(status().is3xxRedirection())		
			.andExpect(view().name("redirect:/"));
}

@WithMockUser(value = "spring")
@Test
void testCrearUser2ConErrores() throws Exception {
	mockMvc.perform(post("/users/new") 
			.with(csrf())	
			.param("username", "   ")
			.param("email", user.getEmail())
			.param("password", user.getPassword())
			.param("enabled","True"))
			.andExpect(status().isOk())		
			.andExpect(view().name("users/createUserForm"));
}
	
@WithMockUser(value = "spring")
@Test
void testListadoAmigos() throws Exception {
	given( userService.findFriendByUser(user.getUsername())).willReturn(lista);
	mockMvc.perform(get("/friends")).andExpect(status().isOk())	
	.andExpect(model().attribute("resultados", Matchers.hasItem(Matchers.<User> hasProperty("username", is(amigo1.getUsername())))))
	.andExpect(view().name("friends/friendsList"));
}

@WithMockUser(value = "spring")
@Test
void testBorrarFriend() throws Exception {
	mockMvc.perform(get("/friends/remove/{username}", amigo1.getUsername()))	
			.andExpect(status().is3xxRedirection())
			.andExpect(model().attribute("message", is("Friend successfully deleted!")))
			.andExpect(view().name("redirect:/friends"));
}


@WithMockUser(value = "spring")
@Test
void testSeeFriend() throws Exception {
	given( userService.findUser(amigo1.getUsername())).willReturn(Optional.of(amigo1));

	mockMvc.perform(get("/friends/{username}", amigo1.getUsername()))
			.andExpect(status().isOk())
			.andExpect(model().attribute("user", is(amigo1)))
			.andExpect(view().name("friends/seefriend"));
}


@WithMockUser(value = "spring")
@Test
void testSeguirSinErrores() throws Exception {
	given(userService.findUser(amigo1.getUsername())).willReturn(Optional.of(amigo1));


	mockMvc.perform(post("/friends") 
			.with(csrf())	
			.param("username", amigo1.getUsername())
			.param("email", amigo1.getEmail())
			.param("password", amigo1.getPassword())
			.param("imgperfil", amigo1.getImgperfil())
			.param("enabled","True"))
			.andExpect(status().is3xxRedirection())		
			.andExpect(view().name("redirect:/friends"));
}

@WithMockUser(value = "spring")
@Test
void testSeguirConErrores() throws Exception {
	given(userService.findUser(amigo1.getUsername())).willReturn(Optional.of(amigo1));

//El error es que se sigue a sí mismo.
	mockMvc.perform(post("/friends") 
			.with(csrf())	
			.param("username", user.getUsername())
			.param("email", user.getEmail())
			.param("password", user.getPassword())
			.param("enabled","True"))
		
			.andExpect(status().isOk())		
			.andExpect(view().name("friends/friendsList"));
}

@WithMockUser(value = "spring")
@Test
void testFormEditarUser() throws Exception {
	mockMvc.perform(get("/users/editarPerfil"))	
			.andExpect(status().isOk())
			.andExpect(model().attribute("user", is(user)))
			.andExpect(view().name("Perfil/editarPerfil"));
}

@WithMockUser(value = "spring")
@Test
void testEditarUser2SinErrores() throws Exception {
	mockMvc.perform(post("/users/editarPerfil") 
			.with(csrf())	
			.param("username", user.getUsername() )
			.param("email", user.getEmail())
			.param("password", user.getPassword())
			.param("imgperfil", user.getImgperfil())
			.param("enabled","True"))
			.andExpect(model().attribute("teams", is(user.getTeam())))
			.andExpect(status().isOk())		
			.andExpect(view().name("Perfil/Perfil"));
}

@WithMockUser(value = "spring")
@Test
void testEditarUser2ConErrores() throws Exception {
	mockMvc.perform(post("/users/editarPerfil") 
			.with(csrf())	
			.param("username", user.getUsername())
			.param("email", " ")
			.param("password", user.getPassword())
			.param("imgperfil", user.getImgperfil())
			.param("enabled","True"))
			.andExpect(status().is4xxClientError())		;
}



}

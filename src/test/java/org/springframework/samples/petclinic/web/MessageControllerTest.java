package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;


import java.util.Set;import java.util.stream.Collectors;

import org.apache.taglibs.standard.tag.common.fmt.SetBundleSupport;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
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
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MessageController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class MessageControllerTest {
	
	private static final String TEST_USER_NAME1 = "serrojjim";
	private static final String TEST_USER_NAME2 = "antcammar4";


	private static final int TEST_MESSAGE_ID = 1;

	@Autowired
	private MessageController messageController;
	
	@MockBean
	private UserService userService;
	private MessageService messageService;

	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		
		User user = new User();
		Message message = new Message();
		
		
		message.setId(TEST_MESSAGE_ID);
		message.setUsernamesend(userService.findUser(TEST_USER_NAME1).get());
		message.setUsernamereceive(userService.findUser(TEST_USER_NAME2).get());
		message.setVisto(0);
		message.setAsunto("PRUEBA");
		message.setCuerpo("PRUEBA CUERPO");
		

		given(this.messageService.findMessageById(TEST_MESSAGE_ID).get()).willReturn(new Message());
		given(this.messageService.findAllUsernameReceive(TEST_USER_NAME2).spliterator().getExactSizeIfKnown()).willReturn(1L);
		
	}


}

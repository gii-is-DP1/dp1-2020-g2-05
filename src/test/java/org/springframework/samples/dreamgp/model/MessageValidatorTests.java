package org.springframework.samples.dreamgp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.dreamgp.model.Message;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
@Slf4j
class MessageValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenCuerpoEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User userrec = new User();
		userrec.setUsername("sergio");
		userrec.setEmail("sergio@prueba.com");
		userrec.setPassword("123");
		
		User usersend = new User();
		usersend.setUsername("sergio");
		usersend.setEmail("sergio@prueba.com");
		usersend.setPassword("123");

		Message mensaje = new Message();
		mensaje.setAsunto("Prueba asunto");
		mensaje.setId(1);
		mensaje.setUsernamereceive(userrec);
		mensaje.setUsernamesend(usersend);
		mensaje.setVisto(0);
		log.info("Se ha creado el mensaje con cuerpo empty: " + mensaje);


		Validator validator = createValidator();
		Set<ConstraintViolation<Message>> constraintViolations = validator.validate(mensaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Message> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("cuerpo");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenAsuntoEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User userrec = new User();
		userrec.setUsername("sergio");
		userrec.setEmail("sergio@prueba.com");
		userrec.setPassword("123");
		
		User usersend = new User();
		usersend.setUsername("sergio");
		usersend.setEmail("sergio@prueba.com");
		usersend.setPassword("123");

		Message mensaje = new Message();
		mensaje.setCuerpo("Pruebo cuerpo");
		mensaje.setId(1);
		mensaje.setUsernamereceive(userrec);
		mensaje.setUsernamesend(usersend);
		mensaje.setVisto(0);
		log.info("Se ha creado el mensaje con asunto empty: " + mensaje);

	
		Validator validator = createValidator();
		Set<ConstraintViolation<Message>> constraintViolations = validator.validate(mensaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Message> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("asunto");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenUsernameReceiveNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User userrec = new User();
		userrec.setUsername("sergio");
		userrec.setEmail("sergio@prueba.com");
		userrec.setPassword("123");
		
		User usersend = new User();
		usersend.setUsername("sergio");
		usersend.setEmail("sergio@prueba.com");
		usersend.setPassword("123");

		Message mensaje = new Message();
		mensaje.setAsunto("Prueba asunto");
		mensaje.setCuerpo("Prueba asunto");
		mensaje.setId(1);
		mensaje.setUsernamesend(usersend);
		mensaje.setVisto(0);
		log.info("Se ha creado el mensaje con usernamereceive null: " + mensaje);


		Validator validator = createValidator();
		Set<ConstraintViolation<Message>> constraintViolations = validator.validate(mensaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Message> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("usernamereceive");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenUsernameSendNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User userrec = new User();
		userrec.setUsername("sergio");
		userrec.setEmail("sergio@prueba.com");
		userrec.setPassword("123");
		
		User usersend = new User();
		usersend.setUsername("sergio");
		usersend.setEmail("sergio@prueba.com");
		usersend.setPassword("123");

		Message mensaje = new Message();
		mensaje.setAsunto("Prueba asunto");
		mensaje.setCuerpo("Prueba cuerpo");
		mensaje.setId(1);
		mensaje.setUsernamereceive(usersend);
		mensaje.setVisto(0);
		log.info("Se ha creado el mensaje con usernamesend null: " + mensaje);

		

		Validator validator = createValidator();
		Set<ConstraintViolation<Message>> constraintViolations = validator.validate(mensaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Message> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("usernamesend");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenVistoNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User userrec = new User();
		userrec.setUsername("sergio");
		userrec.setEmail("sergio@prueba.com");
		userrec.setPassword("123");
		
		User usersend = new User();
		usersend.setUsername("sergio");
		usersend.setEmail("sergio@prueba.com");
		usersend.setPassword("123");

		Message mensaje = new Message();
		mensaje.setAsunto("Prueba asunto");
		mensaje.setCuerpo("Prueba cuerpo");
		mensaje.setId(1);
		mensaje.setUsernamereceive(usersend);
		mensaje.setUsernamesend(usersend);
		log.info("Se ha creado el mensaje con visto null: " + mensaje);


		Validator validator = createValidator();
		Set<ConstraintViolation<Message>> constraintViolations = validator.validate(mensaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Message> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("visto");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}


}

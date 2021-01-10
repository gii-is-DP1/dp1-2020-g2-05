package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.GranPremioController;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
@Slf4j
class UserValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenUsernameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User user = new User();
		user.setEmail("sergio@prueba.com");
		user.setPassword("123");
		user.setImgperfil("http://google.com");
		user.setEnabled(true);
		log.info("Se ha creado el user con username empty: " + user);



		Validator validator = createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<User> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("username");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User user = new User();
		user.setUsername("sergio");
		user.setPassword("123");
		user.setImgperfil("http://google.com");
		user.setEnabled(true);
		log.info("Se ha creado el user con email empty: " + user);


		Validator validator = createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<User> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	

	@Test
	void shouldNotValidateWhenEmailNotFormat() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User user = new User();
		user.setUsername("sergio");
		user.setPassword("123");
		user.setEmail("123");
		user.setImgperfil("http://google.com");
		user.setEnabled(true);
		log.info("Se ha creado el user con email incorrecto: " + user);


		Validator validator = createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<User> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
		assertThat(violation.getMessage()).isEqualTo("must be a well-formed email address");
	}
	
	@Test
	void shouldNotValidateWhenPasswordEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User user = new User();
		user.setUsername("sergio");
		user.setEmail("prueba@gmail.com");
		user.setImgperfil("http://google.com");
		user.setEnabled(true);
		log.info("Se ha creado el user con password empty: " + user);

		Validator validator = createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<User> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenImgPerfilEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User user = new User();
		user.setUsername("sergio");
		user.setPassword("123");
		user.setEmail("sergio@prueba.com");
		user.setEnabled(true);
		log.info("Se ha creado el user con url empty: " + user);

		Validator validator = createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<User> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("imgperfil");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenImgPerfilNotFormat() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		
		User user = new User();
		user.setUsername("sergio");
		user.setPassword("123");
		user.setEmail("sergio@prueba.com");
		user.setImgperfil("oli");
		user.setEnabled(true);
		
		log.info("Se ha creado el user con url incorrecta: " + user);


		Validator validator = createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<User> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("imgperfil");
		assertThat(violation.getMessage()).isEqualTo("must be a valid URL");
	}
	
	
	
	
}

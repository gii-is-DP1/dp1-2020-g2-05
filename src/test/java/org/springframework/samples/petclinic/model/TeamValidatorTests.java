package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class TeamValidatorTests {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

//	@Test
//	void shouldNotValidateWhenNameEmpty() {
//
//		Team team = new Team();
//		team.setName("");
//		
//
//		Validator validator = createValidator();
//		Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
//
//		assertThat(constraintViolations.size()).isEqualTo(1);
//		ConstraintViolation<Team> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
//		assertThat(violation.getMessage()).isEqualTo("must not be empty");
//	}


}

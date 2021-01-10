package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.validation.Errors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.LeagueValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */

class LeagueModelTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}


	
	@Test
	void shouldNotValidateWhenLeagueDateNameEmptyAndBlankAndNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		League league = new League();
		league.setLeagueCode("ASDWQASDEW");
		
		Validator validator = createValidator();
		List<ConstraintViolation<League>> constraintViolations = validator.validate(league).stream().collect(Collectors.toList());

		assertThat(constraintViolations.size()).isEqualTo(3);
		ConstraintViolation<League> violation = constraintViolations.get(0);
		assertThat(violation.getPropertyPath().toString()).isEqualTo("leagueDate");
		violation = constraintViolations.get(1);
		assertThat(violation.getPropertyPath().toString()).isEqualTo("leagueDate");
		violation = constraintViolations.get(2);
		assertThat(violation.getPropertyPath().toString()).isEqualTo("leagueDate");
	}

	@Test
	void shouldNotValidateWhenLeagueCodeWrongSize() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		League league = new League();
		league.setLeagueCode("ASAS");
		league.setLeagueDate("2020/01/01");
		Validator validator = createValidator();
		List<ConstraintViolation<League>> constraintViolations = validator.validate(league).stream().collect(Collectors.toList());

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<League> violation = constraintViolations.get(0);
		assertThat(violation.getPropertyPath().toString()).isEqualTo("leagueCode");
		assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 10");
	
	}
	
	@Test
	void shouldNotValidateWhenLeagueCodeWrong() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		League league = new League();
		league.setLeagueDate("2020/01/01");
		Validator validator = createValidator();
		List<ConstraintViolation<League>> constraintViolations = validator.validate(league).stream().collect(Collectors.toList());

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<League> violation = constraintViolations.get(0);
		assertThat(violation.getPropertyPath().toString()).isEqualTo("leagueCode");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	
	}
}

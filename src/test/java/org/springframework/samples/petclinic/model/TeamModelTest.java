package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
public class TeamModelTest {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void validacionNotEmptyAndNotNull() {
		Team team = new Team();
		team.setName("");
		team.setMoney(null);
		team.setPoints(null);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
		assertThat(constraintViolations.size()).isEqualTo(3);

	}
	
	@Test
	void validacionNotEmptyAndNotNullPositive() {
		Team team = new Team();
		team.setName("PruebaTest");
		team.setPoints(123);
		team.setMoney(123);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@Test
	void validacionNotNegative() {
		Team team = new Team();
		team.setName("PruebasTest");
		team.setMoney(-38);
		team.setPoints(-90);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
		assertThat(constraintViolations.size()).isEqualTo(2);

	}
	
	


}

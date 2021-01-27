package org.springframework.samples.petclinic.model;

import javax.validation.Validator;

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

package org.springframework.samples.dreamgp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.samples.dreamgp.model.Offer;
import org.springframework.samples.dreamgp.model.Status;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class OfferModelTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void validacionOk() {
		Offer offer = new Offer();
		offer.setPrice(200);
		offer.setStatus(Status.Outstanding);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@Test
	void validacionNotNull() {
		Offer offer = new Offer();
		offer.setPrice(null);
		offer.setStatus(Status.Outstanding);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@Test
	void validationNotNegative() {
		Offer offer = new Offer();
		offer.setPrice(-200);
		offer.setStatus(Status.Outstanding);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Offer>> constraintViolations = validator.validate(offer);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}

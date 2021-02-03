package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LineupValidator implements Validator {
	
	private static final String SELECCIONA_FICHAJES = "No has seleccionado ningún fichaje!";

	@Override
	public void validate(Object obj, Errors errors) {
		Lineup lineup = (Lineup) obj;
		Recruit recruit1 = lineup.getRecruit1();
		Recruit recruit2 = lineup.getRecruit2();

		// recruit1 not selected validation
		if (recruit1==null){
			errors.rejectValue("recruit1", SELECCIONA_FICHAJES, SELECCIONA_FICHAJES);
		}
		
		// recruit2 not selected validation
		if (recruit2==null){
			errors.rejectValue("recruit2", SELECCIONA_FICHAJES, SELECCIONA_FICHAJES);
		}
		
	}

	/**
	 * This Validator validates *just* Lineup instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Lineup.class.isAssignableFrom(clazz);
	}
}

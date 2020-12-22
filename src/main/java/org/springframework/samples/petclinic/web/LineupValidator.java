package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Lineup;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LineupValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Lineup lineup = (Lineup) obj;
		Recruit recruit1 = lineup.getRecruit1();
		Recruit recruit2 = lineup.getRecruit2();

		// recruit1 not selected validation
		if (recruit1==null){
			errors.rejectValue("recruit1", "No has seleccionado ningún fichaje!", "No has seleccionado ningún fichaje!");
		}
		
		// recruit2 not selected validation
		if (recruit2==null){
			errors.rejectValue("recruit2", "No has seleccionado ningún fichaje!", "No has seleccionado ningún fichaje!");
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

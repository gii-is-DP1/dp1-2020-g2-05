package org.springframework.samples.petclinic.web.validator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BDCarreraValidator implements Validator{
	
private static final String REQUIRED = "required";
	
	
	@Override
	public void validate(Object obj, Errors errors) {
		BDCarrera form1 = (BDCarrera) obj;
		Calendar fecha = new GregorianCalendar();

		
		if (form1.getYear()!=(int) form1.getYear() || form1.getYear()>fecha.get(Calendar.YEAR)){
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 characters and no special ones");
		}

	}

	/**
	 * This Validator validates *just* League instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return BDCarrera.class.isAssignableFrom(clazz);
	}


}
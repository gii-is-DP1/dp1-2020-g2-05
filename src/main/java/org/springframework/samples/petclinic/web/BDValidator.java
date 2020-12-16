package org.springframework.samples.petclinic.web;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.BDCarrera;
import org.springframework.samples.petclinic.model.FormRellenarBD;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BDValidator implements Validator{
	
private static final String REQUIRED = "required";
	
	
	@Override
	public void validate(Object obj, Errors errors) {
		FormRellenarBD form = (FormRellenarBD) obj;

		Calendar fecha = new GregorianCalendar();
		
		 //AÑO INICIAL validation
		if (form.getAnyoInicial()>=form.getAnyoFinal() || form.getAnyoInicial()!=(int) form.getAnyoInicial() || form.getAnyoInicial()>fecha.get(Calendar.YEAR)){
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 characters and no special ones");
		}
		
		
		//Año FInal validation
		if(form.getAnyoFinal()>fecha.get(Calendar.YEAR) || form.getAnyoInicial()>=form.getAnyoFinal() || form.getAnyoInicial()!=(int) form.getAnyoInicial()) {
			 errors.rejectValue("points", REQUIRED, "Team Id cannot be null");
		}
	
		
	}

	/**
	 * This Validator validates *just* League instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FormRellenarBD.class.isAssignableFrom(clazz);
	}


}

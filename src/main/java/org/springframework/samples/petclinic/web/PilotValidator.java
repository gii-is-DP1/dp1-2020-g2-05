package org.springframework.samples.petclinic.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PilotValidator implements Validator{
	
	private static final String REQUIRED = "required";

	@Override
	public boolean supports(Class<?> clazz) {
		return Pilot.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Pilot	pilot = (Pilot) obj;	
		Pattern p = Pattern.compile("\\p{Punct}");
		String name = pilot.getName();
		String lastName = pilot.getLastName();
		Matcher m = p.matcher(name);
		Matcher n = p.matcher(lastName);
		
		 boolean resultado;

	        try {
	            Integer.parseInt(pilot.getDorsal());
	            resultado = true;
	        } catch (NumberFormatException excepcion) {
	            resultado = false;
	        }


		 int count = 0;
		while (m.find() || n.find()) {
            count++;
		}
		
		if (!StringUtils.hasLength(name) || name.length()>30 || name.length()<4 || count != 0){
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 characters and no special ones");
		}
		
		if(!StringUtils.hasLength(lastName) || lastName.length()>30 || lastName.length()<1 || count != 0)
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 characters and no special ones");
		
		if(pilot.getBaseValue() == null) {
			errors.rejectValue("BaseValue",REQUIRED + " cannot be null");
		}
		
//		if(pilot.getCategory() != Category.MOTO2 || pilot.getCategory() != Category.MOTO3 || pilot.getCategory() != Category.MOTOGP) {
//			errors.rejectValue("Category", REQUIRED + " should be Moto3, Moto2 or MotoGP");
//		}
		
		if(resultado == false) {
			errors.rejectValue("Dorsal", REQUIRED + "shoudl be Integer");
		}
	}

}

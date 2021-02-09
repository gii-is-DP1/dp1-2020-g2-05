package org.springframework.samples.dreamgp.web.validator;

import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LineupValidator implements Validator {
	
	private static final String SELECCIONA_FICHAJES = "You haven't selected any rider!";
	private static final String FICHAJE_DUPLICADO = "You can't select the same rider twice!";
	private static final String FICHAJE_EN_VENTA = "You can't select a rider that is for sale!";
	private static final String FORSALE_NULL = "The forSale field is null!";

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
		
		// repeated recruit selection validation
		if (recruit1 == recruit2) {
			errors.rejectValue("recruit1", FICHAJE_DUPLICADO, FICHAJE_DUPLICADO);
			errors.rejectValue("recruit2", FICHAJE_DUPLICADO, FICHAJE_DUPLICADO);
		}
		
		// recruit1 selected being for sale validation
		try {
			if (recruit1.getForSale() != false) {
				errors.rejectValue("recruit1", FICHAJE_EN_VENTA, FICHAJE_EN_VENTA);
			}
		} catch (Exception e) {
			errors.rejectValue("recruit1", FORSALE_NULL, FORSALE_NULL);
		}
		
		// recruit2 selected being for sale validation
		try {
			if (recruit2.getForSale() != false) {
				errors.rejectValue("recruit2", FICHAJE_EN_VENTA, FICHAJE_EN_VENTA);
			}
		} catch (Exception e) {
			errors.rejectValue("recruit1", FORSALE_NULL, FORSALE_NULL);
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

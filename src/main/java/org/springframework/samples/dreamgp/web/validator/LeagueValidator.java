/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.dreamgp.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.samples.dreamgp.model.League;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class LeagueValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		League league = (League) obj;
		String name = league.getName();
		Pattern p = Pattern.compile("\\p{Punct}");
		Matcher m = p.matcher(name);
		 int count = 0;
		while (m.find()) {
           count++;
		}
		// name validation
		if (!StringUtils.hasLength(name) || name.length()>50 || name.length()<3 || count != 0 ||  name.isEmpty() || name.trim().isEmpty()){
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 characters and no special ones");
		}
		
		
		
		//races completed validation
//		if(league.getRacesCompleted()==null || league.getRacesCompleted()<0 || league.getRacesCompleted()>20) {
//			 errors.rejectValue("racesCompleted", REQUIRED, "races completed cant be null");
//		}
		
		//league code validation
		if(league.getLeagueCode()==null || league.getLeagueCode().length()>10 || league.getLeagueCode().length()<10) {
			 errors.rejectValue("leagueCode", REQUIRED, "league code cant be null");
		}
		
		// date validation
		if (league.getLeagueDate() == null) {
			errors.rejectValue("leagueDate", REQUIRED, REQUIRED);
		}
	}

	/**
	 * This Validator validates *just* League instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return League.class.isAssignableFrom(clazz);
	}

}

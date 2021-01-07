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
package org.springframework.samples.petclinic.web;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
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
public class UserValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		User user = (User) obj;
		String name = user.getUsername();
		Pattern p = Pattern.compile("\\p{Punct}");
		Matcher m = p.matcher(name);
	    int count = 0;
		while (m.find()) {
           count++;
		}

		
//		//name validation
		if(name==null || name.trim().isEmpty()==true) {
			 errors.rejectValue("username", REQUIRED, "El nombre de usuario no puede ser null o estar vacio");
		}
		
	} 

	/**
	 * This Validator validates *just* League instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

}

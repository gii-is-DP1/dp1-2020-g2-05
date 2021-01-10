package org.springframework.samples.petclinic.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TeamValidator implements Validator{
	
	private static final String REQUIRED = "required";
	
	
	@Override
	public void validate(Object obj, Errors errors) {
		Team team = (Team) obj;
		Pattern p = Pattern.compile("\\p{Punct}");
		String name = team.getName();
		Matcher m = p.matcher(name);
		 int count = 0;
		while (m.find()) {
            count++;
		}
		// name validation
		if (!StringUtils.hasLength(name) || name.length()>30 || name.length()<4 || count != 0){
			errors.rejectValue("name", REQUIRED+" and between 3 and 50 characters", REQUIRED+" and between 3 and 50 characters and no special ones");
		}
		
		//Points validation
		if(team.getPoints()==null || team.getPoints()<0) {
			 errors.rejectValue("points", REQUIRED, "Team points cannot be null");
		}
		
		//league  validation
		if(team.getLeague()==null) {
			 errors.rejectValue("league", REQUIRED, "league cannot be null");
		}
		
		// Money validation
		if (team.getMoney() == null || team.getMoney()<0) {
			errors.rejectValue("money", REQUIRED,  REQUIRED);
		}
		
	
		
	}

	/**
	 * This Validator validates *just* League instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Team.class.isAssignableFrom(clazz);
	}


}

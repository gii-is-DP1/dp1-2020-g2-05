package org.springframework.samples.dreamgp.model;


import static org.springframework.samples.dreamgp.entryPointsForAssertions.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.samples.dreamgp.customAssertions.Assertions;
import org.springframework.samples.dreamgp.web.validator.LineupValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class LineupTests {
	
	@Test
	void validacionNotEmptyPositive() {
		
		Lineup lineup = new Lineup();
		GranPremio gp = new GranPremio();
		Recruit recruit1 = new Recruit();
		Recruit recruit2 = new Recruit();
		Team team = new Team();
		
		recruit1.setForSale(false);
		recruit2.setForSale(false);

		lineup.setCategory(Category.MOTOGP);
		lineup.setGp(gp);
		lineup.setRecruit1(recruit1);
		lineup.setRecruit2(recruit2);
		lineup.setTeam(team);
		
		assertThat(lineup).hasCategory(Category.MOTOGP);
		assertThat(lineup).hasGp(gp);
		assertThat(lineup).hasRecruit1(recruit1);
		assertThat(lineup).hasRecruit2(recruit2);
		assertThat(lineup).hasTeam(team);

		Assertions.assertThat(lineup).hasAllRelations();
		
		LineupValidator lineupValidator = new LineupValidator();
        Errors errors = new BeanPropertyBindingResult(lineup, "");

		lineupValidator.validate(lineup, errors);
        assertThat(errors.getAllErrors().size() == 0);
	}
		
	@Test
	void validacionNotEmptyNegative() {
		Lineup lineup = new Lineup();
		lineup.setCategory(null);
		lineup.setGp(null);
		lineup.setRecruit1(null);
		lineup.setRecruit2(null);
		lineup.setTeam(null);
		
		assertThat(lineup).extracting("category").isNull();
		assertThat(lineup).extracting("gp").isNull();
		assertThat(lineup).extracting("recruit1").isNull();
		assertThat(lineup).extracting("recruit2").isNull();
		assertThat(lineup).extracting("team").isNull();

		assertThat(lineup).hasAllNullFieldsOrProperties();
		Assertions.assertThat(lineup).doesNotHaveAnyRelations();
		
		LineupValidator lineupValidator = new LineupValidator();
        Errors errors = new BeanPropertyBindingResult(lineup, "");

		lineupValidator.validate(lineup, errors);
        assertThat(errors.getAllErrors().size() >= 0);
	}
	
	@Test
	void validacionDuplicatedRecruit() {
		
		Lineup lineup = new Lineup();
		GranPremio gp = new GranPremio();
		Recruit recruit1 = new Recruit();
		Team team = new Team();
		
		recruit1.setForSale(false);

		lineup.setCategory(Category.MOTOGP);
		lineup.setGp(gp);
		lineup.setRecruit1(recruit1);
		lineup.setRecruit2(recruit1);
		lineup.setTeam(team);
		
		LineupValidator lineupValidator = new LineupValidator();
        Errors errors = new BeanPropertyBindingResult(lineup, "");

		lineupValidator.validate(lineup, errors);
        assertThat(errors.getFieldError("recruit1"));
	}
	
	@Test
	void validacionRecruit1ForSale() {
		
		Lineup lineup = new Lineup();
		GranPremio gp = new GranPremio();
		Recruit recruit1 = new Recruit();
		Recruit recruit2 = new Recruit();
		Team team = new Team();
		
		recruit1.setForSale(true);
		recruit1.setForSale(false);

		lineup.setCategory(Category.MOTOGP);
		lineup.setGp(gp);
		lineup.setRecruit1(recruit1);
		lineup.setRecruit2(recruit2);
		lineup.setTeam(team);
		
		LineupValidator lineupValidator = new LineupValidator();
        Errors errors = new BeanPropertyBindingResult(lineup, "");

		lineupValidator.validate(lineup, errors);
        assertThat(errors.getFieldError("recruit1"));
	}
	
	@Test
	void validacionRecruit2ForSale() {
		
		Lineup lineup = new Lineup();
		GranPremio gp = new GranPremio();
		Recruit recruit1 = new Recruit();
		Recruit recruit2 = new Recruit();
		Team team = new Team();
		
		recruit1.setForSale(false);
		recruit1.setForSale(true);

		lineup.setCategory(Category.MOTOGP);
		lineup.setGp(gp);
		lineup.setRecruit1(recruit1);
		lineup.setRecruit2(recruit2);
		lineup.setTeam(team);
		
		LineupValidator lineupValidator = new LineupValidator();
        Errors errors = new BeanPropertyBindingResult(lineup, "");

		lineupValidator.validate(lineup, errors);
        assertThat(errors.getFieldError("recruit2"));
	}
}

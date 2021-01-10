package org.springframework.samples.petclinic.model;


import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customAssertions.Assertions;

public class LineupTests {
	
	@Test
	void validacionNotEmptyPositive() {
		Lineup lineup = new Lineup();
		lineup.setCategory(Category.MOTOGP);
		lineup.setGp(new GranPremio());
		lineup.setRecruit1(new Recruit());
		lineup.setRecruit2(new Recruit());
		lineup.setTeam(new Team());

		Assertions.assertThat(lineup).hasAllRelations();
	}
		
	@Test
	void validacionNotEmptyNegative() {
		Lineup lineup = new Lineup();
		lineup.setCategory(null);
		lineup.setGp(null);
		lineup.setRecruit1(null);
		lineup.setRecruit2(null);
		lineup.setTeam(null);

		Assertions.assertThat(lineup).doesNotHaveAnyRelations();
	}
}

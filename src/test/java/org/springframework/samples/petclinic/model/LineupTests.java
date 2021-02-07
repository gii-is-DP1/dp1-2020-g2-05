package org.springframework.samples.petclinic.model;


import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customAssertions.Assertions;
import static org.springframework.samples.petclinic.entryPointsForAssertions.Assertions.assertThat;

public class LineupTests {
	
	@Test
	void validacionNotEmptyPositive() {
		
		Lineup lineup = new Lineup();
		GranPremio gp = new GranPremio();
		Recruit recruit1 = new Recruit();
		Recruit recruit2 = new Recruit();
		Team team = new Team();
		
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
	}
}

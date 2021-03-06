package org.springframework.samples.dreamgp.customAssertions;

import org.assertj.core.api.AbstractAssert;
import org.springframework.samples.dreamgp.model.Lineup;

public class LineupAssert extends AbstractAssert<LineupAssert, Lineup> {

	public LineupAssert(Lineup actual) {
		super(actual, LineupAssert.class);
	}


	public static LineupAssert assertThat(Lineup actual) {
		return new LineupAssert(actual);
	}

	public LineupAssert hasAllRelations() {
		isNotNull();
		Boolean errors = false;
		String res = "";
		if (actual.getGp() == null) {
			errors = true;
			res += "(GP)";
		}
		
		if (actual.getTeam() == null) {
			errors = true;
			res += "(Team)";
		}
		
		if (actual.getRecruit1() == null) {
			errors = true;
			res += "(Recruit1)";
		}
		
		if (actual.getRecruit2() == null) {
			errors = true;
			res += "(Recruit2)";
		}
		
		if (errors == true) {
			failWithMessage("Expected lineup to have relationships with: [%s]", res);
		}
		
		return this;
	}
	
	public LineupAssert doesNotHaveAnyRelations() {
		isNotNull();
		Boolean errors = false;
		String res = "";
		if (actual.getGp() != null) {
			errors = true;
			res = "(GP)";
		}
		
		if (actual.getTeam() != null) {
			errors = true;
			res = "(Team)";
		}
		
		if (actual.getRecruit1() != null) {
			errors = true;
			res = "(Recruit1)";
		}
		
		if (actual.getRecruit2() != null) {
			errors = true;
			res = "(Recruit2)";
		}
		
		if (errors == true) {
			failWithMessage("Expected lineup not to have relationships with: [%s]", res);
		}
		
		return this;
	}
}

package org.springframework.samples.petclinic.customAssertions;

import org.springframework.samples.petclinic.model.Lineup;

public class Assertions {
	
	protected Assertions() {
    }
	
    public static LineupAssert assertThat(Lineup actual) {
        return new LineupAssert(actual);
    }
    
}
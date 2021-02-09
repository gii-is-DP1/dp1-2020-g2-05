package org.springframework.samples.dreamgp.customAssertions;

import org.springframework.samples.dreamgp.model.Lineup;

public class Assertions {
	
	protected Assertions() {
    }
	
    public static LineupAssert assertThat(Lineup actual) {
        return new LineupAssert(actual);
    }
    
}
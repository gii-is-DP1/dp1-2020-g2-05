package org.springframework.samples.petclinic.customAssertions;

import javax.annotation.Generated;

import org.assertj.core.util.CheckReturnValue;
import org.springframework.samples.petclinic.model.Lineup;

//@Generated(value="assertj-assertions-generator")
public class Assertions {
	
	protected Assertions() {
    }
	
//    @CheckReturnValue
    public static LineupAssert assertThat(Lineup actual) {
        return new LineupAssert(actual);
    }
    

    

    // static factory methods of other assertion classes
}
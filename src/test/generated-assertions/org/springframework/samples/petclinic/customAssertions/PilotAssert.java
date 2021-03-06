package org.springframework.samples.petclinic.customAssertions;

import org.springframework.samples.petclinic.model.Pilot;

/**
 * {@link Pilot} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractPilotAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class PilotAssert extends AbstractPilotAssert<PilotAssert, Pilot> {

  /**
   * Creates a new <code>{@link PilotAssert}</code> to make assertions on actual Pilot.
   * @param actual the Pilot we want to make assertions on.
   */
  public PilotAssert(Pilot actual) {
    super(actual, PilotAssert.class);
  }

  /**
   * An entry point for PilotAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myPilot)</code> and get specific assertion with code completion.
   * @param actual the Pilot we want to make assertions on.
   * @return a new <code>{@link PilotAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static PilotAssert assertThat(Pilot actual) {
    return new PilotAssert(actual);
  }
}

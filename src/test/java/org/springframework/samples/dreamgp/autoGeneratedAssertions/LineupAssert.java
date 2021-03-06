package org.springframework.samples.dreamgp.autoGeneratedAssertions;

import org.springframework.samples.dreamgp.model.Lineup;

/**
 * {@link Lineup} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractLineupAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class LineupAssert extends AbstractLineupAssert<LineupAssert, Lineup> {

  /**
   * Creates a new <code>{@link LineupAssert}</code> to make assertions on actual Lineup.
   * @param actual the Lineup we want to make assertions on.
   */
  public LineupAssert(Lineup actual) {
    super(actual, LineupAssert.class);
  }

  /**
   * An entry point for LineupAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myLineup)</code> and get specific assertion with code completion.
   * @param actual the Lineup we want to make assertions on.
   * @return a new <code>{@link LineupAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static LineupAssert assertThat(Lineup actual) {
    return new LineupAssert(actual);
  }
}

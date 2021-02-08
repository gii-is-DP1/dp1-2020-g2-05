package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.springframework.samples.petclinic.model.League;

/**
 * {@link League} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractLeagueAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class LeagueAssert extends AbstractLeagueAssert<LeagueAssert, League> {

  /**
   * Creates a new <code>{@link LeagueAssert}</code> to make assertions on actual League.
   * @param actual the League we want to make assertions on.
   */
  public LeagueAssert(League actual) {
    super(actual, LeagueAssert.class);
  }

  /**
   * An entry point for LeagueAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myLeague)</code> and get specific assertion with code completion.
   * @param actual the League we want to make assertions on.
   * @return a new <code>{@link LeagueAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static LeagueAssert assertThat(League actual) {
    return new LeagueAssert(actual);
  }
}

package org.springframework.samples.petclinic.customAssertions;

import org.springframework.samples.petclinic.model.Team;

/**
 * {@link Team} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractTeamAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class TeamAssert extends AbstractTeamAssert<TeamAssert, Team> {

  /**
   * Creates a new <code>{@link TeamAssert}</code> to make assertions on actual Team.
   * @param actual the Team we want to make assertions on.
   */
  public TeamAssert(Team actual) {
    super(actual, TeamAssert.class);
  }

  /**
   * An entry point for TeamAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myTeam)</code> and get specific assertion with code completion.
   * @param actual the Team we want to make assertions on.
   * @return a new <code>{@link TeamAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static TeamAssert assertThat(Team actual) {
    return new TeamAssert(actual);
  }
}

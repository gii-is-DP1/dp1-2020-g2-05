package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.springframework.samples.petclinic.model.audit.UserRevEntity;

/**
 * {@link UserRevEntity} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractUserRevEntityAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class UserRevEntityAssert extends AbstractUserRevEntityAssert<UserRevEntityAssert, UserRevEntity> {

  /**
   * Creates a new <code>{@link UserRevEntityAssert}</code> to make assertions on actual UserRevEntity.
   * @param actual the UserRevEntity we want to make assertions on.
   */
  public UserRevEntityAssert(UserRevEntity actual) {
    super(actual, UserRevEntityAssert.class);
  }

  /**
   * An entry point for UserRevEntityAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myUserRevEntity)</code> and get specific assertion with code completion.
   * @param actual the UserRevEntity we want to make assertions on.
   * @return a new <code>{@link UserRevEntityAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static UserRevEntityAssert assertThat(UserRevEntity actual) {
    return new UserRevEntityAssert(actual);
  }
}

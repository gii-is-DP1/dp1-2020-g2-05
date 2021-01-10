package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Lineup;

/**
 * Abstract base class for {@link Lineup} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractLineupAssert<S extends AbstractLineupAssert<S, A>, A extends Lineup> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractLineupAssert}</code> to make assertions on actual Lineup.
   * @param actual the Lineup we want to make assertions on.
   */
  protected AbstractLineupAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Lineup's category is equal to the given one.
   * @param category the given category to compare the actual Lineup's category to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's category is not equal to the given one.
   */
  public S hasCategory(org.springframework.samples.petclinic.model.Category category) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting category of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Category actualCategory = actual.getCategory();
    if (!Objects.areEqual(actualCategory, category)) {
      failWithMessage(assertjErrorMessage, actual, category, actualCategory);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Lineup's gp is equal to the given one.
   * @param gp the given gp to compare the actual Lineup's gp to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's gp is not equal to the given one.
   */
  public S hasGp(org.springframework.samples.petclinic.model.GranPremio gp) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting gp of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.GranPremio actualGp = actual.getGp();
    if (!Objects.areEqual(actualGp, gp)) {
      failWithMessage(assertjErrorMessage, actual, gp, actualGp);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Lineup's recruit1 is equal to the given one.
   * @param recruit1 the given recruit1 to compare the actual Lineup's recruit1 to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's recruit1 is not equal to the given one.
   */
  public S hasRecruit1(org.springframework.samples.petclinic.model.Recruit recruit1) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting recruit1 of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Recruit actualRecruit1 = actual.getRecruit1();
    if (!Objects.areEqual(actualRecruit1, recruit1)) {
      failWithMessage(assertjErrorMessage, actual, recruit1, actualRecruit1);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Lineup's recruit2 is equal to the given one.
   * @param recruit2 the given recruit2 to compare the actual Lineup's recruit2 to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's recruit2 is not equal to the given one.
   */
  public S hasRecruit2(org.springframework.samples.petclinic.model.Recruit recruit2) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting recruit2 of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Recruit actualRecruit2 = actual.getRecruit2();
    if (!Objects.areEqual(actualRecruit2, recruit2)) {
      failWithMessage(assertjErrorMessage, actual, recruit2, actualRecruit2);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Lineup's rider1 is equal to the given one.
   * @param rider1 the given rider1 to compare the actual Lineup's rider1 to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's rider1 is not equal to the given one.
   */
  public S hasRider1(org.springframework.samples.petclinic.model.Pilot rider1) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting rider1 of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Pilot actualRider1 = actual.getRider1();
    if (!Objects.areEqual(actualRider1, rider1)) {
      failWithMessage(assertjErrorMessage, actual, rider1, actualRider1);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Lineup's rider2 is equal to the given one.
   * @param rider2 the given rider2 to compare the actual Lineup's rider2 to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's rider2 is not equal to the given one.
   */
  public S hasRider2(org.springframework.samples.petclinic.model.Pilot rider2) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting rider2 of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Pilot actualRider2 = actual.getRider2();
    if (!Objects.areEqual(actualRider2, rider2)) {
      failWithMessage(assertjErrorMessage, actual, rider2, actualRider2);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Lineup's team is equal to the given one.
   * @param team the given team to compare the actual Lineup's team to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Lineup's team is not equal to the given one.
   */
  public S hasTeam(org.springframework.samples.petclinic.model.Team team) {
    // check that actual Lineup we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting team of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Team actualTeam = actual.getTeam();
    if (!Objects.areEqual(actualTeam, team)) {
      failWithMessage(assertjErrorMessage, actual, team, actualTeam);
    }

    // return the current assertion for method chaining
    return myself;
  }

}

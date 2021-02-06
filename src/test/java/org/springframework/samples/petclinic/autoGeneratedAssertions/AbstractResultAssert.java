package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Result;

/**
 * Abstract base class for {@link Result} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractResultAssert<S extends AbstractResultAssert<S, A>, A extends Result> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractResultAssert}</code> to make assertions on actual Result.
   * @param actual the Result we want to make assertions on.
   */
  protected AbstractResultAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Result's gp is equal to the given one.
   * @param gp the given gp to compare the actual Result's gp to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Result's gp is not equal to the given one.
   */
  public S hasGp(org.springframework.samples.petclinic.model.GranPremio gp) {
    // check that actual Result we want to make assertions on is not null.
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
   * Verifies that the actual Result's lap is equal to the given one.
   * @param lap the given lap to compare the actual Result's lap to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Result's lap is not equal to the given one.
   */


  /**
   * Verifies that the actual Result's pilot is equal to the given one.
   * @param pilot the given pilot to compare the actual Result's pilot to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Result's pilot is not equal to the given one.
   */
  public S hasPilot(org.springframework.samples.petclinic.model.Pilot pilot) {
    // check that actual Result we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting pilot of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Pilot actualPilot = actual.getPilot();
    if (!Objects.areEqual(actualPilot, pilot)) {
      failWithMessage(assertjErrorMessage, actual, pilot, actualPilot);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Result's pole is equal to the given one.
   * @param pole the given pole to compare the actual Result's pole to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Result's pole is not equal to the given one.
   */


  /**
   * Verifies that the actual Result's position is equal to the given one.
   * @param position the given position to compare the actual Result's position to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Result's position is not equal to the given one.
   */
  public S hasPosition(Integer position) {
    // check that actual Result we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting position of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualPosition = actual.getPosition();
    if (!Objects.areEqual(actualPosition, position)) {
      failWithMessage(assertjErrorMessage, actual, position, actualPosition);
    }

    // return the current assertion for method chaining
    return myself;
  }

}

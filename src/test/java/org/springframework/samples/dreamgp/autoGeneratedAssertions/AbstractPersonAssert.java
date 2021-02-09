package org.springframework.samples.dreamgp.autoGeneratedAssertions;

import org.assertj.core.util.Objects;
import org.springframework.samples.dreamgp.model.Person;

/**
 * Abstract base class for {@link Person} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractPersonAssert<S extends AbstractPersonAssert<S, A>, A extends Person> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractPersonAssert}</code> to make assertions on actual Person.
   * @param actual the Person we want to make assertions on.
   */
  protected AbstractPersonAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Person's firstName is equal to the given one.
   * @param firstName the given firstName to compare the actual Person's firstName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person's firstName is not equal to the given one.
   */
  public S hasFirstName(String firstName) {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting firstName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualFirstName = actual.getFirstName();
    if (!Objects.areEqual(actualFirstName, firstName)) {
      failWithMessage(assertjErrorMessage, actual, firstName, actualFirstName);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Person's lastName is equal to the given one.
   * @param lastName the given lastName to compare the actual Person's lastName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Person's lastName is not equal to the given one.
   */
  public S hasLastName(String lastName) {
    // check that actual Person we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualLastName = actual.getLastName();
    if (!Objects.areEqual(actualLastName, lastName)) {
      failWithMessage(assertjErrorMessage, actual, lastName, actualLastName);
    }

    // return the current assertion for method chaining
    return myself;
  }

}
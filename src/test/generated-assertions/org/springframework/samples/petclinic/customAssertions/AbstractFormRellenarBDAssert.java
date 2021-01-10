package org.springframework.samples.petclinic.customAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.FormRellenarBD;

/**
 * Abstract base class for {@link FormRellenarBD} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractFormRellenarBDAssert<S extends AbstractFormRellenarBDAssert<S, A>, A extends FormRellenarBD> extends AbstractObjectAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractFormRellenarBDAssert}</code> to make assertions on actual FormRellenarBD.
   * @param actual the FormRellenarBD we want to make assertions on.
   */
  protected AbstractFormRellenarBDAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual FormRellenarBD's anyoFinal is equal to the given one.
   * @param anyoFinal the given anyoFinal to compare the actual FormRellenarBD's anyoFinal to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FormRellenarBD's anyoFinal is not equal to the given one.
   */
  public S hasAnyoFinal(Integer anyoFinal) {
    // check that actual FormRellenarBD we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting anyoFinal of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualAnyoFinal = actual.getAnyoFinal();
    if (!Objects.areEqual(actualAnyoFinal, anyoFinal)) {
      failWithMessage(assertjErrorMessage, actual, anyoFinal, actualAnyoFinal);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual FormRellenarBD's anyoInicial is equal to the given one.
   * @param anyoInicial the given anyoInicial to compare the actual FormRellenarBD's anyoInicial to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FormRellenarBD's anyoInicial is not equal to the given one.
   */
  public S hasAnyoInicial(Integer anyoInicial) {
    // check that actual FormRellenarBD we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting anyoInicial of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualAnyoInicial = actual.getAnyoInicial();
    if (!Objects.areEqual(actualAnyoInicial, anyoInicial)) {
      failWithMessage(assertjErrorMessage, actual, anyoInicial, actualAnyoInicial);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual FormRellenarBD's category is equal to the given one.
   * @param category the given category to compare the actual FormRellenarBD's category to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FormRellenarBD's category is not equal to the given one.
   */
  public S hasCategory(org.springframework.samples.petclinic.model.Category category) {
    // check that actual FormRellenarBD we want to make assertions on is not null.
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

}

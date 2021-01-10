package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Offer;

/**
 * Abstract base class for {@link Offer} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractOfferAssert<S extends AbstractOfferAssert<S, A>, A extends Offer> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractOfferAssert}</code> to make assertions on actual Offer.
   * @param actual the Offer we want to make assertions on.
   */
  protected AbstractOfferAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Offer's price is equal to the given one.
   * @param price the given price to compare the actual Offer's price to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Offer's price is not equal to the given one.
   */
  public S hasPrice(Integer price) {
    // check that actual Offer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting price of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualPrice = actual.getPrice();
    if (!Objects.areEqual(actualPrice, price)) {
      failWithMessage(assertjErrorMessage, actual, price, actualPrice);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Offer's recruit is equal to the given one.
   * @param recruit the given recruit to compare the actual Offer's recruit to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Offer's recruit is not equal to the given one.
   */
  public S hasRecruit(org.springframework.samples.petclinic.model.Recruit recruit) {
    // check that actual Offer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting recruit of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Recruit actualRecruit = actual.getRecruit();
    if (!Objects.areEqual(actualRecruit, recruit)) {
      failWithMessage(assertjErrorMessage, actual, recruit, actualRecruit);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Offer's status is equal to the given one.
   * @param status the given status to compare the actual Offer's status to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Offer's status is not equal to the given one.
   */
  public S hasStatus(org.springframework.samples.petclinic.util.Status status) {
    // check that actual Offer we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting status of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.util.Status actualStatus = actual.getStatus();
    if (!Objects.areEqual(actualStatus, status)) {
      failWithMessage(assertjErrorMessage, actual, status, actualStatus);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Offer's team is equal to the given one.
   * @param team the given team to compare the actual Offer's team to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Offer's team is not equal to the given one.
   */
  public S hasTeam(org.springframework.samples.petclinic.model.Team team) {
    // check that actual Offer we want to make assertions on is not null.
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

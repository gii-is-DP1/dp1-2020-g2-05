package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.model.Team;

/**
 * Abstract base class for {@link Team} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractTeamAssert<S extends AbstractTeamAssert<S, A>, A extends Team> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractTeamAssert}</code> to make assertions on actual Team.
   * @param actual the Team we want to make assertions on.
   */
  protected AbstractTeamAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Team's league is equal to the given one.
   * @param league the given league to compare the actual Team's league to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Team's league is not equal to the given one.
   */
  public S hasLeague(org.springframework.samples.petclinic.model.League league) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting league of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.League actualLeague = actual.getLeague();
    if (!Objects.areEqual(actualLeague, league)) {
      failWithMessage(assertjErrorMessage, actual, league, actualLeague);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's money is equal to the given one.
   * @param money the given money to compare the actual Team's money to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Team's money is not equal to the given one.
   */
  public S hasMoney(Integer money) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting money of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualMoney = actual.getMoney();
    if (!Objects.areEqual(actualMoney, money)) {
      failWithMessage(assertjErrorMessage, actual, money, actualMoney);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's name is equal to the given one.
   * @param name the given name to compare the actual Team's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Team's name is not equal to the given one.
   */
  public S hasName(String name) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualName = actual.getName();
    if (!Objects.areEqual(actualName, name)) {
      failWithMessage(assertjErrorMessage, actual, name, actualName);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's points is equal to the given one.
   * @param points the given points to compare the actual Team's points to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Team's points is not equal to the given one.
   */
  public S hasPoints(Integer points) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting points of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualPoints = actual.getPoints();
    if (!Objects.areEqual(actualPoints, points)) {
      failWithMessage(assertjErrorMessage, actual, points, actualPoints);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's user is equal to the given one.
   * @param user the given user to compare the actual Team's user to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Team's user is not equal to the given one.
   */
  public S hasUser(org.springframework.samples.petclinic.model.User user) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting user of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.User actualUser = actual.getUser();
    if (!Objects.areEqual(actualUser, user)) {
      failWithMessage(assertjErrorMessage, actual, user, actualUser);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's lineups contains the given Lineup elements.
   * @param lineups the given elements that should be contained in actual Team's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups does not contain all given Lineup elements.
   */
  public S hasLineups(Lineup... lineups) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup varargs is not null.
    if (lineups == null) failWithMessage("Expecting lineups parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual), lineups);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's lineups contains the given Lineup elements in Collection.
   * @param lineups the given elements that should be contained in actual Team's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups does not contain all given Lineup elements.
   */
  public S hasLineups(java.util.Collection<? extends Lineup> lineups) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup collection is not null.
    if (lineups == null) {
      failWithMessage("Expecting lineups parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual), lineups.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's lineups contains <b>only</b> the given Lineup elements and nothing else in whatever order.
   * @param lineups the given elements that should be contained in actual Team's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups does not contain all given Lineup elements.
   */
  public S hasOnlyLineups(Lineup... lineups) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup varargs is not null.
    if (lineups == null) failWithMessage("Expecting lineups parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual), lineups);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's lineups contains <b>only</b> the given Lineup elements in Collection and nothing else in whatever order.
   * @param lineups the given elements that should be contained in actual Team's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups does not contain all given Lineup elements.
   */
  public S hasOnlyLineups(java.util.Collection<? extends Lineup> lineups) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup collection is not null.
    if (lineups == null) {
      failWithMessage("Expecting lineups parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual), lineups.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's lineups does not contain the given Lineup elements.
   *
   * @param lineups the given elements that should not be in actual Team's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups contains any given Lineup elements.
   */
  public S doesNotHaveLineups(Lineup... lineups) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup varargs is not null.
    if (lineups == null) failWithMessage("Expecting lineups parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual), lineups);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's lineups does not contain the given Lineup elements in Collection.
   *
   * @param lineups the given elements that should not be in actual Team's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups contains any given Lineup elements.
   */
  public S doesNotHaveLineups(java.util.Collection<? extends Lineup> lineups) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup collection is not null.
    if (lineups == null) {
      failWithMessage("Expecting lineups parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual), lineups.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team has no lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's lineups is not empty.
   */
  public S hasNoLineups() {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have lineups but had :\n  <%s>";

    // check
    if (org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual).iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("lineups", java.util.Set.class, actual));
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual Team's offer contains the given Offer elements.
   * @param offer the given elements that should be contained in actual Team's offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer does not contain all given Offer elements.
   */
  public S hasOffer(Offer... offer) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Offer varargs is not null.
    if (offer == null) failWithMessage("Expecting offer parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual), offer);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's offer contains the given Offer elements in Collection.
   * @param offer the given elements that should be contained in actual Team's offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer does not contain all given Offer elements.
   */
  public S hasOffer(java.util.Collection<? extends Offer> offer) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Offer collection is not null.
    if (offer == null) {
      failWithMessage("Expecting offer parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual), offer.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's offer contains <b>only</b> the given Offer elements and nothing else in whatever order.
   * @param offer the given elements that should be contained in actual Team's offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer does not contain all given Offer elements.
   */
  public S hasOnlyOffer(Offer... offer) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Offer varargs is not null.
    if (offer == null) failWithMessage("Expecting offer parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual), offer);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's offer contains <b>only</b> the given Offer elements in Collection and nothing else in whatever order.
   * @param offer the given elements that should be contained in actual Team's offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer does not contain all given Offer elements.
   */
  public S hasOnlyOffer(java.util.Collection<? extends Offer> offer) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Offer collection is not null.
    if (offer == null) {
      failWithMessage("Expecting offer parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual), offer.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's offer does not contain the given Offer elements.
   *
   * @param offer the given elements that should not be in actual Team's offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer contains any given Offer elements.
   */
  public S doesNotHaveOffer(Offer... offer) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Offer varargs is not null.
    if (offer == null) failWithMessage("Expecting offer parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual), offer);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's offer does not contain the given Offer elements in Collection.
   *
   * @param offer the given elements that should not be in actual Team's offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer contains any given Offer elements.
   */
  public S doesNotHaveOffer(java.util.Collection<? extends Offer> offer) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Offer collection is not null.
    if (offer == null) {
      failWithMessage("Expecting offer parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual), offer.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team has no offer.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's offer is not empty.
   */
  public S hasNoOffer() {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have offer but had :\n  <%s>";

    // check
    if (org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual).iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("offer", java.util.Set.class, actual));
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual Team's recruit contains the given Recruit elements.
   * @param recruit the given elements that should be contained in actual Team's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit does not contain all given Recruit elements.
   */
  public S hasRecruit(Recruit... recruit) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit varargs is not null.
    if (recruit == null) failWithMessage("Expecting recruit parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's recruit contains the given Recruit elements in Collection.
   * @param recruit the given elements that should be contained in actual Team's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit does not contain all given Recruit elements.
   */
  public S hasRecruit(java.util.Collection<? extends Recruit> recruit) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit collection is not null.
    if (recruit == null) {
      failWithMessage("Expecting recruit parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's recruit contains <b>only</b> the given Recruit elements and nothing else in whatever order.
   * @param recruit the given elements that should be contained in actual Team's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit does not contain all given Recruit elements.
   */
  public S hasOnlyRecruit(Recruit... recruit) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit varargs is not null.
    if (recruit == null) failWithMessage("Expecting recruit parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's recruit contains <b>only</b> the given Recruit elements in Collection and nothing else in whatever order.
   * @param recruit the given elements that should be contained in actual Team's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit does not contain all given Recruit elements.
   */
  public S hasOnlyRecruit(java.util.Collection<? extends Recruit> recruit) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit collection is not null.
    if (recruit == null) {
      failWithMessage("Expecting recruit parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's recruit does not contain the given Recruit elements.
   *
   * @param recruit the given elements that should not be in actual Team's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit contains any given Recruit elements.
   */
  public S doesNotHaveRecruit(Recruit... recruit) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit varargs is not null.
    if (recruit == null) failWithMessage("Expecting recruit parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team's recruit does not contain the given Recruit elements in Collection.
   *
   * @param recruit the given elements that should not be in actual Team's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit contains any given Recruit elements.
   */
  public S doesNotHaveRecruit(java.util.Collection<? extends Recruit> recruit) {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit collection is not null.
    if (recruit == null) {
      failWithMessage("Expecting recruit parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Team has no recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Team's recruit is not empty.
   */
  public S hasNoRecruit() {
    // check that actual Team we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have recruit but had :\n  <%s>";

    // check
    if (org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual).iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual));
    }

    // return the current assertion for method chaining
    return myself;
  }


}

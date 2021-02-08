package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Result;

/**
 * Abstract base class for {@link Pilot} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractPilotAssert<S extends AbstractPilotAssert<S, A>, A extends Pilot> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractPilotAssert}</code> to make assertions on actual Pilot.
   * @param actual the Pilot we want to make assertions on.
   */
  protected AbstractPilotAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Pilot's baseValue is equal to the given one.
   * @param baseValue the given baseValue to compare the actual Pilot's baseValue to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pilot's baseValue is not equal to the given one.
   */
  public S hasBaseValue(Integer baseValue) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting baseValue of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualBaseValue = actual.getBaseValue();
    if (!Objects.areEqual(actualBaseValue, baseValue)) {
      failWithMessage(assertjErrorMessage, actual, baseValue, actualBaseValue);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's category is equal to the given one.
   * @param category the given category to compare the actual Pilot's category to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pilot's category is not equal to the given one.
   */
  public S hasCategory(org.springframework.samples.petclinic.model.Category category) {
    // check that actual Pilot we want to make assertions on is not null.
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
   * Verifies that the actual Pilot's dorsal is equal to the given one.
   * @param dorsal the given dorsal to compare the actual Pilot's dorsal to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pilot's dorsal is not equal to the given one.
   */
  public S hasDorsal(String dorsal) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting dorsal of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualDorsal = actual.getPoints();
    if (!Objects.areEqual(actualDorsal, dorsal)) {
      failWithMessage(assertjErrorMessage, actual, dorsal, actualDorsal);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's lastName is equal to the given one.
   * @param lastName the given lastName to compare the actual Pilot's lastName to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pilot's lastName is not equal to the given one.
   */
  public S hasLastName(String lastName) {
    // check that actual Pilot we want to make assertions on is not null.
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

  /**
   * Verifies that the actual Pilot's name is equal to the given one.
   * @param name the given name to compare the actual Pilot's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pilot's name is not equal to the given one.
   */
  public S hasName(String name) {
    // check that actual Pilot we want to make assertions on is not null.
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
   * Verifies that the actual Pilot's nationality is equal to the given one.
   * @param nationality the given nationality to compare the actual Pilot's nationality to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Pilot's nationality is not equal to the given one.
   */
  public S hasNationality(String nationality) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting nationality of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualNationality = actual.getNationality();
    if (!Objects.areEqual(actualNationality, nationality)) {
      failWithMessage(assertjErrorMessage, actual, nationality, actualNationality);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's results contains the given Result elements.
   * @param results the given elements that should be contained in actual Pilot's results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results does not contain all given Result elements.
   */
  public S hasResults(Result... results) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Result varargs is not null.
    if (results == null) failWithMessage("Expecting results parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getResults(), results);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's results contains the given Result elements in Collection.
   * @param results the given elements that should be contained in actual Pilot's results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results does not contain all given Result elements.
   */
  public S hasResults(java.util.Collection<? extends Result> results) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Result collection is not null.
    if (results == null) {
      failWithMessage("Expecting results parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getResults(), results.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's results contains <b>only</b> the given Result elements and nothing else in whatever order.
   * @param results the given elements that should be contained in actual Pilot's results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results does not contain all given Result elements.
   */
  public S hasOnlyResults(Result... results) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Result varargs is not null.
    if (results == null) failWithMessage("Expecting results parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getResults(), results);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's results contains <b>only</b> the given Result elements in Collection and nothing else in whatever order.
   * @param results the given elements that should be contained in actual Pilot's results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results does not contain all given Result elements.
   */
  public S hasOnlyResults(java.util.Collection<? extends Result> results) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Result collection is not null.
    if (results == null) {
      failWithMessage("Expecting results parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getResults(), results.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's results does not contain the given Result elements.
   *
   * @param results the given elements that should not be in actual Pilot's results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results contains any given Result elements.
   */
  public S doesNotHaveResults(Result... results) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Result varargs is not null.
    if (results == null) failWithMessage("Expecting results parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getResults(), results);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's results does not contain the given Result elements in Collection.
   *
   * @param results the given elements that should not be in actual Pilot's results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results contains any given Result elements.
   */
  public S doesNotHaveResults(java.util.Collection<? extends Result> results) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Result collection is not null.
    if (results == null) {
      failWithMessage("Expecting results parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getResults(), results.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot has no results.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's results is not empty.
   */
  public S hasNoResults() {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have results but had :\n  <%s>";

    // check
    if (actual.getResults().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getResults());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual Pilot's recruit contains the given Recruit elements.
   * @param recruit the given elements that should be contained in actual Pilot's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit does not contain all given Recruit elements.
   */
  public S hasRecruit(Recruit... recruit) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit varargs is not null.
    if (recruit == null) failWithMessage("Expecting recruit parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's recruit contains the given Recruit elements in Collection.
   * @param recruit the given elements that should be contained in actual Pilot's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit does not contain all given Recruit elements.
   */
  public S hasRecruit(java.util.Collection<? extends Recruit> recruit) {
    // check that actual Pilot we want to make assertions on is not null.
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
   * Verifies that the actual Pilot's recruit contains <b>only</b> the given Recruit elements and nothing else in whatever order.
   * @param recruit the given elements that should be contained in actual Pilot's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit does not contain all given Recruit elements.
   */
  public S hasOnlyRecruit(Recruit... recruit) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit varargs is not null.
    if (recruit == null) failWithMessage("Expecting recruit parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's recruit contains <b>only</b> the given Recruit elements in Collection and nothing else in whatever order.
   * @param recruit the given elements that should be contained in actual Pilot's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit does not contain all given Recruit elements.
   */
  public S hasOnlyRecruit(java.util.Collection<? extends Recruit> recruit) {
    // check that actual Pilot we want to make assertions on is not null.
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
   * Verifies that the actual Pilot's recruit does not contain the given Recruit elements.
   *
   * @param recruit the given elements that should not be in actual Pilot's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit contains any given Recruit elements.
   */
  public S doesNotHaveRecruit(Recruit... recruit) {
    // check that actual Pilot we want to make assertions on is not null.
    isNotNull();

    // check that given Recruit varargs is not null.
    if (recruit == null) failWithMessage("Expecting recruit parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("recruit", java.util.Set.class, actual), recruit);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual Pilot's recruit does not contain the given Recruit elements in Collection.
   *
   * @param recruit the given elements that should not be in actual Pilot's recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit contains any given Recruit elements.
   */
  public S doesNotHaveRecruit(java.util.Collection<? extends Recruit> recruit) {
    // check that actual Pilot we want to make assertions on is not null.
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
   * Verifies that the actual Pilot has no recruit.
   * @return this assertion object.
   * @throws AssertionError if the actual Pilot's recruit is not empty.
   */
  public S hasNoRecruit() {
    // check that actual Pilot we want to make assertions on is not null.
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

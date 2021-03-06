package org.springframework.samples.dreamgp.autoGeneratedAssertions;

import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.GranPremio;
import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.model.Result;

/**
 * Abstract base class for {@link GranPremio} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractGranPremioAssert<S extends AbstractGranPremioAssert<S, A>, A extends GranPremio> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractGranPremioAssert}</code> to make assertions on actual GranPremio.
   * @param actual the GranPremio we want to make assertions on.
   */
  protected AbstractGranPremioAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual GranPremio's calendar is equal to the given one.
   * @param calendar the given calendar to compare the actual GranPremio's calendar to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's calendar is not equal to the given one.
   */
  public S hasCalendar(Boolean calendar) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting calendar of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Boolean actualCalendar = actual.getCalendar();
    if (!Objects.areEqual(actualCalendar, calendar)) {
      failWithMessage(assertjErrorMessage, actual, calendar, actualCalendar);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's circuit is equal to the given one.
   * @param circuit the given circuit to compare the actual GranPremio's circuit to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's circuit is not equal to the given one.
   */
  public S hasCircuit(String circuit) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting circuit of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualCircuit = actual.getCircuit();
    if (!Objects.areEqual(actualCircuit, circuit)) {
      failWithMessage(assertjErrorMessage, actual, circuit, actualCircuit);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's date0 is equal to the given one.
   * @param date0 the given date0 to compare the actual GranPremio's date0 to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's date0 is not equal to the given one.
   */
  public S hasDate0(java.time.LocalDate date0) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting date0 of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualDate0 = actual.getDate0();
    if (!Objects.areEqual(actualDate0, date0)) {
      failWithMessage(assertjErrorMessage, actual, date0, actualDate0);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's hasBeenRun is equal to the given one.
   * @param hasBeenRun the given hasBeenRun to compare the actual GranPremio's hasBeenRun to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's hasBeenRun is not equal to the given one.
   */
  public S hasHasBeenRun(Boolean hasBeenRun) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting hasBeenRun of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Boolean actualHasBeenRun = actual.getHasBeenRun();
    if (!Objects.areEqual(actualHasBeenRun, hasBeenRun)) {
      failWithMessage(assertjErrorMessage, actual, hasBeenRun, actualHasBeenRun);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's lineups contains the given Lineup elements.
   * @param lineups the given elements that should be contained in actual GranPremio's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups does not contain all given Lineup elements.
   */
  public S hasLineups(Lineup... lineups) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup varargs is not null.
    if (lineups == null) failWithMessage("Expecting lineups parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getLineups(), lineups);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's lineups contains the given Lineup elements in Collection.
   * @param lineups the given elements that should be contained in actual GranPremio's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups does not contain all given Lineup elements.
   */
  public S hasLineups(java.util.Collection<? extends Lineup> lineups) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup collection is not null.
    if (lineups == null) {
      failWithMessage("Expecting lineups parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getLineups(), lineups.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's lineups contains <b>only</b> the given Lineup elements and nothing else in whatever order.
   * @param lineups the given elements that should be contained in actual GranPremio's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups does not contain all given Lineup elements.
   */
  public S hasOnlyLineups(Lineup... lineups) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup varargs is not null.
    if (lineups == null) failWithMessage("Expecting lineups parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getLineups(), lineups);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's lineups contains <b>only</b> the given Lineup elements in Collection and nothing else in whatever order.
   * @param lineups the given elements that should be contained in actual GranPremio's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups does not contain all given Lineup elements.
   */
  public S hasOnlyLineups(java.util.Collection<? extends Lineup> lineups) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup collection is not null.
    if (lineups == null) {
      failWithMessage("Expecting lineups parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getLineups(), lineups.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's lineups does not contain the given Lineup elements.
   *
   * @param lineups the given elements that should not be in actual GranPremio's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups contains any given Lineup elements.
   */
  public S doesNotHaveLineups(Lineup... lineups) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup varargs is not null.
    if (lineups == null) failWithMessage("Expecting lineups parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getLineups(), lineups);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's lineups does not contain the given Lineup elements in Collection.
   *
   * @param lineups the given elements that should not be in actual GranPremio's lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups contains any given Lineup elements.
   */
  public S doesNotHaveLineups(java.util.Collection<? extends Lineup> lineups) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Lineup collection is not null.
    if (lineups == null) {
      failWithMessage("Expecting lineups parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getLineups(), lineups.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio has no lineups.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's lineups is not empty.
   */
  public S hasNoLineups() {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have lineups but had :\n  <%s>";

    // check
    if (actual.getLineups().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getLineups());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual GranPremio's raceCode is equal to the given one.
   * @param raceCode the given raceCode to compare the actual GranPremio's raceCode to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's raceCode is not equal to the given one.
   */
  public S hasRaceCode(String raceCode) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting raceCode of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualRaceCode = actual.getRaceCode();
    if (!Objects.areEqual(actualRaceCode, raceCode)) {
      failWithMessage(assertjErrorMessage, actual, raceCode, actualRaceCode);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's record is equal to the given one.
   * @param record the given record to compare the actual GranPremio's record to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's record is not equal to the given one.
   */
  public S hasRecord(org.springframework.samples.dreamgp.model.Record record, Category category) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting record of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.dreamgp.model.Record actualRecord = actual.getRecord(category);
    if (!Objects.areEqual(actualRecord, record)) {
      failWithMessage(assertjErrorMessage, actual, record, actualRecord);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's results contains the given Result elements.
   * @param results the given elements that should be contained in actual GranPremio's results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results does not contain all given Result elements.
   */
  public S hasResults(Result... results) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Result varargs is not null.
    if (results == null) failWithMessage("Expecting results parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getResults(), results);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's results contains the given Result elements in Collection.
   * @param results the given elements that should be contained in actual GranPremio's results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results does not contain all given Result elements.
   */
  public S hasResults(java.util.Collection<? extends Result> results) {
    // check that actual GranPremio we want to make assertions on is not null.
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
   * Verifies that the actual GranPremio's results contains <b>only</b> the given Result elements and nothing else in whatever order.
   * @param results the given elements that should be contained in actual GranPremio's results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results does not contain all given Result elements.
   */
  public S hasOnlyResults(Result... results) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Result varargs is not null.
    if (results == null) failWithMessage("Expecting results parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getResults(), results);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's results contains <b>only</b> the given Result elements in Collection and nothing else in whatever order.
   * @param results the given elements that should be contained in actual GranPremio's results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results does not contain all given Result elements.
   */
  public S hasOnlyResults(java.util.Collection<? extends Result> results) {
    // check that actual GranPremio we want to make assertions on is not null.
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
   * Verifies that the actual GranPremio's results does not contain the given Result elements.
   *
   * @param results the given elements that should not be in actual GranPremio's results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results contains any given Result elements.
   */
  public S doesNotHaveResults(Result... results) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // check that given Result varargs is not null.
    if (results == null) failWithMessage("Expecting results parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getResults(), results);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio's results does not contain the given Result elements in Collection.
   *
   * @param results the given elements that should not be in actual GranPremio's results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results contains any given Result elements.
   */
  public S doesNotHaveResults(java.util.Collection<? extends Result> results) {
    // check that actual GranPremio we want to make assertions on is not null.
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
   * Verifies that the actual GranPremio has no results.
   * @return this assertion object.
   * @throws AssertionError if the actual GranPremio's results is not empty.
   */
  public S hasNoResults() {
    // check that actual GranPremio we want to make assertions on is not null.
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
   * Verifies that the actual GranPremio's site is equal to the given one.
   * @param site the given site to compare the actual GranPremio's site to.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio's site is not equal to the given one.
   */
  public S hasSite(String site) {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting site of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualSite = actual.getSite();
    if (!Objects.areEqual(actualSite, site)) {
      failWithMessage(assertjErrorMessage, actual, site, actualSite);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio has been run.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio does not have been run.
   */
  public S hasBeenRun() {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // null safe check
    if (Objects.areEqual(Boolean.FALSE, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("hasBeenRun", Boolean.class, actual))) {
      failWithMessage("\nExpecting that actual GranPremio has been run but does not have.");
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual GranPremio does not have been run.
   * @return this assertion object.
   * @throws AssertionError - if the actual GranPremio has been run.
   */
  public S doesNotHaveBeenRun() {
    // check that actual GranPremio we want to make assertions on is not null.
    isNotNull();

    // null safe check
    if (Objects.areEqual(Boolean.TRUE, org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("hasBeenRun", Boolean.class, actual))) {
      failWithMessage("\nExpecting that actual GranPremio does not have been run but has.");
    }

    // return the current assertion for method chaining
    return myself;
  }

}

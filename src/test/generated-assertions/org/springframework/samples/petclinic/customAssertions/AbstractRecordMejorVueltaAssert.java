package org.springframework.samples.petclinic.customAssertions;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.AbstractBaseEntityAssert;
import org.springframework.samples.petclinic.model.RecordMejorVuelta;

/**
 * Abstract base class for {@link RecordMejorVuelta} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractRecordMejorVueltaAssert<S extends AbstractRecordMejorVueltaAssert<S, A>, A extends RecordMejorVuelta> extends AbstractBaseEntityAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractRecordMejorVueltaAssert}</code> to make assertions on actual RecordMejorVuelta.
   * @param actual the RecordMejorVuelta we want to make assertions on.
   */
  protected AbstractRecordMejorVueltaAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual RecordMejorVuelta's anyo is equal to the given one.
   * @param anyo the given anyo to compare the actual RecordMejorVuelta's anyo to.
   * @return this assertion object.
   * @throws AssertionError - if the actual RecordMejorVuelta's anyo is not equal to the given one.
   */
  public S hasAnyo(Integer anyo) {
    // check that actual RecordMejorVuelta we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting anyo of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualAnyo = actual.getAnyo();
    if (!Objects.areEqual(actualAnyo, anyo)) {
      failWithMessage(assertjErrorMessage, actual, anyo, actualAnyo);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual RecordMejorVuelta's kmh is equal to the given one.
   * @param kmh the given kmh to compare the actual RecordMejorVuelta's kmh to.
   * @return this assertion object.
   * @throws AssertionError - if the actual RecordMejorVuelta's kmh is not equal to the given one.
   */
  public S hasKmh(Double kmh) {
    // check that actual RecordMejorVuelta we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting kmh of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Double actualKmh = actual.getKmh();
    if (!Objects.areEqual(actualKmh, kmh)) {
      failWithMessage(assertjErrorMessage, actual, kmh, actualKmh);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual RecordMejorVuelta's kmh is close to the given value by less than the given offset.
   * <p>
   * If difference is equal to the offset value, assertion is considered successful.
   * @param kmh the value to compare the actual RecordMejorVuelta's kmh to.
   * @param assertjOffset the given offset.
   * @return this assertion object.
   * @throws AssertionError - if the actual RecordMejorVuelta's kmh is not close enough to the given value.
   */
  public S hasKmhCloseTo(Double kmh, Double assertjOffset) {
    // check that actual RecordMejorVuelta we want to make assertions on is not null.
    isNotNull();

    Double actualKmh = actual.getKmh();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = String.format("\nExpecting kmh:\n  <%s>\nto be close to:\n  <%s>\nby less than <%s> but difference was <%s>",
                                               actualKmh, kmh, assertjOffset, Math.abs(kmh - actualKmh));

    // check
    Assertions.assertThat(actualKmh).overridingErrorMessage(assertjErrorMessage).isCloseTo(kmh, Assertions.within(assertjOffset));

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual RecordMejorVuelta's nombrePiloto is equal to the given one.
   * @param nombrePiloto the given nombrePiloto to compare the actual RecordMejorVuelta's nombrePiloto to.
   * @return this assertion object.
   * @throws AssertionError - if the actual RecordMejorVuelta's nombrePiloto is not equal to the given one.
   */
  public S hasNombrePiloto(String nombrePiloto) {
    // check that actual RecordMejorVuelta we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting nombrePiloto of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualNombrePiloto = actual.getNombrePiloto();
    if (!Objects.areEqual(actualNombrePiloto, nombrePiloto)) {
      failWithMessage(assertjErrorMessage, actual, nombrePiloto, actualNombrePiloto);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual RecordMejorVuelta's tiempo is equal to the given one.
   * @param tiempo the given tiempo to compare the actual RecordMejorVuelta's tiempo to.
   * @return this assertion object.
   * @throws AssertionError - if the actual RecordMejorVuelta's tiempo is not equal to the given one.
   */
  public S hasTiempo(Integer tiempo) {
    // check that actual RecordMejorVuelta we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting tiempo of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualTiempo = actual.getTiempo();
    if (!Objects.areEqual(actualTiempo, tiempo)) {
      failWithMessage(assertjErrorMessage, actual, tiempo, actualTiempo);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual RecordMejorVuelta's record is equal to the given one.
   * @param record the given record to compare the actual RecordMejorVuelta's record to.
   * @return this assertion object.
   * @throws AssertionError - if the actual RecordMejorVuelta's record is not equal to the given one.
   */
  public S hasRecord(org.springframework.samples.petclinic.model.Record record) {
    // check that actual RecordMejorVuelta we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting record of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.springframework.samples.petclinic.model.Record actualRecord = org.assertj.core.util.introspection.FieldSupport.EXTRACTION.fieldValue("record", org.springframework.samples.petclinic.model.Record.class, actual);
    if (!Objects.areEqual(actualRecord, record)) {
      failWithMessage(assertjErrorMessage, actual, record, actualRecord);
    }

    // return the current assertion for method chaining
    return myself;
  }

}

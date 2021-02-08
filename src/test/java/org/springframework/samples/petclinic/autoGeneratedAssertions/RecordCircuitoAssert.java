package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.springframework.samples.petclinic.model.RecordCircuito;

/**
 * {@link RecordCircuito} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractRecordCircuitoAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class RecordCircuitoAssert extends AbstractRecordCircuitoAssert<RecordCircuitoAssert, RecordCircuito> {

  /**
   * Creates a new <code>{@link RecordCircuitoAssert}</code> to make assertions on actual RecordCircuito.
   * @param actual the RecordCircuito we want to make assertions on.
   */
  public RecordCircuitoAssert(RecordCircuito actual) {
    super(actual, RecordCircuitoAssert.class);
  }

  /**
   * An entry point for RecordCircuitoAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myRecordCircuito)</code> and get specific assertion with code completion.
   * @param actual the RecordCircuito we want to make assertions on.
   * @return a new <code>{@link RecordCircuitoAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static RecordCircuitoAssert assertThat(RecordCircuito actual) {
    return new RecordCircuitoAssert(actual);
  }
}

package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.springframework.samples.petclinic.model.RecordPole;

/**
 * {@link RecordPole} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractRecordPoleAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class RecordPoleAssert extends AbstractRecordPoleAssert<RecordPoleAssert, RecordPole> {

  /**
   * Creates a new <code>{@link RecordPoleAssert}</code> to make assertions on actual RecordPole.
   * @param actual the RecordPole we want to make assertions on.
   */
  public RecordPoleAssert(RecordPole actual) {
    super(actual, RecordPoleAssert.class);
  }

  /**
   * An entry point for RecordPoleAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myRecordPole)</code> and get specific assertion with code completion.
   * @param actual the RecordPole we want to make assertions on.
   * @return a new <code>{@link RecordPoleAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static RecordPoleAssert assertThat(RecordPole actual) {
    return new RecordPoleAssert(actual);
  }
}

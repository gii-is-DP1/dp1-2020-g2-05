package org.springframework.samples.petclinic.customAssertions;

import org.springframework.samples.petclinic.model.FormRellenarBD;

/**
 * {@link FormRellenarBD} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractFormRellenarBDAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class FormRellenarBDAssert extends AbstractFormRellenarBDAssert<FormRellenarBDAssert, FormRellenarBD> {

  /**
   * Creates a new <code>{@link FormRellenarBDAssert}</code> to make assertions on actual FormRellenarBD.
   * @param actual the FormRellenarBD we want to make assertions on.
   */
  public FormRellenarBDAssert(FormRellenarBD actual) {
    super(actual, FormRellenarBDAssert.class);
  }

  /**
   * An entry point for FormRellenarBDAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myFormRellenarBD)</code> and get specific assertion with code completion.
   * @param actual the FormRellenarBD we want to make assertions on.
   * @return a new <code>{@link FormRellenarBDAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static FormRellenarBDAssert assertThat(FormRellenarBD actual) {
    return new FormRellenarBDAssert(actual);
  }
}

package org.springframework.samples.dreamgp.autoGeneratedAssertions;

import org.springframework.samples.dreamgp.model.TablaConsultas;

/**
 * {@link TablaConsultas} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractTablaConsultasAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class TablaConsultasAssert extends AbstractTablaConsultasAssert<TablaConsultasAssert, TablaConsultas> {

  /**
   * Creates a new <code>{@link TablaConsultasAssert}</code> to make assertions on actual TablaConsultas.
   * @param actual the TablaConsultas we want to make assertions on.
   */
  public TablaConsultasAssert(TablaConsultas actual) {
    super(actual, TablaConsultasAssert.class);
  }

  /**
   * An entry point for TablaConsultasAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myTablaConsultas)</code> and get specific assertion with code completion.
   * @param actual the TablaConsultas we want to make assertions on.
   * @return a new <code>{@link TablaConsultasAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static TablaConsultasAssert assertThat(TablaConsultas actual) {
    return new TablaConsultasAssert(actual);
  }
}

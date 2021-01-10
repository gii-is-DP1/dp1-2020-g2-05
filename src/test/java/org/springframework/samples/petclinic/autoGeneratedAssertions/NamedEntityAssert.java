package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.springframework.samples.petclinic.model.NamedEntity;

/**
 * {@link NamedEntity} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractNamedEntityAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class NamedEntityAssert extends AbstractNamedEntityAssert<NamedEntityAssert, NamedEntity> {

  /**
   * Creates a new <code>{@link NamedEntityAssert}</code> to make assertions on actual NamedEntity.
   * @param actual the NamedEntity we want to make assertions on.
   */
  public NamedEntityAssert(NamedEntity actual) {
    super(actual, NamedEntityAssert.class);
  }

  /**
   * An entry point for NamedEntityAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myNamedEntity)</code> and get specific assertion with code completion.
   * @param actual the NamedEntity we want to make assertions on.
   * @return a new <code>{@link NamedEntityAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static NamedEntityAssert assertThat(NamedEntity actual) {
    return new NamedEntityAssert(actual);
  }
}

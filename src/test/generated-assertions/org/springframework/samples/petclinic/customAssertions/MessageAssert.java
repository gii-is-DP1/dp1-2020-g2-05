package org.springframework.samples.petclinic.customAssertions;

import org.springframework.samples.petclinic.model.Message;

/**
 * {@link Message} specific assertions - Generated by CustomAssertionGenerator.
 *
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, 
 * extend {@link AbstractMessageAssert} instead.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class MessageAssert extends AbstractMessageAssert<MessageAssert, Message> {

  /**
   * Creates a new <code>{@link MessageAssert}</code> to make assertions on actual Message.
   * @param actual the Message we want to make assertions on.
   */
  public MessageAssert(Message actual) {
    super(actual, MessageAssert.class);
  }

  /**
   * An entry point for MessageAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myMessage)</code> and get specific assertion with code completion.
   * @param actual the Message we want to make assertions on.
   * @return a new <code>{@link MessageAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static MessageAssert assertThat(Message actual) {
    return new MessageAssert(actual);
  }
}

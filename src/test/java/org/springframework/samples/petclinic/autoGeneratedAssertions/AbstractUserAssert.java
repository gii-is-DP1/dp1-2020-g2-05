package org.springframework.samples.petclinic.autoGeneratedAssertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;


/**
 * Abstract base class for {@link User} specific assertions - Generated by CustomAssertionGenerator.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public abstract class AbstractUserAssert<S extends AbstractUserAssert<S, A>, A extends User> extends AbstractObjectAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractUserAssert}</code> to make assertions on actual User.
   * @param actual the User we want to make assertions on.
   */
  protected AbstractUserAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual User's authorities contains the given Authorities elements.
   * @param authorities the given elements that should be contained in actual User's authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities does not contain all given Authorities elements.
   */
  public S hasAuthorities(Authorities... authorities) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Authorities varargs is not null.
    if (authorities == null) failWithMessage("Expecting authorities parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getAuthorities(), authorities);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's authorities contains the given Authorities elements in Collection.
   * @param authorities the given elements that should be contained in actual User's authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities does not contain all given Authorities elements.
   */
  public S hasAuthorities(java.util.Collection<? extends Authorities> authorities) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Authorities collection is not null.
    if (authorities == null) {
      failWithMessage("Expecting authorities parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getAuthorities(), authorities.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's authorities contains <b>only</b> the given Authorities elements and nothing else in whatever order.
   * @param authorities the given elements that should be contained in actual User's authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities does not contain all given Authorities elements.
   */
  public S hasOnlyAuthorities(Authorities... authorities) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Authorities varargs is not null.
    if (authorities == null) failWithMessage("Expecting authorities parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getAuthorities(), authorities);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's authorities contains <b>only</b> the given Authorities elements in Collection and nothing else in whatever order.
   * @param authorities the given elements that should be contained in actual User's authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities does not contain all given Authorities elements.
   */
  public S hasOnlyAuthorities(java.util.Collection<? extends Authorities> authorities) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Authorities collection is not null.
    if (authorities == null) {
      failWithMessage("Expecting authorities parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getAuthorities(), authorities.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's authorities does not contain the given Authorities elements.
   *
   * @param authorities the given elements that should not be in actual User's authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities contains any given Authorities elements.
   */
  public S doesNotHaveAuthorities(Authorities... authorities) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Authorities varargs is not null.
    if (authorities == null) failWithMessage("Expecting authorities parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getAuthorities(), authorities);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's authorities does not contain the given Authorities elements in Collection.
   *
   * @param authorities the given elements that should not be in actual User's authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities contains any given Authorities elements.
   */
  public S doesNotHaveAuthorities(java.util.Collection<? extends Authorities> authorities) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Authorities collection is not null.
    if (authorities == null) {
      failWithMessage("Expecting authorities parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getAuthorities(), authorities.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User has no authorities.
   * @return this assertion object.
   * @throws AssertionError if the actual User's authorities is not empty.
   */
  public S hasNoAuthorities() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have authorities but had :\n  <%s>";

    // check
    if (actual.getAuthorities().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getAuthorities());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual User's email is equal to the given one.
   * @param email the given email to compare the actual User's email to.
   * @return this assertion object.
   * @throws AssertionError - if the actual User's email is not equal to the given one.
   */
  public S hasEmail(String email) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting email of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualEmail = actual.getEmail();
    if (!Objects.areEqual(actualEmail, email)) {
      failWithMessage(assertjErrorMessage, actual, email, actualEmail);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User is enabled.
   * @return this assertion object.
   * @throws AssertionError - if the actual User is not enabled.
   */
  public S isEnabled() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is true
    if (!actual.isEnabled()) {
      failWithMessage("\nExpecting that actual User is enabled but is not.");
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User is not enabled.
   * @return this assertion object.
   * @throws AssertionError - if the actual User is enabled.
   */
  public S isNotEnabled() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that property call/field access is false
    if (actual.isEnabled()) {
      failWithMessage("\nExpecting that actual User is not enabled but is.");
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's friends contains the given User elements.
   * @param friends the given elements that should be contained in actual User's friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends does not contain all given User elements.
   */
  public S hasFriends(User... friends) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given User varargs is not null.
    if (friends == null) failWithMessage("Expecting friends parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getFriends(), friends);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's friends contains the given User elements in Collection.
   * @param friends the given elements that should be contained in actual User's friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends does not contain all given User elements.
   */
  public S hasFriends(java.util.Collection<? extends User> friends) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given User collection is not null.
    if (friends == null) {
      failWithMessage("Expecting friends parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getFriends(), friends.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's friends contains <b>only</b> the given User elements and nothing else in whatever order.
   * @param friends the given elements that should be contained in actual User's friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends does not contain all given User elements.
   */
  public S hasOnlyFriends(User... friends) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given User varargs is not null.
    if (friends == null) failWithMessage("Expecting friends parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getFriends(), friends);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's friends contains <b>only</b> the given User elements in Collection and nothing else in whatever order.
   * @param friends the given elements that should be contained in actual User's friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends does not contain all given User elements.
   */
  public S hasOnlyFriends(java.util.Collection<? extends User> friends) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given User collection is not null.
    if (friends == null) {
      failWithMessage("Expecting friends parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getFriends(), friends.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's friends does not contain the given User elements.
   *
   * @param friends the given elements that should not be in actual User's friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends contains any given User elements.
   */
  public S doesNotHaveFriends(User... friends) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given User varargs is not null.
    if (friends == null) failWithMessage("Expecting friends parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getFriends(), friends);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's friends does not contain the given User elements in Collection.
   *
   * @param friends the given elements that should not be in actual User's friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends contains any given User elements.
   */
  public S doesNotHaveFriends(java.util.Collection<? extends User> friends) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given User collection is not null.
    if (friends == null) {
      failWithMessage("Expecting friends parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getFriends(), friends.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User has no friends.
   * @return this assertion object.
   * @throws AssertionError if the actual User's friends is not empty.
   */
  public S hasNoFriends() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have friends but had :\n  <%s>";

    // check
    if (actual.getFriends().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getFriends());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual User's imgperfil is equal to the given one.
   * @param imgperfil the given imgperfil to compare the actual User's imgperfil to.
   * @return this assertion object.
   * @throws AssertionError - if the actual User's imgperfil is not equal to the given one.
   */
  public S hasImgperfil(String imgperfil) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting imgperfil of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualImgperfil = actual.getImgperfil();
    if (!Objects.areEqual(actualImgperfil, imgperfil)) {
      failWithMessage(assertjErrorMessage, actual, imgperfil, actualImgperfil);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_received contains the given Message elements.
   * @param messages_received the given elements that should be contained in actual User's messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received does not contain all given Message elements.
   */
  public S hasMessages_received(Message... messages_received) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message varargs is not null.
    if (messages_received == null) failWithMessage("Expecting messages_received parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getMessages_received(), messages_received);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_received contains the given Message elements in Collection.
   * @param messages_received the given elements that should be contained in actual User's messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received does not contain all given Message elements.
   */
  public S hasMessages_received(java.util.Collection<? extends Message> messages_received) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message collection is not null.
    if (messages_received == null) {
      failWithMessage("Expecting messages_received parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getMessages_received(), messages_received.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_received contains <b>only</b> the given Message elements and nothing else in whatever order.
   * @param messages_received the given elements that should be contained in actual User's messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received does not contain all given Message elements.
   */
  public S hasOnlyMessages_received(Message... messages_received) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message varargs is not null.
    if (messages_received == null) failWithMessage("Expecting messages_received parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getMessages_received(), messages_received);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_received contains <b>only</b> the given Message elements in Collection and nothing else in whatever order.
   * @param messages_received the given elements that should be contained in actual User's messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received does not contain all given Message elements.
   */
  public S hasOnlyMessages_received(java.util.Collection<? extends Message> messages_received) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message collection is not null.
    if (messages_received == null) {
      failWithMessage("Expecting messages_received parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getMessages_received(), messages_received.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_received does not contain the given Message elements.
   *
   * @param messages_received the given elements that should not be in actual User's messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received contains any given Message elements.
   */
  public S doesNotHaveMessages_received(Message... messages_received) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message varargs is not null.
    if (messages_received == null) failWithMessage("Expecting messages_received parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getMessages_received(), messages_received);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_received does not contain the given Message elements in Collection.
   *
   * @param messages_received the given elements that should not be in actual User's messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received contains any given Message elements.
   */
  public S doesNotHaveMessages_received(java.util.Collection<? extends Message> messages_received) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message collection is not null.
    if (messages_received == null) {
      failWithMessage("Expecting messages_received parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getMessages_received(), messages_received.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User has no messages_received.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_received is not empty.
   */
  public S hasNoMessages_received() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have messages_received but had :\n  <%s>";

    // check
    if (actual.getMessages_received().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getMessages_received());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual User's messages_send contains the given Message elements.
   * @param messages_send the given elements that should be contained in actual User's messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send does not contain all given Message elements.
   */
  public S hasMessages_send(Message... messages_send) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message varargs is not null.
    if (messages_send == null) failWithMessage("Expecting messages_send parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getMessages_send(), messages_send);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_send contains the given Message elements in Collection.
   * @param messages_send the given elements that should be contained in actual User's messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send does not contain all given Message elements.
   */
  public S hasMessages_send(java.util.Collection<? extends Message> messages_send) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message collection is not null.
    if (messages_send == null) {
      failWithMessage("Expecting messages_send parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getMessages_send(), messages_send.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_send contains <b>only</b> the given Message elements and nothing else in whatever order.
   * @param messages_send the given elements that should be contained in actual User's messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send does not contain all given Message elements.
   */
  public S hasOnlyMessages_send(Message... messages_send) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message varargs is not null.
    if (messages_send == null) failWithMessage("Expecting messages_send parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getMessages_send(), messages_send);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_send contains <b>only</b> the given Message elements in Collection and nothing else in whatever order.
   * @param messages_send the given elements that should be contained in actual User's messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send does not contain all given Message elements.
   */
  public S hasOnlyMessages_send(java.util.Collection<? extends Message> messages_send) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message collection is not null.
    if (messages_send == null) {
      failWithMessage("Expecting messages_send parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getMessages_send(), messages_send.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_send does not contain the given Message elements.
   *
   * @param messages_send the given elements that should not be in actual User's messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send contains any given Message elements.
   */
  public S doesNotHaveMessages_send(Message... messages_send) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message varargs is not null.
    if (messages_send == null) failWithMessage("Expecting messages_send parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getMessages_send(), messages_send);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's messages_send does not contain the given Message elements in Collection.
   *
   * @param messages_send the given elements that should not be in actual User's messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send contains any given Message elements.
   */
  public S doesNotHaveMessages_send(java.util.Collection<? extends Message> messages_send) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Message collection is not null.
    if (messages_send == null) {
      failWithMessage("Expecting messages_send parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getMessages_send(), messages_send.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User has no messages_send.
   * @return this assertion object.
   * @throws AssertionError if the actual User's messages_send is not empty.
   */
  public S hasNoMessages_send() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have messages_send but had :\n  <%s>";

    // check
    if (actual.getMessages_send().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getMessages_send());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual User's password is equal to the given one.
   * @param password the given password to compare the actual User's password to.
   * @return this assertion object.
   * @throws AssertionError - if the actual User's password is not equal to the given one.
   */
  public S hasPassword(String password) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting password of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualPassword = actual.getPassword();
    if (!Objects.areEqual(actualPassword, password)) {
      failWithMessage(assertjErrorMessage, actual, password, actualPassword);
    }

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's team contains the given Team elements.
   * @param team the given elements that should be contained in actual User's team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team does not contain all given Team elements.
   */
  public S hasTeam(Team... team) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Team varargs is not null.
    if (team == null) failWithMessage("Expecting team parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getTeam(), team);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's team contains the given Team elements in Collection.
   * @param team the given elements that should be contained in actual User's team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team does not contain all given Team elements.
   */
  public S hasTeam(java.util.Collection<? extends Team> team) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Team collection is not null.
    if (team == null) {
      failWithMessage("Expecting team parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getTeam(), team.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's team contains <b>only</b> the given Team elements and nothing else in whatever order.
   * @param team the given elements that should be contained in actual User's team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team does not contain all given Team elements.
   */
  public S hasOnlyTeam(Team... team) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Team varargs is not null.
    if (team == null) failWithMessage("Expecting team parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getTeam(), team);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's team contains <b>only</b> the given Team elements in Collection and nothing else in whatever order.
   * @param team the given elements that should be contained in actual User's team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team does not contain all given Team elements.
   */
  public S hasOnlyTeam(java.util.Collection<? extends Team> team) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Team collection is not null.
    if (team == null) {
      failWithMessage("Expecting team parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getTeam(), team.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's team does not contain the given Team elements.
   *
   * @param team the given elements that should not be in actual User's team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team contains any given Team elements.
   */
  public S doesNotHaveTeam(Team... team) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Team varargs is not null.
    if (team == null) failWithMessage("Expecting team parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getTeam(), team);

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User's team does not contain the given Team elements in Collection.
   *
   * @param team the given elements that should not be in actual User's team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team contains any given Team elements.
   */
  public S doesNotHaveTeam(java.util.Collection<? extends Team> team) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // check that given Team collection is not null.
    if (team == null) {
      failWithMessage("Expecting team parameter not to be null.");
      return myself; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getTeam(), team.toArray());

    // return the current assertion for method chaining
    return myself;
  }

  /**
   * Verifies that the actual User has no team.
   * @return this assertion object.
   * @throws AssertionError if the actual User's team is not empty.
   */
  public S hasNoTeam() {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have team but had :\n  <%s>";

    // check
    if (actual.getTeam().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getTeam());
    }

    // return the current assertion for method chaining
    return myself;
  }


  /**
   * Verifies that the actual User's username is equal to the given one.
   * @param username the given username to compare the actual User's username to.
   * @return this assertion object.
   * @throws AssertionError - if the actual User's username is not equal to the given one.
   */
  public S hasUsername(String username) {
    // check that actual User we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting username of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualUsername = actual.getUsername();
    if (!Objects.areEqual(actualUsername, username)) {
      failWithMessage(assertjErrorMessage, actual, username, actualUsername);
    }

    // return the current assertion for method chaining
    return myself;
  }

}

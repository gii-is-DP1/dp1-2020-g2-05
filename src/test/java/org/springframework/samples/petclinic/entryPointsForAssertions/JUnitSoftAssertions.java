package org.springframework.samples.petclinic.entryPointsForAssertions;

import org.springframework.samples.petclinic.autoGeneratedAssertions.*;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.model.audit.*;

/**
 * Like {@link SoftAssertions} but as a junit rule that takes care of calling
 * {@link SoftAssertions#assertAll() assertAll()} at the end of each test.
 * <p>
 * Example:
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Rule
 *     public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();
 *
 *     &#064;Test
 *     public void soft_bdd_assertions() throws Exception {
 *       softly.assertThat(1).isEqualTo(2);
 *       softly.assertThat(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *       // no need to call assertAll(), this is done automatically.
 *     }
 *  }</code></pre>
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class JUnitSoftAssertions extends org.assertj.core.api.JUnitSoftAssertions {

  /**
   * Creates a new "soft" instance of <code>{@link AuthoritiesAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public AuthoritiesAssert assertThat(Authorities actual) {
    return proxy(AuthoritiesAssert.class, Authorities.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link BDCarreraAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public BDCarreraAssert assertThat(BDCarrera actual) {
    return proxy(BDCarreraAssert.class, BDCarrera.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link BaseEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public BaseEntityAssert assertThat(BaseEntity actual) {
    return proxy(BaseEntityAssert.class, BaseEntity.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link CategoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public CategoryAssert assertThat(Category actual) {
    return proxy(CategoryAssert.class, Category.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link FormRellenarBDAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public FormRellenarBDAssert assertThat(FormRellenarBD actual) {
    return proxy(FormRellenarBDAssert.class, FormRellenarBD.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link GranPremioAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public GranPremioAssert assertThat(GranPremio actual) {
    return proxy(GranPremioAssert.class, GranPremio.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link LeagueAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public LeagueAssert assertThat(League actual) {
    return proxy(LeagueAssert.class, League.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link LineupAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public LineupAssert assertThat(Lineup actual) {
    return proxy(LineupAssert.class, Lineup.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link LineupTestsAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public LineupTestsAssert assertThat(LineupTests actual) {
    return proxy(LineupTestsAssert.class, LineupTests.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link MessageAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public MessageAssert assertThat(Message actual) {
    return proxy(MessageAssert.class, Message.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link NamedEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public NamedEntityAssert assertThat(NamedEntity actual) {
    return proxy(NamedEntityAssert.class, NamedEntity.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link OfferAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public OfferAssert assertThat(Offer actual) {
    return proxy(OfferAssert.class, Offer.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link PersonAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public PersonAssert assertThat(Person actual) {
    return proxy(PersonAssert.class, Person.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link PilotAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public PilotAssert assertThat(Pilot actual) {
    return proxy(PilotAssert.class, Pilot.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RecordAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RecordAssert assertThat(Record actual) {
    return proxy(RecordAssert.class, Record.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RecordCircuitoAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RecordCircuitoAssert assertThat(RecordCircuito actual) {
    return proxy(RecordCircuitoAssert.class, RecordCircuito.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RecordMejorVueltaAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RecordMejorVueltaAssert assertThat(RecordMejorVuelta actual) {
    return proxy(RecordMejorVueltaAssert.class, RecordMejorVuelta.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RecordPoleAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RecordPoleAssert assertThat(RecordPole actual) {
    return proxy(RecordPoleAssert.class, RecordPole.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RecordVueltaRapidaAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RecordVueltaRapidaAssert assertThat(RecordVueltaRapida actual) {
    return proxy(RecordVueltaRapidaAssert.class, RecordVueltaRapida.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RecruitAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RecruitAssert assertThat(Recruit actual) {
    return proxy(RecruitAssert.class, Recruit.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link ResultAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public ResultAssert assertThat(Result actual) {
    return proxy(ResultAssert.class, Result.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link RolAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public RolAssert assertThat(Rol actual) {
    return proxy(RolAssert.class, Rol.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link StatusAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public StatusAssert assertThat(Status actual) {
    return proxy(StatusAssert.class, Status.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link TablaConsultasAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public TablaConsultasAssert assertThat(TablaConsultas actual) {
    return proxy(TablaConsultasAssert.class, TablaConsultas.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public TeamAssert assertThat(Team actual) {
    return proxy(TeamAssert.class, Team.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link TeamModelTestAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public TeamModelTestAssert assertThat(TeamModelTest actual) {
    return proxy(TeamModelTestAssert.class, TeamModelTest.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link TeamValidatorTestsAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public TeamValidatorTestsAssert assertThat(TeamValidatorTests actual) {
    return proxy(TeamValidatorTestsAssert.class, TeamValidatorTests.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link TransactionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public TransactionAssert assertThat(Transaction actual) {
    return proxy(TransactionAssert.class, Transaction.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link TransactionTypeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public TransactionTypeAssert assertThat(TransactionType actual) {
    return proxy(TransactionTypeAssert.class, TransactionType.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link UserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public UserAssert assertThat(User actual) {
    return proxy(UserAssert.class, User.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link UserRevEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public UserRevEntityAssert assertThat(UserRevEntity actual) {
    return proxy(UserRevEntityAssert.class, UserRevEntity.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link UserRevisionListenerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public UserRevisionListenerAssert assertThat(UserRevisionListener actual) {
    return proxy(UserRevisionListenerAssert.class, UserRevisionListener.class, actual);
  }

}

package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TablaConsultasService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.Status;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OfferController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class OfferControllerTests {

	private static final int TEST_LEAGUE_ID = 1;
	private static final int TEST_OFFER1_ID = 1;
	private static final int TEST_OFFER2_ID = 2;
	private static final int TEST_OFFER3_ID = 3;
	private static final int TEST_OFFERFAIL_ID = 4;

	@MockBean
	private TablaConsultasService tablaConsultas;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OfferService offerService;

	@MockBean
	private RecruitService recruitService;

	@MockBean
	private TeamService teamService;

	@MockBean
	private TransactionService transactionService;

	@MockBean
	private UserService userService;

	// @MockBean
	// private LeagueService leagueService;

	private Offer offer1 = new Offer();
	private Offer offer2 = new Offer();
	private Offer offer3 = new Offer();
	private List<Offer> offersList = new ArrayList<>();
	private Recruit recruit1 = new Recruit();
	private Recruit recruit2 = new Recruit();
	private Recruit recruit3 = new Recruit();
	private Recruit recruit4 = new Recruit();
	private Recruit recruit5 = new Recruit();
	private List<Recruit> recruitTeam1List = new ArrayList<>();
	private Team team1 = new Team();
	private Team team2 = new Team();
	private Team team3 = new Team();
	private User user1 = new User();
	private User user2 = new User();
	private User user3 = new User();
	private League league = new League();
	private Pilot pilot1 = new Pilot();
	private Pilot pilot2 = new Pilot();
	private Pilot pilot3 = new Pilot();
	private Pilot pilot4 = new Pilot();
	private Pilot pilot5 = new Pilot();

	@BeforeEach
	void setup() {
		pilot1.setCategory(Category.MOTOGP);
		pilot1.setDorsal("27");
		pilot1.setName("Ale");
		pilot1.setLastName("Ruiz");
		pilot1.setNationality("Spain");
		pilot1.setBaseValue(200);

		pilot2.setCategory(Category.MOTOGP);
		pilot2.setDorsal("7");
		pilot2.setName("Juan");
		pilot2.setLastName("Perez");
		pilot2.setNationality("Spain");
		pilot2.setBaseValue(400);

		pilot3.setCategory(Category.MOTOGP);
		pilot3.setDorsal("3");
		pilot3.setName("Rodri");
		pilot3.setLastName("Perez");
		pilot3.setNationality("Spain");
		pilot3.setBaseValue(500);

		pilot4.setCategory(Category.MOTOGP);
		pilot4.setDorsal("2");
		pilot4.setName("Omar");
		pilot4.setLastName("Perez");
		pilot4.setNationality("Spain");
		pilot4.setBaseValue(2000);

		pilot5.setCategory(Category.MOTOGP);
		pilot5.setDorsal("5");
		pilot5.setName("Pepe");
		pilot5.setLastName("Perez");
		pilot5.setNationality("Spain");
		pilot5.setBaseValue(500);

		league.setId(TEST_LEAGUE_ID);
		league.setLeagueCode("UDTQCSSOND");
		league.setName("prueba");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		league.setLeagueDate(formatter.format(date));

		user1.setUsername("migue");
		user1.setPassword("asd");
		user1.setEmail("migue@mail.com");
		user1.setEnabled(true);

		user2.setUsername("ale");
		user2.setPassword("asd");
		user2.setEmail("ale@mail.com");
		user2.setEnabled(true);

		user3.setUsername("fran");
		user3.setPassword("asd");
		user3.setEmail("fran@mail.com");
		user3.setEnabled(true);

		team1.setId(1);
		team1.setLeague(league);
		team1.setMoney(200);
		team1.setName("Team1");
		team1.setPoints(2000);
		team1.setUser(user1);

		team2.setId(2);
		team2.setLeague(league);
		team2.setMoney(3000);
		team2.setName("Team2");
		team2.setPoints(300);
		team2.setUser(user2);

		team3.setId(3);
		team3.setLeague(league);
		team3.setMoney(300);
		team3.setName("Team3");
		team3.setPoints(3000);
		team3.setUser(user3);

		recruit1.setPilot(pilot1);
		recruit1.setTeam(team1);
		recruit2.setPilot(pilot2);
		recruit2.setTeam(team2);
		recruit3.setPilot(pilot4);
		recruit3.setTeam(team1);
		recruit4.setPilot(pilot3);
		recruit4.setTeam(team1);
		recruit5.setPilot(pilot5);
		recruit5.setTeam(team1);

		offer1.setId(TEST_OFFER1_ID);
		offer1.setRecruit(recruit1);
		offer1.setPrice(2000);
		offer1.setStatus(Status.Outstanding);

		offer3.setId(TEST_OFFER3_ID);
		offer3.setRecruit(recruit3);
		offer3.setPrice(1000);
		offer3.setStatus(Status.Accepted);

		offer2.setId(TEST_OFFER2_ID);
		offer2.setRecruit(recruit2);
		offer2.setPrice(1000);
		offer2.setStatus(Status.Outstanding);

		offersList.add(offer1);
		offersList.add(offer2);
		offersList.add(offer3);

		recruitTeam1List.add(recruit1);
		recruitTeam1List.add(recruit3);
		recruitTeam1List.add(recruit4);
		recruitTeam1List.add(recruit5);
	}

	@WithMockUser(value = "spring")
	@Test
	void testGetOffers() throws Exception {
		given(userService.getUserSession()).willReturn(user1);
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);

		mockMvc.perform(get("/leagues/{leagueId}/market", TEST_LEAGUE_ID)).andExpect(status().isOk())
				.andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer1.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer1.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer1.getRecruit())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer2.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer2.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer2.getRecruit())))));
	}

	@WithMockUser(value = "spring")
	@Test
	void testRecruitPilot() throws Exception {
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);
		given(userService.getUserSession()).willReturn(user2);
		given(teamService.findTeamByUsernameAndLeagueId(user2.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team2));
		given(offerService.findOfferById(TEST_OFFER1_ID)).willReturn(Optional.of(offer1));

		mockMvc.perform(get("/leagues/{leagueId}/market/{offerId}", TEST_LEAGUE_ID, TEST_OFFER1_ID))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer1.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(Status.Accepted)))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer1.getRecruit())))))
				.andExpect(
						model().attribute("offers", Matchers.hasItem(Matchers.<Offer>hasProperty("team", is(team2)))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer2.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer2.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer2.getRecruit())))))
				.andExpect(model().attribute("message", is("Pilot recruited!")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testOfferCancel() throws Exception {
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);
		given(userService.getUserSession()).willReturn(user1);
		given(teamService.findTeamByUsernameAndLeagueId(user1.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team1));// El mismo team que ha puesto la oferta
		given(offerService.findOfferById(TEST_OFFER1_ID)).willReturn(Optional.of(offer1));

		mockMvc.perform(get("/leagues/{leagueId}/market/{offerId}", TEST_LEAGUE_ID, TEST_OFFER1_ID))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer1.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(Status.Denied)))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer1.getRecruit())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer2.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer2.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer2.getRecruit())))))
				.andExpect(model().attribute("message", is("Offer cancelled!")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testTryToRecruitWith4Recruits() throws Exception {
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);
		given(userService.getUserSession()).willReturn(user1);
		given(teamService.findTeamByUsernameAndLeagueId(user1.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team1));
		given(offerService.findOfferById(TEST_OFFER2_ID)).willReturn(Optional.of(offer2));
		given(recruitService.getRecruitsByTeam(team1.getId())).willReturn(recruitTeam1List);

		mockMvc.perform(get("/leagues/{leagueId}/market/{offerId}", TEST_LEAGUE_ID, TEST_OFFER2_ID))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("message", is("You already own 4 riders on this league")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testRecruitWithoutMoney() throws Exception {
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);
		given(userService.getUserSession()).willReturn(user3);
		given(teamService.findTeamByUsernameAndLeagueId(user3.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team3));
		given(offerService.findOfferById(TEST_OFFER1_ID)).willReturn(Optional.of(offer1));

		mockMvc.perform(get("/leagues/{leagueId}/market/{offerId}", TEST_LEAGUE_ID, TEST_OFFER1_ID))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer1.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer1.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer1.getRecruit())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer2.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer2.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer2.getRecruit())))))
				.andExpect(model().attribute("message", is("Not enought money to recruit this pilot")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testTryToRecruitPilotIsNotInSale() throws Exception {
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);
		given(userService.getUserSession()).willReturn(user2);
		given(teamService.findTeamByUsernameAndLeagueId(user2.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team2));
		given(offerService.findOfferById(TEST_OFFER3_ID)).willReturn(Optional.of(offer3));

		mockMvc.perform(get("/leagues/{leagueId}/market/{offerId}", TEST_LEAGUE_ID, TEST_OFFER3_ID))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer1.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer1.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer1.getRecruit())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer2.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer2.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer2.getRecruit())))))
				.andExpect(model().attribute("message", is("This pilot isn't on sale anymore")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testTryToFindOffer() throws Exception {
		given(offerService.findOffersByLeague(TEST_LEAGUE_ID)).willReturn(offersList);
		given(userService.getUserSession()).willReturn(user2);
		given(teamService.findTeamByUsernameAndLeagueId(user2.getUsername(), TEST_LEAGUE_ID))
				.willReturn(Optional.of(team2));
		given(offerService.findOfferById(TEST_OFFERFAIL_ID)).willReturn(Optional.empty());

		mockMvc.perform(get("/leagues/{leagueId}/market/{offerId}", TEST_LEAGUE_ID, TEST_OFFERFAIL_ID))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("offers/offersList"))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer1.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer1.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer1.getRecruit())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("price", is(offer2.getPrice())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("status", is(offer2.getStatus())))))
				.andExpect(model().attribute("offers",
						Matchers.hasItem(Matchers.<Offer>hasProperty("recruit", is(offer2.getRecruit())))))
				.andExpect(model().attribute("message", is("Offer not found!")));
	}
}

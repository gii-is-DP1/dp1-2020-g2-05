package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.util.Set;import java.util.stream.Collectors;

import org.apache.taglibs.standard.tag.common.fmt.SetBundleSupport;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.LeagueService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = TeamController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class TeamControllerTest {
	
	private static final int TEST_LEAGUE_ID = 1;

	private static final int TEST_TEAM_ID = 1;

	@Autowired
	private TeamController teamController;
	
	@MockBean
	private LeagueService leagueService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		
		User user = new User();
		League liga = new League();
		Team team = new Team();
		
		liga.setId(TEST_LEAGUE_ID);
		liga.setLeagueCode("IOKASXISAU");
		liga.setLeagueDate("15/10/2020");
		liga.setMoto2Active(true);
		liga.setMoto3Active(false);
		liga.setMotogpActive(false);
		liga.setName("liga");
		liga.setRacesCompleted(2);
		liga.setTeam((Set<Team>) team);
		
		
		team.setId(1);
		team.setName("Migue");
		team.setLeague(liga);
		given(this.leagueService.findLeague(1).get().getTeam()).willReturn(Sets.newSet(team));
		given(this.leagueService.findLeague(TEST_LEAGUE_ID).get()).willReturn(new  League());
		given(this.leagueService.findTeamById(TEST_TEAM_ID).get()).willReturn(new Team());
		
	}


}

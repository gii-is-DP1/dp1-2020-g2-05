package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.samples.petclinic.service.exceptions.duplicatedLeagueNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TeamServiceTest {

	@Autowired
	protected TeamService teamService;

	@Autowired
	protected LeagueService leagueService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected RecruitService recruitService;

	@Test
	void shouldCountTeamsByLeagueId() {
		Integer positive_test = this.teamService.findTeamsByLeagueId(1);
		assertThat(positive_test).isNotEqualTo(0);
	 }
	 
		
	 @Test
	 void shouldBeZeroTeamsByLeagueId() {
			Integer negative_test= this.teamService.findTeamsByLeagueId(2323);
			assertThat(negative_test).isEqualTo(0);
		
	 }
	
	 
	 @Test
		void shouldFindTeamsById() {
			Optional<Team> team = this.teamService.findTeamById(1);
			assertThat(team.isPresent()).isTrue();
			
			
			Optional<Team> team_fail = this.teamService.findTeamById(300);
			assertThat(team_fail.isPresent()).isFalse();
		}
	 
	 @Test
		void shouldFindTeamsIntByUsername() {
			Collection<Integer> teams = this.teamService.findTeamsIntByUsername("migniearj");
			assertThat(teams.size()> 0).isTrue();
			

			
			Collection<Integer> teams_fail = this.teamService.findTeamsIntByUsername("negative_test");
			assertThat(teams_fail.size()).isEqualTo(0);
		}
	 
	 
	 @Test
	 @Transactional
	 void shouldInsertTeam() {
		 
		 Iterable<Team> teams = this.teamService.findAllTeams();
		 List<Team> team = new ArrayList<Team>();
		 teams.forEach(team::add);
		 Integer equipo1 = team.size();
		 
		 Team team_new = new Team();
		 team_new.setMoney(200);
		 team_new.setName("TEST");
		 team_new.setPoints(222);
		 team_new.setLeague(this.leagueService.findLeague(1).get());
		 team_new.setUser(this.userService.findUser("migniearj").get());
		 System.out.println(team_new);
		 this.teamService.saveTeam(team_new);
		 assertThat(team_new.getId().longValue()).isNotEqualTo(0);
		 
		teams = this.teamService.findAllTeams();
		team = new ArrayList<Team>();
		teams.forEach(team::add);

		assertThat(team.size()).isEqualTo(equipo1 + 1);

	}

	@Test
	@Transactional
	void shouldDeleteTeam() {

		Iterable<Team> teams = this.teamService.findAllTeams();
		List<Team> team = new ArrayList<Team>();
		teams.forEach(team::add);
		Integer equipo1 = team.size();

		Team team_new = new Team();
		team_new.setMoney(200);
		team_new.setName("TEST");
		team_new.setPoints(222);
		team_new.setLeague(this.leagueService.findLeague(1).get());
		team_new.setUser(this.userService.findUser("migniearj").get());

		this.teamService.saveTeam(team_new);
		assertThat(team_new.getId().longValue()).isNotEqualTo(0);

		Iterable<Team> teams1 = this.teamService.findAllTeams();
		List<Team> team1 = new ArrayList<Team>();
		teams1.forEach(team1::add);

		this.teamService.delete(team_new);
		assertThat(team_new.getId().longValue()).isNotEqualTo(0);

		teams = this.teamService.findAllTeams();
		team = new ArrayList<Team>();
		teams.forEach(team::add);

		assertThat(team.size()).isEqualTo(equipo1);

	}

	@Test
	@Transactional
	void shouldFindTeamByLeagueId() {
		List<Team> team = this.teamService.findTeamByLeagueId(1);
		Integer equipo1 = team.size();

		assertThat(equipo1).isNotEqualTo(0);

	}

	@Test
	@Transactional
	void shouldFindTeamByUsername() {
		List<Team> team = this.teamService.findTeamByUsername("migniearj");
		Integer equipo1 = team.size();

		assertThat(equipo1).isNotEqualTo(0);

	}

	@Test
	@Transactional
	void shouldFindTeamByUsernameAndLeagueId() {
		Team team = this.teamService.findTeamByUsernameAndLeagueId("migniearj", 2).get();

		assertThat(team).isNotNull();

	}

	@Test
	@Transactional
	void shouldSaveTeamMoney() {
		Team team = teamService.findTeamById(1).get();
		Integer money = team.getMoney();

		teamService.saveTeamMoney(team, 300);

		assertThat(team.getMoney()).isEqualTo(money + 300);
	}

	@Test
	@Transactional
	void shouldSaveSystemTeam() throws DataAccessException, duplicatedLeagueNameException {
		League league = new League();
		league.setLeagueCode("UDTQCSSOND");
		league.setName("prueba");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		league.setLeagueDate(formatter.format(date));
		leagueService.saveLeague(league);

		teamService.saveSystemTeam(league);

		List<Team> teams = teamService.findTeamByLeagueId(league.getId());

		assertThat(teams.get(0).getName()).isEqualTo("Sistema");
		assertThat(teams.get(0).getLeague()).isEqualTo(league);
		assertThat(teams.get(0).getMoney()).isEqualTo(0);
		assertThat(teams.get(0).getPoints()).isEqualTo(0);
		assertThat(teams.size()).isEqualTo(1);
	}

	@Test
	@Transactional
	void shouldRecruitAndOfferAll() {
		Team team = teamService.findTeamById(8).get();

		teamService.recruitAndOfferAll(team, Category.MOTO2);

		assertThat(this.recruitService.getRecruitsByTeam(team.getId()).size()).isGreaterThan(0);
	}
	
	@Test
	@Transactional
	void shouldSellAllTeamRecruits() {
		Team team = teamService.findTeamById(1).get();
		Integer money = team.getMoney();
		
		teamService.sellAllTeamRecruits(team);
		
		assertThat(this.recruitService.getRecruitsByTeam(team.getId()).size()).isEqualTo(0);
//		assertThat(team.getMoney()).isGreaterThanOrEqualTo(money);
	}
	
	@Test
	@Transactional
	void shouldRandomRecruit2Pilots() throws NotAllowedNumberOfRecruitsException {
		Team team = teamService.findTeamById(3).get();
		Team sysTeam = teamService.findTeamById(8).get();
		teamService.recruitAndOfferAll(sysTeam, Category.MOTO3);
		
		teamService.randomRecruit2Pilots(team);
		
		
		assertThat(this.recruitService.getRecruitsByTeam(team.getId()).size()).isEqualTo(2);
	}
}

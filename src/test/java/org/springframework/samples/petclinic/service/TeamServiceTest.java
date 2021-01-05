package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.transaction.annotation.Transactional;

public class TeamServiceTest {
	
	 @Autowired
	 	protected TeamService teamService;
	 
	 @Autowired
		protected LeagueService leagueService;
	 
	 @Autowired
	 	protected UserService userService;
	 
	 @Test
	 void shouldCountTeamsByLeagueId() {
		Integer positive_test= this.teamService.findTeamsByLeagueId(1);
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
		void shouldFindTeamsByUsername() {
			Collection<Integer> teams = this.teamService.findTeamsByUsername("migniearj");
			assertThat(teams.size()> 0).isTrue();
			

			
			Collection<Integer> teams_fail = this.teamService.findTeamsByUsername("negative_test");
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
	 

}

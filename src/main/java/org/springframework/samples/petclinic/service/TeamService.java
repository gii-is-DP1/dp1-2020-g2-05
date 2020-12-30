package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
	
	private TeamRepository teamRepository;
	
	@Autowired
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}
	
	public Optional<Team> findTeamById(Integer teamId) {
		return teamRepository.findById(teamId);
	}

}

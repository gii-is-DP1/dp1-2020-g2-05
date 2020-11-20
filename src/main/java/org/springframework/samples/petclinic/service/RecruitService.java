package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.repository.RecruitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruitService {

	private RecruitRepository recruitRepository;

	@Autowired
	public RecruitService(RecruitRepository recruitRepository) {
		this.recruitRepository = recruitRepository;
	}

	@Transactional(readOnly = true)
	public List<Recruit> getRecruits(Long teamID) throws DataAccessException {
		return recruitRepository.listTeamRecruits(teamID);
	}

}

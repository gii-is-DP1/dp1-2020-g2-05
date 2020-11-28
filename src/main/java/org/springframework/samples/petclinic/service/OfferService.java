package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferService {
	
	private OfferRepository offerRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LeagueService leagueService;

	@Autowired
	public OfferService(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}	
	
	@Transactional
	public Iterable<Offer> findAllOffers() {
		return offerRepository.findAll();
	}
	
	@Transactional
	public Optional<Offer> findOfferById(int offerId) {
		return offerRepository.findById(offerId);
	}
	
	@Transactional
	public Collection<Offer> findOffersByLeague(int leagueId) throws DataAccessException {
		return offerRepository.findOffersByLeague(leagueId);
	}
	
	@Transactional
	public Optional<Team> findTeamByUsernameLeague(int leagueId) throws DataAccessException {
		return offerRepository.findTeamByUsernameLeague(leagueId, userService.getUserSession().getUsername());
	}
	
	@Transactional
	public void saveTeamMoney(Team team, String price) {
		team.setMoney(String.valueOf(Integer.parseInt(team.getMoney()) 
				- Integer.parseInt(price)));
		leagueService.saveTeam(team);		
	}
	
	@Transactional
	public void saveOffer(Offer offer) throws DataAccessException {
		offerRepository.save(offer);		
	}
	
	@Transactional
	public void deleteOffer(Offer offer) throws DataAccessException {
		offerRepository.delete(offer);		
	}
}

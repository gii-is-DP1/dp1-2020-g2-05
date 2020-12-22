package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.OfferRepository;
import org.springframework.samples.petclinic.util.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferService {
	
	private OfferRepository offerRepository;
	private UserService userService;
	private LeagueService leagueService;

	@Autowired
	public OfferService(OfferRepository offerRepository, UserService userService, LeagueService leagueService) {
		this.offerRepository = offerRepository;
		this.userService = userService;
		this.leagueService = leagueService;
	}
	
	@Transactional
	public Collection<Offer> findAllOffers() {
		return (Collection<Offer>) offerRepository.findAll();
	}
	
	@Transactional
	public Optional<Offer> findOfferById(int offerId) {
		return offerRepository.findById(offerId);
	}
	
	@Transactional
	public List<Offer> findOffersByLeague(int leagueId) throws DataAccessException {
		System.out.println(offerRepository.findOffersByLeague(leagueId));
		return offerRepository.findOffersByLeague(leagueId);
	}
	
	@Transactional
	public Team findTeamByUsernameLeague(int leagueId) throws DataAccessException {
		return leagueService.findTeamByUsernameAndLeagueId(userService.getUserSession().getUsername(), leagueId).get();
	}
	
	@Transactional
	public void saveTeamMoney(Team team, Integer price) throws DataAccessException {
		team.setMoney(String.valueOf(Integer.parseInt(team.getMoney()) + price));
		leagueService.saveTeam(team);		
	}
	
	@Transactional
	public void putOnSale(Recruit recruit, Integer price) throws DataAccessException {
		Offer offer = new Offer();
		offer.setRecruit(recruit);
		offer.setStatus(Status.Outstanding);
		offer.setPrice(price);
		saveOffer(offer);
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

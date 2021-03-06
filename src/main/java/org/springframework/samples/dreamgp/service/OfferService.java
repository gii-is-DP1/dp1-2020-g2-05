package org.springframework.samples.dreamgp.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Offer;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.samples.dreamgp.model.Status;
import org.springframework.samples.dreamgp.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferService {

	private OfferRepository offerRepository;

	@Autowired
	public OfferService(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
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
		return offerRepository.findOffersByLeague(leagueId);
	}

	@Transactional
	public List<Offer> findOffersByRecruit(int recruitId) throws DataAccessException {
		return offerRepository.findOffersByRecruit(recruitId);
	}

	@Transactional
	public void putOnSale(Recruit recruit, Integer price) throws DataAccessException {
		Offer offer = new Offer();
		offer.setRecruit(recruit);
		offer.setStatus(Status.Outstanding);
		offer.setPrice(price);
		offer.setTeam(recruit.getTeam());
		saveOffer(offer);
	}

	@Transactional
	public void saveOffer(Offer offer) throws DataAccessException {
		offerRepository.save(offer);
	}
}

package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepository;

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
	public void saveOffer(Offer offer) throws DataAccessException {
		offerRepository.save(offer);		
	}
	
	@Transactional
	public void deleteOffer(Offer offer) throws DataAccessException {
		offerRepository.delete(offer);		
	}
}

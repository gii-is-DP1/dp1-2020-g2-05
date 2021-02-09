package org.springframework.samples.dreamgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dreamgp.model.Offer;

public interface OfferRepository extends CrudRepository<Offer,Integer>{
	
	@Query("SELECT offer FROM Offer offer WHERE offer.recruit.team.league.id = ?1")
	public List<Offer> findOffersByLeague(int leagueId);
	
	@Query("SELECT offer FROM Offer offer WHERE offer.recruit.id = ?1")
	public List<Offer> findOffersByRecruit(int recruitId);
}

package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Offer;

public interface OfferRepository extends CrudRepository<Offer,Integer>{
	
	@Query("SELECT offer FROM Offer offer WHERE offer.recruit.team.league.id = ?1")
	public List<Offer> findOffersByLeague(int leagueId);
}

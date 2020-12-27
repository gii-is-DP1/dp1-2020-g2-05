package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Team;

public interface OfferRepository extends CrudRepository<Offer,Integer>{
	
	@Query(value = "SELECT offer.* FROM OFFER join team join recruit where team.league_id = :leagueId and recruit.id = offer.recruit_id and team.id = recruit.team_id", nativeQuery = true)
	public List<Offer> findOffersByLeague(@Param("leagueId") int leagueId);
}

package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Team;

public interface OfferRepository extends CrudRepository<Offer,Integer>{
	
	@Query(value = "SELECT offer.* FROM team inner join offer where team.id = offer.team_id and league_id LIKE :leagueId%", nativeQuery = true)
	public List<Offer> findOffersByLeague(@Param("leagueId") int leagueId);
}

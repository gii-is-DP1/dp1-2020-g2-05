package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Trade;

public interface TradeRepository extends CrudRepository<Trade, Integer> {

	@Query("SELECT t FROM Trade t WHERE t.team.id = :teamID")
	List<Trade> findTradesByTeamId(@Param("teamID") int teamID);

}

package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Trade;

public interface TradeRepository extends Repository<Trade, Integer> {

	@Query("SELECT t FROM Trade t WHERE t.team.id = :teamID")
	public List<Trade> findTradesByTeamId(@Param("teamID") int teamID);

	public void save(Trade trade);
}

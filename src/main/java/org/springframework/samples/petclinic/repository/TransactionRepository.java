package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.team.id = :teamID")
	List<Transaction> findTradesByTeamId(@Param("teamID") int teamID);

}

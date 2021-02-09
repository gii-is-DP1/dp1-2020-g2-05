package org.springframework.samples.dreamgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dreamgp.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.team.id = :teamID")
	List<Transaction> findByTeamId(@Param("teamID") int teamID);

}

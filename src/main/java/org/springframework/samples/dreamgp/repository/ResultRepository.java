package org.springframework.samples.dreamgp.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.Result;

public interface ResultRepository extends CrudRepository<Result, Integer> {

	@Query("SELECT r FROM Result r WHERE r.gp.id = :gpId AND r.gp.raceCode = :raceCode AND r.pilot.category = :category")
	List<Result> findResultsByCategoryAndId(Integer gpId, String raceCode, Category category) throws DataAccessException;
	
	@Query("SELECT r FROM Result r WHERE r.pilot.id = ?1")
	public List<Result> findByPilotId(int pilotId);
}

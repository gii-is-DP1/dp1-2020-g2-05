package org.springframework.samples.dreamgp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dreamgp.model.Category;
import org.springframework.samples.dreamgp.model.Pilot;

public interface PilotRepository extends CrudRepository<Pilot, Integer> {

	@Query("SELECT p FROM Pilot p ORDER BY p.category, p.name, p.lastName, p.points, p.nationality")
	public List<Pilot> findAll();
	
	@Query("SELECT COUNT(pilot) FROM Pilot pilot WHERE pilot.name LIKE :name AND pilot.lastName LIKE :lastName")
	public Integer countByName(@Param("lastName")String lastName,@Param("name")String name);

	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.name LIKE :name AND pilot.lastName LIKE :lastName")
	public Optional<Pilot> findByName(@Param("lastName")String lastName,@Param("name")String name);
	
	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.category=:category ORDER BY pilot.points DESC")
	public List<Pilot> findTop3ByPointsAndCategory(@Param("category") Category category);

	@Query(value="SELECT p.* FROM RECRUIT AS r NATURAL JOIN PILOT AS p", nativeQuery = true)
	List<Pilot> findAllRecruits();
	
	@Query("SELECT p FROM Recruit r, Pilot p WHERE r.team.id = ?1 AND  r.pilot.id = p.id")
	List<Pilot> findAllRecruits(int teamID);
	
	@Query("SELECT p FROM Pilot p")
	Page<Pilot> findAllPage(Pageable pageable); 
}

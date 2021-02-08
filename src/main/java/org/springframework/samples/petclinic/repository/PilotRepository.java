/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Pilot;

public interface PilotRepository extends CrudRepository<Pilot, Integer> {

	@Query(value="SELECT * FROM PILOT order by category, name, lastname, points, nationality", nativeQuery=true)
	public List<Pilot> findAll();
	
	@Query("SELECT COUNT(pilot) FROM Pilot pilot WHERE pilot.name LIKE :name AND pilot.lastName LIKE :lastName")
	public Integer countByName(@Param("lastName")String lastName,@Param("name")String name);

	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.name LIKE :name AND pilot.lastName LIKE :lastName")
	public Optional<Pilot> findByName(@Param("lastName")String lastName,@Param("name")String name);
	
	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.category=:category ORDER BY pilot.points DESC")
	public List<Pilot> findTop3ByPointsAndCategory(@Param("category") Category category);

	@Query(value="SELECT p.* FROM RECRUIT AS r NATURAL JOIN PILOT AS p", nativeQuery = true)
	List<Pilot> findAllRecruits();
	
	@Query(value="SELECT p.* FROM RECRUIT AS r, PILOT as p WHERE r.team_id = ?1 AND  r.pilot_id = p.id", nativeQuery = true)
	List<Pilot> findAllRecruits(int teamID);
	
	@Query(value="SELECT * FROM PILOT", nativeQuery = true)
	Page<Pilot> findAllPage(Pageable pageable); 
}

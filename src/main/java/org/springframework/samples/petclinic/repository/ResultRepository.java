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

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Result;

/**
 * Spring Data JPA specialization of the {@link ResultRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface ResultRepository extends CrudRepository<Result, Integer> {

//	/**
//	 * Retrieve all <code>PetType</code>s from the data store.
//	 * @return a <code>Collection</code> of <code>PetType</code>s
//	 */
////	@Query("SELECT * FROM RESULTS WHERE RESULTS.pilot_id = SELECT id FROM Pilot  WHERE pilot.name=%name")
////	List<Result> findResults(@Param("name")String name) throws DataAccessException;
//	//Obtiene resultados del piloto pasado como parametro
//	
//	/**
//	 * Retrieve a <code>Pet</code> from the data store by id.
//	 * @param id the id to search for
//	 * @return the <code>Pet</code> if found
//	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
//	 */
//	Result findById(int id) throws DataAccessException;
//
//	/**
//	 * Save a <code>Pet</code> to the data store, either inserting or updating it.
//	 * @param pet the <code>Pet</code> to save
//	 * @see BaseEntity#isNew
//	 */
//	void save(Result pet) throws DataAccessException;

}

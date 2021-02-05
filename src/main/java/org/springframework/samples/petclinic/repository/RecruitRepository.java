package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.transaction.annotation.Transactional;

public interface RecruitRepository extends CrudRepository<Recruit, Integer> {

	@Query(value = "SELECT r.* FROM RECRUIT AS r JOIN PILOT AS p JOIN TEAM t WHERE r.pilot_id = ?1 and  t.league_id = ?2 and t.id = r.team_id and p.id = r.pilot_id", nativeQuery = true)
	Optional<Recruit> findRecruitByPilotId(int pilotId, int leagueId);

	@Query(value = "SELECT r.* FROM RECRUIT AS r WHERE r.team_id = ?1", nativeQuery = true)
	List<Recruit> findAllRecruits(int teamID);

//	@Query(value = "SELECT r.* FROM RECRUIT AS r NATURAL JOIN OFFER AS o WHERE r.team_id = 1 AND o.status = 2", nativeQuery = true)
//	List<Recruit> findAllRecruitSOnSaleByTeam(int teamID);
//
//	@Query(value = "SELECT r.* FROM RECRUIT AS r LEFT JOIN OFFER AS o WHERE r.team_id = o.team_id = 1 and o.id IS NULL", nativeQuery = true)
//	List<Recruit> findAllRecruitSNotOnSaleByTeam(int teamID);

	@Query(value = "SELECT r.* FROM RECRUIT AS r WHERE r.team_id = ?1 AND r.for_sale = true", nativeQuery = true)
	List<Recruit> findAllRecruitSOnSaleByTeam(int teamID);

	@Query(value = "SELECT r.* FROM RECRUIT AS r WHERE r.team_id = ?1 AND r.for_sale = false", nativeQuery = true)
	List<Recruit> findAllRecruitSNotOnSaleByTeam(int teamID);

	@Transactional
	@Modifying
	@Query("UPDATE Recruit r SET r.forSale = true WHERE r.id = :recruitID")
	public void putRecruitOnSale(@Param("recruitID") int recruitID);

	@Transactional
	@Modifying
	@Query("UPDATE Recruit r SET r.forSale = false WHERE r.id = :recruitID")
	public void quitRecruitOnSale(@Param("recruitID") int recruitID);

	@Transactional
	@Modifying
	@Query("UPDATE Recruit r SET r.team = :purchaserTeam WHERE r.id = :recruitID")
	void transfer(@Param("recruitID") int recruitID, @Param("purchaserTeam") Team purchaserTeam);
}

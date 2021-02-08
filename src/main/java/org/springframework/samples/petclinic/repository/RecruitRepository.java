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

	@Query("SELECT r FROM RECRUIT AS r JOIN PILOT p JOIN TEAM t WHERE r.pilot.id = :pilotID AND t.league.id = :leagueID and t.id = r.team.id and p.id = r.pilot.id")
	Optional<Recruit> findRecruitByPilotId(@Param("pilotID") int pilotID, @Param("leagueID") int leagueID);

	@Query("SELECT r FROM RECRUIT r WHERE r.team.id = :teamID")
	List<Recruit> findAllRecruits(@Param("teamID") int teamID);

	@Query("SELECT r FROM Recruit r WHERE r.team.id = :teamID")
	List<Recruit> findAllRecruitsByTeam(@Param("teamID") int teamID);

	@Query("SELECT r FROM RECRUIT r WHERE r.team.id = :teamID AND r.for_sale = false")
	List<Recruit> findAllRecruitsNotOnSaleByTeam(@Param("teamID") int teamID);

	@Query("SELECT r FROM RECRUIT r WHERE r.team.id = :teamID AND r.for_sale = true")
	List<Recruit> findAllRecruitsOnSaleByTeam(@Param("teamID") int teamID);

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

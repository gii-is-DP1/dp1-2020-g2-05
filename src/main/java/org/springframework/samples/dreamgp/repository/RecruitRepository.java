package org.springframework.samples.dreamgp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.samples.dreamgp.model.Team;

public interface RecruitRepository extends CrudRepository<Recruit, Integer> {

	@Query("SELECT r FROM Recruit r WHERE r.pilot.id = :pilotID AND r.team.league.id = :leagueID")
	Optional<Recruit> findRecruitByPilotId(@Param("pilotID") int pilotID, @Param("leagueID") int leagueID);

	@Query("SELECT r FROM Recruit r WHERE r.team.id = :teamID")
	List<Recruit> findAllRecruitsByTeam(@Param("teamID") int teamID);

	@Query("SELECT r FROM Recruit r WHERE r.team.id = :teamID AND r.forSale = false")
	List<Recruit> findAllRecruitsNotOnSaleByTeam(@Param("teamID") int teamID);

	@Query("SELECT r FROM Recruit r WHERE r.team.id = :teamID AND r.forSale = true")
	List<Recruit> findAllRecruitsOnSaleByTeam(@Param("teamID") int teamID);
	
	@Modifying
	@Query("UPDATE Recruit r SET r.forSale = true WHERE r.id = :recruitID")
	public void putRecruitOnSale(@Param("recruitID") int recruitID);

	@Modifying
	@Query("UPDATE Recruit r SET r.forSale = false WHERE r.id = :recruitID")
	public void quitRecruitOnSale(@Param("recruitID") int recruitID);

	@Modifying
	@Query("UPDATE Recruit r SET r.team = :purchaserTeam WHERE r.id = :recruitID")
	void transfer(@Param("recruitID") int recruitID, @Param("purchaserTeam") Team purchaserTeam);

}

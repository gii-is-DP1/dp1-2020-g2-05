
package org.springframework.samples.petclinic.service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.League;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNameException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamService {

	private TeamRepository teamRepository;
	private UserService userService;
	private PilotService pilotService;
	private RecruitService recruitService;
	private TablaConsultasService TCService;
	private OfferService offerService;
	private TransactionService transactionService;

	@Autowired
	public TeamService(TeamRepository teamRepository, UserService userService,
			PilotService pilotService, RecruitService recruitService, TablaConsultasService TCService, OfferService offerService, TransactionService transactionService) {
		this.teamRepository = teamRepository;
		this.userService = userService;
		this.pilotService = pilotService;
		this.recruitService = recruitService;
		this.TCService = TCService;
		this.offerService = offerService;
		this.transactionService = transactionService;
	}

	public List<Integer> findTeamsIntByUsername(String username) throws DataAccessException {
		return teamRepository.findTeamsByUsername(username);
	}
	
	public Integer findTeamsByLeagueId(Integer id) throws DataAccessException {
		return teamRepository.findTeamsByLeagueId(id);
	}

	@Transactional
	public Iterable<Team> findAllTeams() {
		return teamRepository.findAll();
	}

	public Optional<Team> findTeamById(Integer teamId) {
		return teamRepository.findById(teamId);
	}

	@Transactional(rollbackFor = DuplicatedTeamNameException.class)
	public void saveTeam(Team team) {
		boolean igual = false;

		League league = team.getLeague();
		List<Team> list = this.findTeamByLeagueId(league.getId());
		for (int i = 0; i < list.size(); i++) {
			Team t = list.get(i);

			if (t.getName().equals(team.getName()) && t.getId() != team.getId()) {
				igual = true;
			}

		}

		if (!igual) {
			teamRepository.save(team);

		} else {
			log.warn("No se ha podido guardar el equipo " + team);
		}

	}

	public void delete(Team team) {
		teamRepository.delete(team);
	}

	public List<Team> findTeamByUsername(String username) {
		return teamRepository.findTeamByUsername(username);
	}

	public Optional<Team> findTeamByUsernameAndLeagueId(String username, Integer id) {
		return teamRepository.findTeamByUsernameAndLeagueId(username, id);
	}

	public List<Team> findTeamByLeagueId(Integer id) {
		return teamRepository.findTeamByLeagueId(id);
	}

	@Transactional
	public void saveTeamMoney(Team team, Integer price) throws DataAccessException {
		team.setMoney(team.getMoney() + price);
		saveTeam(team);
	}

	@Transactional
	public void saveSystemTeam(League league) {
		Team sysTeam = new Team();
		sysTeam.setName("Sistema");
		sysTeam.setLeague(league);
		sysTeam.setMoney(0);
		sysTeam.setPoints(0);
		sysTeam.setUser(userService.findUser("admin1").get());
		saveTeam(sysTeam);
		log.debug("Creada la escudería sisteama:" + sysTeam);

		log.info(
				"Procedemos a fichar y ofertar a todos los pilotos con la escudería sistema que estén en la categoría actual");
		recruitAndOfferAll(sysTeam, TCService.getTabla().get().getCurrentCategory());
		log.debug("Fichados los pilotos de la categoría actual con la escudería sistema");
	}

	@Transactional
	public void recruitAndOfferAll(Team t, Category cat) {
		List<Pilot> pilots = pilotService.findAll();
		for (int i = 0; i < pilots.size(); i++) {
			if (pilots.get(i).getCategory().equals(cat)) {
				Recruit r = new Recruit();
				r.setPilot(pilots.get(i));
				r.setForSale(true);
				r.setTeam(t);
				recruitService.saveRecruit(r);
				offerService.putOnSale(r, r.getPilot().getBaseValue());

				// Por si luego este fichaje tiene que ser asignado al primer equipo, se pueda
				// borrar sin problemas con la base de datos, le añadimos la oferta a mano
				Offer offer = offerService.findOffersByRecruit(r.getId()).get(0);
				Set<Offer> offers = new HashSet<>();
				offers.add(offer);
				r.setOffer(offers);
				recruitService.saveRecruit(r);
			}
		}
	}

	@Transactional
	public void sellAllTeamRecruits(Team t) {
		List<Recruit> recruits = this.recruitService.getRecruitsByTeam(t.getId());
		Integer valor = 0;
		for (int i = 0; i < recruits.size(); i++) {
			Recruit r = recruits.get(i);
			if (r.getForSale()) { // Si el fichaje esta en venta, ponemos su oferta asociada en denegada para
									// evitar incoherencias
				Offer offer = r.getOffer().stream().filter(o -> o.getStatus().equals(Status.Outstanding)).findAny()
						.get();
				offer.setStatus(Status.Denied);
				offerService.saveOffer(offer);
			}
			valor += r.getPilot().getBaseValue();
			recruitService.deleteRecruit(r);
		}
		saveTeamMoney(t, valor);
		transactionService.saveTransaction(valor, "Has pasado a " + TCService.getTabla().get().getCurrentCategory()
				+ " y has obtenido el valor de tus pilotos de la categoría anterior", t);
	}

	@Transactional
	public void randomRecruit2Pilots(Team t) throws NotAllowedNumberOfRecruitsException {
		Team syst = findTeamByUsernameAndLeagueId("admin1", t.getLeague().getId()).get();
		SecureRandom rand = new SecureRandom();
		for (int i = 0; i < 2; i++) {
			List<Recruit> recruitSys = this.recruitService.getRecruitsByTeam(syst.getId());
			Integer recruitId = rand.nextInt(recruitSys.size());
			Recruit newR = recruitSys.get(recruitId);
			Offer offer = newR.getOffer().stream().filter(o -> o.getStatus().equals(Status.Outstanding)).findAny()
					.get();
			offer.setStatus(Status.Denied);
			offerService.saveOffer(offer);
			recruitService.purchaseRecruit(newR, t);
		}
	}

}

package org.springframework.samples.petclinic.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.RecruitService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leagues/{leagueId}/market")
public class OfferController {

	private static final String VIEW_OFFERS = "offers/offersList";

	private final OfferService offerService;

	private final RecruitService recruitService;

	private final TeamService teamService;

	private final UserService userService;

	private final TransactionService transactionService;

	@Autowired
	public OfferController(OfferService offerService, RecruitService recruitService, UserService userService,
			LeagueService leagueService, TeamService teamService, TransactionService transactionService) {
		this.offerService = offerService;
		this.recruitService = recruitService;
		this.userService = userService;
		this.teamService = teamService;
		this.transactionService = transactionService;
	}

	@ModelAttribute("userTeam")
	public Team getUserTeam(@PathVariable("leagueId") int leagueId) {
		Optional<Team> userTeam = teamService.findTeamByUsernameAndLeagueId(userService.getUserSession().getUsername(),
				leagueId);
		if (userTeam.isPresent()) {
			return userTeam.get();
		} else {
			return null;// Terminar redirecionando diciendo que no tienes equipo en esta liga
		}
	}

	@GetMapping
	public String getOffers(@PathVariable("leagueId") int leagueId, ModelMap modelMap) {
		modelMap.addAttribute("offers", offerService.findOffersByLeague(leagueId));
		return VIEW_OFFERS;
	}

	@GetMapping(path = "{offerId}")
	public String recruitPilot(@PathVariable("leagueId") int leagueId, @PathVariable("offerId") int offerId,
			ModelMap modelMap) {
		Optional<Offer> opo = offerService.findOfferById(offerId);

		if (opo.isPresent()) {
			Offer offer = opo.get();
			Team team = getUserTeam(leagueId);// Esdudería que va a comprar un piloto
			Integer price = offer.getPrice();
			Team sellerTeam = offer.getRecruit().getTeam();
			if (!offer.getStatus().equals(Status.Outstanding)) {
				modelMap.addAttribute("message", "This pilot isn't on sale anymore");
			} else if (team.getId() == sellerTeam.getId()) {// Si la escudería es la misma que ofrecio
															// el piloto, se cancela la oferta
				offer.setStatus(Status.Denied);
				offerService.saveOffer(offer);
				modelMap.addAttribute("message", "Offer cancelled!");
			} else if (recruitService.getRecruitsByTeam(team.getId()).size() >= 4){
				modelMap.addAttribute("message", "You already own 4 riders on this league");
			} else if (team.getMoney() >= price) { // RN-07:
				// Máximo
				// de
				// fichajes
				teamService.saveTeamMoney(team, -price);// Restar dinero al comprador
				teamService.saveTeamMoney(offer.getRecruit().getTeam(), price);// Dar dinero al vendedor

				offer.setTeam(team);
				offer.setStatus(Status.Accepted);
				offerService.saveOffer(offer);
				Pilot pilot = offer.getRecruit().getPilot(); // Piloto sobre el que se hace la compraventa
				String fullName = pilot.getName() + " " + pilot.getLastName();
				recruitService.saveRecruit(pilot, team);
				transactionService.saveTransaction(team.getMoney(), price, TransactionType.BUY, "Compra de " + fullName,
						team, offer); // Guardar
				// transaccion
				// en el
				// registro del comprador
				transactionService.saveTransaction(sellerTeam.getMoney(), price, TransactionType.SELL,
						"Venta de " + fullName, sellerTeam, offer); // Guardar
				// transaccion
				// en el
				// registro del
				// vendedor

				modelMap.addAttribute("message", "Pilot recruited!");
			}else {
				modelMap.addAttribute("message", "Not enought money to recruit this pilot");
			}
		} else {
			modelMap.addAttribute("message", "Offer not found!");
		}
		modelMap.addAttribute("leagueId", leagueId);
		return getOffers(leagueId, modelMap);
	}
}

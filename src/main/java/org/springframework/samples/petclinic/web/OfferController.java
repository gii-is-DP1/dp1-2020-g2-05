package org.springframework.samples.petclinic.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Recruit;
import org.springframework.samples.petclinic.model.Team;
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
//			LeagueService leagueService, 
			TeamService teamService, TransactionService transactionService) {
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
			Team purchaserTeam = getUserTeam(leagueId);
			Team sellerTeam = offer.getRecruit().getTeam();
			Integer price = offer.getPrice();
			if (!offer.getStatus().equals(Status.Outstanding)) {
				modelMap.addAttribute("message", "This pilot isn't on sale anymore");
			} else if (purchaserTeam.getId() == sellerTeam.getId()) {// Si la escudería es la misma que ofrecio
				// el piloto, se cancela la oferta
				offer.setStatus(Status.Denied);
				offerService.saveOffer(offer);
				recruitService.quitOnSale(offer.getRecruit());
				modelMap.addAttribute("message", "Offer cancelled!");
			} else if (recruitService.getRecruitsByTeam(purchaserTeam.getId()).size() >= 4) {// RN-07: Máximo de
																								// fichajes
				modelMap.addAttribute("message", "You already own 4 riders on this league");
			} else if (purchaserTeam.getMoney() >= price) {
				teamService.saveTeamMoney(purchaserTeam, -price);// Restar dinero al comprador
				teamService.saveTeamMoney(offer.getRecruit().getTeam(), price);// Dar dinero al vendedor

				offer.setTeam(purchaserTeam);
				offer.setStatus(Status.Accepted);
				offerService.saveOffer(offer);

				Recruit recruit = offer.getRecruit();
				recruitService.trade(offer.getRecruit(), sellerTeam, purchaserTeam); // Eliminamos el piloto del equipo
																						// vendedor y lo añadimos al
																						// equipo comprador

				transactionService.trade(price, recruit.getPilot(), sellerTeam, purchaserTeam, offer); // Guardamos los
																										// registros de
				// las transacciones en las
				// carteras de ambos equipos

				modelMap.addAttribute("message", "Pilot recruited!");
			} else {
				modelMap.addAttribute("message", "Not enought money to recruit this pilot");
			}
		} else {
			modelMap.addAttribute("message", "Offer not found!");
		}
		modelMap.addAttribute("leagueId", leagueId);
		return getOffers(leagueId, modelMap);
	}
}

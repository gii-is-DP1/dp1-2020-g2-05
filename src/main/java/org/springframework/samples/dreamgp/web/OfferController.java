package org.springframework.samples.dreamgp.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.Offer;
import org.springframework.samples.dreamgp.model.Recruit;
import org.springframework.samples.dreamgp.model.Status;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.service.OfferService;
import org.springframework.samples.dreamgp.service.RecruitService;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.samples.dreamgp.service.UserService;
import org.springframework.samples.dreamgp.service.exceptions.NoTeamInThisLeagueException;
import org.springframework.samples.dreamgp.service.exceptions.NotAllowedNumberOfRecruitsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			TeamService teamService, TransactionService transactionService) {
		this.offerService = offerService;
		this.recruitService = recruitService;
		this.userService = userService;
		this.teamService = teamService;
		this.transactionService = transactionService;
	}

	@ModelAttribute("userTeam")
	public Team getUserTeam(@PathVariable("leagueId") int leagueId) throws NoTeamInThisLeagueException {
		Optional<Team> userTeam = teamService.findTeamByUsernameAndLeagueId(userService.getUserSession().getUsername(),
				leagueId);
		if (userTeam.isPresent()) {
			return userTeam.get();
		} else {
			throw new NoTeamInThisLeagueException();
		}
	}

	@GetMapping
	public String getOffers(@PathVariable("leagueId") int leagueId, ModelMap modelMap) {
		modelMap.addAttribute("offers", offerService.findOffersByLeague(leagueId));
		return VIEW_OFFERS;
	}

	@GetMapping(path = "{offerId}")
	public String recruitPilot(@PathVariable("leagueId") int leagueId, @PathVariable("offerId") int offerId, 
			@RequestParam(value = "version", required=false) Integer version, ModelMap modelMap) throws NoTeamInThisLeagueException {
		Optional<Offer> opo = offerService.findOfferById(offerId);
		if (opo.isPresent()) {
			Offer offer = opo.get();
			Team purchaserTeam = getUserTeam(leagueId);
			Team sellerTeam = offer.getRecruit().getTeam();
			Integer price = offer.getPrice();
			if (purchaserTeam.getId() == sellerTeam.getId()) {// Si la escudería es la misma que ofrecio
				// el piloto, se cancela la oferta
				offer.setStatus(Status.Denied);
				offerService.saveOffer(offer);
				recruitService.quitOnSale(offer.getRecruit());
				modelMap.addAttribute("message", "Offer cancelled!");
				return "redirect:/leagues/{leagueId}/teams/" + purchaserTeam.getId() + "/details";
			} else if (!offer.getStatus().equals(Status.Outstanding)) {
				modelMap.addAttribute("message", "This pilot isn't on sale anymore");
			} else if(offer.getVersion()!=version) {
				modelMap.addAttribute("message", "Concurrent transaction of offer! Try again!");
			} else if (purchaserTeam.getMoney() >= price) {
				log.info("Comprando fichaje con suficiente dinero");
				try {
					Recruit recruit = offer.getRecruit();
					recruitService.purchaseRecruit(recruit, purchaserTeam);
					teamService.saveTeamMoney(purchaserTeam, -price);// Restar dinero al comprador
					teamService.saveTeamMoney(sellerTeam, price);// Dar dinero al vendedor
					transactionService.trade(price, recruit.getPilot(), sellerTeam, purchaserTeam); // su registro
					offer.setTeam(purchaserTeam);
					offer.setStatus(Status.Accepted);
					offerService.saveOffer(offer);
					modelMap.addAttribute("message", "Pilot recruited!");
				} catch (NotAllowedNumberOfRecruitsException e) {
					modelMap.addAttribute("message", "You already own 4 riders in this league!");
				}

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

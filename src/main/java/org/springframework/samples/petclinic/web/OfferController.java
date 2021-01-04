package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.RecruitService;
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

	@Autowired
	public OfferController(OfferService offerService, RecruitService recruitService, UserService userService) {
		this.offerService = offerService;
		this.recruitService = recruitService;
	}

	@ModelAttribute("userTeam")
	public Team getUserTeam(@PathVariable("leagueId") int leagueId) {
		return offerService.findTeamByUsernameLeague(leagueId);
	}
	
	@GetMapping
	public String getOffers(@PathVariable("leagueId") int leagueId, ModelMap modelMap) {
		modelMap.addAttribute("offers", offerService.findOffersByLeague(leagueId));
		System.out.println(offerService.findOffersByLeague(leagueId));
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
			if (!offer.getStatus().equals(Status.Outstanding)) {
				modelMap.addAttribute("message", "This pilot isn't on sale");
			}else if(team.getId() == offer.getRecruit().getTeam().getId()){
				offer.setStatus(Status.Denied);
				offerService.saveOffer(offer);
			} else if (team.getMoney() >= price) {
				offerService.saveTeamMoney(team, -price);// Restar dinero al comprador
				offerService.saveTeamMoney(offer.getRecruit().getTeam(), price);// Dar dinero al vendedor
				offer.setTeam(team);
				offer.setStatus(Status.Accepted);
				offerService.saveOffer(offer);
				recruitService.saveRecruit(offer.getRecruit().getPilot(), team);
				modelMap.addAttribute("message", "Pilot recruit!");
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

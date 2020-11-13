package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {
	
	private static final String VIEW_OFFERS = "offers/offersList";
	
	private final OfferService offerService;
	
	@Autowired
	public OfferController(OfferService offerService) {
		this.offerService = offerService;
	}
	
	@GetMapping
	public String getOffers(ModelMap modelMap) {
		modelMap.addAttribute("offers", offerService.findAllOffers());
		return VIEW_OFFERS;
	}
	
}

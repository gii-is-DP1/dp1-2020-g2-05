package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Trade;
import org.springframework.samples.petclinic.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TradeController {

	private TradeService tradeService;

	@Autowired
	public TradeController(TradeService tradeService) {
		super();
		this.tradeService = tradeService;
	}

	@GetMapping("/leagues/{leagueID}/teams/{teamID}/trades")
	public ModelAndView getTrades(@PathVariable("leagueID") int leagueID, @PathVariable("teamID") int teamID) {
		ModelAndView mav = new ModelAndView("leagues/tradesList");
		List<Trade> trades = tradeService.getTeamTrades(teamID);
		mav.addObject("trades", trades);
		return mav;
	}
}

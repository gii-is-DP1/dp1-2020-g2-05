package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TradeController {

	private TransactionService transactionService;

	@Autowired
	public TradeController(TransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}

	@GetMapping("/leagues/{leagueID}/teams/{teamID}/trades")
	public ModelAndView getTrades(@PathVariable("leagueID") int leagueID, @PathVariable("teamID") int teamID) {
		ModelAndView mav = new ModelAndView("leagues/transactionsList");
		List<Transaction> transactions = transactionService.getTeamTrades(teamID);
		mav.addObject("transaction", transactions);
		return mav;
	}
}

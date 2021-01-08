package org.springframework.samples.petclinic.web;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TransactionController {

	private TransactionService transactionService;
	private TeamService teamService;

	@Autowired
	public TransactionController(TransactionService transactionService, TeamService teamService) {
		super();
		this.transactionService = transactionService;
		this.teamService = teamService;
	}

	@GetMapping("/leagues/{leagueID}/teams/{teamID}/transactions")
	public ModelAndView getTrades(@PathVariable("leagueID") int leagueID, @PathVariable("teamID") int teamID) {
		ModelAndView mav = new ModelAndView("leagues/transactionsList");
		List<Transaction> l = transactionService.getTeamTransactions(teamID);
		l.sort(Comparator.reverseOrder());
		mav.addObject("transactions", l);
		mav.addObject("money", teamService.findTeamById(teamID).get().getMoney());
		return mav;
	}
}

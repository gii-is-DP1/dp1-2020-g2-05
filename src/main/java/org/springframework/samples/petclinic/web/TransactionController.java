package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransactionController {

	private TransactionService transactionService;
	private TeamService teamService;
	private UserService userService;

	@Autowired
	public TransactionController(TransactionService transactionService, TeamService teamService,
			UserService userService) {
		super();
		this.transactionService = transactionService;
		this.teamService = teamService;
		this.userService = userService;
	}

	@GetMapping("/myTeams/{teamID}/transactions")
	public ModelAndView getTransactions(@PathVariable("teamID") int teamID) {
		ModelAndView mav = new ModelAndView();
		User user = this.userService.getUserSession();
		Optional<Team> optTeam = teamService.findTeamById(teamID);
		if (optTeam.isPresent() && user.equals(optTeam.get().getUser())) {
			Team team = optTeam.get();
			mav.setViewName("leagues/transactionsList");
			List<Transaction> transactions = this.transactionService.findTransactionsByTeamId(teamID);
			mav.addObject("transactions", transactions);
			mav.addObject("money", team.getMoney());
		} else {
			mav.setViewName("redirect:/myTeams");
		}
		return mav;
	}
}
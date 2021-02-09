package org.springframework.samples.dreamgp.web;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.model.Transaction;
import org.springframework.samples.dreamgp.model.User;
import org.springframework.samples.dreamgp.service.TeamService;
import org.springframework.samples.dreamgp.service.TransactionService;
import org.springframework.samples.dreamgp.service.UserService;
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
			List<Transaction> transactions = this.transactionService.findTransactionsByTeamId(teamID).stream()
					.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
			Integer money = this.teamService.findTeamById(team.getId()).get().getMoney();
			mav.addObject("transactions", transactions);
			mav.addObject("money", money);
		} else {
			mav.setViewName("redirect:/myTeams");
		}
		return mav;
	}
}
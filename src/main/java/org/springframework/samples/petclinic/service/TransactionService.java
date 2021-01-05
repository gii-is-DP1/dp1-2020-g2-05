package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.samples.petclinic.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	private TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}

	public List<Transaction> getTeamTrades(int teamID) {
		return transactionRepository.findTradesByTeamId(teamID);
	}

	public void saveTransaction(Integer amount, TransactionType transactionType, String concept, Team team,
			Offer offer) {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setConcept(concept);
		transaction.setTeam(team);
		transaction.setOffer(offer);
		transactionRepository.save(transaction);
	}

}

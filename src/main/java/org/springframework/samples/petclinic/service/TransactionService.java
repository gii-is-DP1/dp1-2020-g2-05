package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pilot;
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

	public List<Transaction> getTeamTransactions(int teamID) {
		return transactionRepository.findTransactionsByTeamId(teamID);
	}

	public void saveTransaction(Integer amount, TransactionType transactionType, String concept, Team team,
			Offer offer) {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		Integer m = team.getMoney();
		transaction.setRemainingMoney(m);
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setConcept(concept);
		transaction.setTeam(team);
		transaction.setOffer(offer);
		transactionRepository.save(transaction);
	}

	public void trade(Integer price, Pilot pilot, Team sellerTeam, Team purchaserTeam, Offer offer) {
		String fullName = pilot.getName() + " " + pilot.getLastName();
		// Transaccion del equipo vendedor

		saveTransaction(price, TransactionType.SELL, "Venta de " + fullName, sellerTeam, offer);

		// Transaccion del equipo comprador
		saveTransaction(-price, TransactionType.BUY, "Compra de " + fullName, purchaserTeam, offer);

	}

	public Iterable<Transaction> findAll() {
		return this.transactionRepository.findAll();
	}

}

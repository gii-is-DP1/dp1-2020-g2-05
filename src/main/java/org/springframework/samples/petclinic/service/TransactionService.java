package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.samples.petclinic.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public void saveTransaction(Integer amount, TransactionType transactionType, String concept, Team team) {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		transaction.setRemainingMoney(team.getMoney());
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setConcept(concept);
		log.info("El equipo es: " + team);
		transaction.setTeam(team);
		transactionRepository.save(transaction);
	}

	public void trade(Integer price, Pilot pilot, Team sellerTeam, Team purchaserTeam) {
		String fullName = pilot.getName() + " " + pilot.getLastName();
		// Transaccion del equipo vendedor

		log.info("Transacciones de: " + price + " " + TransactionType.SELL + " " + "Venta de " + fullName + " "
				+ sellerTeam);

		saveTransaction(price, TransactionType.SELL, "Venta de " + fullName, sellerTeam);

		// Transaccion del equipo comprador
		saveTransaction(price, TransactionType.BUY, "Compra de " + fullName, purchaserTeam);

	}

	public Iterable<Transaction> findAll() {
		return this.transactionRepository.findAll();
	}

}

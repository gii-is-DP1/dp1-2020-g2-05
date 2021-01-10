package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

	private TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}

	@Transactional(readOnly = true)
	public List<Transaction> getTeamTransactions(int teamID) {
		return transactionRepository.findTransactionsByTeamId(teamID);
	}

	@Transactional
	public void saveTransaction(Integer amount, String concept, Team team) throws DataAccessException {
		Transaction transaction = new Transaction();
		transaction.setDate(LocalDate.now());
		transaction.setRemainingMoney(team.getMoney());
		transaction.setAmount(amount);
		transaction.setConcept(concept);
		transaction.setTeam(team);
		transactionRepository.save(transaction);
	}

	public void trade(Integer price, Pilot pilot, Team sellerTeam, Team purchaserTeam) {
		String fullName = pilot.getName() + " " + pilot.getLastName();
		// Transaccion del equipo vendedor

		saveTransaction(price, "Venta de " + fullName, sellerTeam);

		// Transaccion del equipo comprador
		saveTransaction(-price, "Compra de " + fullName, purchaserTeam);

	}

	@Transactional(readOnly = true)
	public Iterable<Transaction> findAll() {
		return this.transactionRepository.findAll();
	}

}
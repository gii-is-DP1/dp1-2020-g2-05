package org.springframework.samples.dreamgp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.dreamgp.model.Lineup;
import org.springframework.samples.dreamgp.model.Pilot;
import org.springframework.samples.dreamgp.model.Team;
import org.springframework.samples.dreamgp.model.Transaction;
import org.springframework.samples.dreamgp.repository.TransactionRepository;
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

	// Operaciones de Create
	public Transaction createTransaction(Integer amount, String concept, Team team) {
		Transaction t = new Transaction();
		t.setDate(LocalDate.now());
		t.setRemainingMoney(team.getMoney());
		t.setAmount(amount);
		t.setConcept(concept);
		t.setTeam(team);
		return t;
	}

	@Transactional
	public void saveTransaction(Integer amount, String concept, Team team) throws DataAccessException {
		Transaction t = createTransaction(amount, concept, team);
		transactionRepository.save(t);
		List<Transaction> transacciones = this.transactionRepository.findByTeamId(team.getId());
		if (transacciones.size() == 10) 
			this.delete(transacciones.get(0));
		
	}

	public void trade(Integer price, Pilot pilot, Team sellerTeam, Team purchaserTeam) {
		// Transaccion del equipo vendedor
		saveTransaction(price, "Venta de " + pilot.getFullName(), sellerTeam);

		// Transaccion del equipo comprador
		saveTransaction(-price, "Compra de " + pilot.getFullName(), purchaserTeam);
	}

	public void results(Integer amount, Lineup alineacion, String RaceCode) {
		saveTransaction(amount, "Resultados de " + alineacion.getRider1().getFullName() + " y "
				+ alineacion.getRider2().getFullName() + " en " + RaceCode, alineacion.getTeam());
	}

	//Operaciones de Read
	
	@Transactional(readOnly = true)
	public Iterable<Transaction> findAll() {
		return this.transactionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Transaction> findTransactionsByTeamId(int teamID) {
		return this.transactionRepository.findByTeamId(teamID);
	}

	// Operaciones de Delete
	
	@Transactional
	private void delete(Transaction t) {
		this.transactionRepository.delete(t);
	}

}
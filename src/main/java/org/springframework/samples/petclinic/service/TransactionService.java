package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Lineup;
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

	@Transactional
	public void saveTransaction(Integer amount, String concept, Team team) throws DataAccessException {
		Transaction t = createTransaction(amount, concept, team);
		transactionRepository.save(t);
		List<Transaction> transacciones = team.getTransactions();
		if (transacciones.size() == 10) {
			this.delete(transacciones.get(0));
		}
	}

	public Transaction createTransaction(Integer amount, String concept, Team team) {
		Transaction t = new Transaction();
		t.setDate(LocalDate.now());
		t.setRemainingMoney(team.getMoney());
		t.setAmount(amount);
		t.setConcept(concept);
		t.setTeam(team);
		return t;
	}

	public void trade(Integer price, Pilot pilot, Team sellerTeam, Team purchaserTeam) {
		// Transaccion del equipo vendedor
		saveTransaction(price, "Venta de " + pilot.getFullName(), sellerTeam);

		// Transaccion del equipo comprador
		saveTransaction(-price, "Compra de " + pilot.getFullName(), purchaserTeam);
	}

	public void results(Integer i, Lineup l, String RaceCode) {
		saveTransaction(i, "Resultados de " + l.getRider1().getFullName() + " y " + l.getRider2().getFullName() + " en "
				+ RaceCode, l.getTeam());
	}

	@Transactional(readOnly = true)
	public Iterable<Transaction> findAll() {
		return this.transactionRepository.findAll();
	}

	@Transactional
	private void delete(Transaction t) {
		this.transactionRepository.delete(t);
	}

	@Transactional
	public void eliminarTransaccionesAntiguas() {
		this.transactionRepository.deleteAll();
	}

}
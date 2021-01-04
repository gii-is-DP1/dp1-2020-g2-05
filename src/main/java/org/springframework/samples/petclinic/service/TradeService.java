package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Trade;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.samples.petclinic.repository.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

	private TradeRepository tradeRepository;

	@Autowired
	public TradeService(TradeRepository tradeRepository) {
		super();
		this.tradeRepository = tradeRepository;
	}

	public List<Trade> getTeamTrades(int teamID) {
		return tradeRepository.findTradesByTeamId(teamID);
	}

	public void saveTrade(Integer price, TransactionType transactionType, String concept, Team team, Offer offer) {
		Trade trade = new Trade();
		trade.setDate(LocalDate.now());
		trade.setPrice(price);
		trade.setTransactionType(transactionType);
		trade.setConcept(concept);
		trade.setTeam(team);
		trade.setOffer(offer);
		tradeRepository.save(trade);
	}

}

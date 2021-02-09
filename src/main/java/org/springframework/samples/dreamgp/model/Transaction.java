package org.springframework.samples.dreamgp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Transaction")
public class Transaction extends BaseEntity implements Comparable<Transaction> {

	@Column(name = "date")
	@NotNull
	private LocalDate date;

	@Column(name = "remainingMoney")
	@NotNull
	private Integer remainingMoney;

	@Column(name = "amount")
	@NotNull
	private Integer amount;

	@Column(name = "concept")
	@NotBlank
	private String concept;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Team team;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getRemainingMoney() {
		return remainingMoney;
	}

	public void setRemainingMoney(Integer remainingMoney) {
		this.remainingMoney = remainingMoney;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer price) {
		this.amount = price;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public int compareTo(Transaction o) {
		return this.id.compareTo(o.id);
	}

	@Override
	public String toString() {
		return "Transaction [date=" + date + ", remainingMoney=" + remainingMoney + ", amount=" + amount + ", concept="
				+ concept + ", team=" + team + "]";
	}

}
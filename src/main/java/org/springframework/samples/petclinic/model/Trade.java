package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Trade")
public class Trade extends BaseEntity {

	@Column(name = "date")
	@NotNull
	private LocalDate date;

	@Column(name = "price")
	@NotNull
	private Integer price;

	@Enumerated(EnumType.STRING)
	@Column(name = "transactionType")
	@NotNull
	private TransactionType transactionType;

	@Column(name = "concept")
	@NotBlank
	private String concept;

	@ManyToOne()
	private Team team;

	@OneToOne()
	private Offer offer;

//	public Trade(@NotNull LocalDate date, @NotNull Integer price, @NotNull TransactionType transactionType,
//			@NotBlank String concept, Team team, Offer offer) {
//		super();
//		this.date = date;
//		this.price = price;
//		this.transactionType = transactionType;
//		this.concept = concept;
//		this.team = team;
//		this.offer = offer;
//	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
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

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

}

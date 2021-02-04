package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "team")
public class Team extends BaseEntity implements Comparable<Team> {

	@Column(name = "name")
	@NotEmpty
	private String name;

	@Column(name = "points")
	@NotNull
	@Min(0)
	private Integer points;

	@Column(name = "money")
	@NotNull
	@Min(0)
	private Integer money;

	@ManyToOne()
	@JoinColumn(name = "league_id")
	private League league;

	@ManyToOne()
	@JoinColumn(name = "username")
	private User user;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Lineup> lineups;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Offer> offer;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Recruit> recruit;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<Transaction> transactions;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league2) {
		this.league = league2;
	}

	protected Set<Transaction> getTransactionsInternal() {
		if (this.transactions == null) {
			this.transactions = new HashSet<>();
		}
		return this.transactions;
	}

	protected void setTransactionsInternal(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions() {
		List<Transaction> t = new ArrayList<>(getTransactionsInternal());
		t.sort(Comparator.reverseOrder());
		return Collections.unmodifiableList(t);
	}

	@Override
	public String toString() {
		return "Team [name =" + name + ", points=" + points + ", money=" + money + " username =" + user + "]";
	}

	@Override
	public int compareTo(Team o) {
		return Integer.valueOf(this.getPoints()).compareTo(Integer.valueOf(o.getPoints()));
	}

}

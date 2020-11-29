package org.springframework.samples.petclinic.model;

import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User{
	@Id
	@NotEmpty
	String username;
	
	@Column(name = "email")
	@NotEmpty
	String email;
	
	@Column(name = "password")
	@NotEmpty
	String password;
	
	
	boolean enabled;
	
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Team> team;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usernamesend")
	private Set<Message> messages_send;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usernamereceive")
	private Set<Message> messages_received;
	
	@ManyToMany
	@JoinTable(name="friends", joinColumns={@JoinColumn(name="userqueagrega")}, inverseJoinColumns={@JoinColumn(name="useragregado")})

	private Set<User> friends;
	
	@Override
	public String toString() {
		return  username ;
	}
}

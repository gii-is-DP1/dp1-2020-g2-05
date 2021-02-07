package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


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
	@Email
	String email;
	
	@Column(name = "password")
	@NotEmpty
	String password;
	
	@Column(name = "imgperfil")
	@NotEmpty
	String imgperfil;
	
	boolean enabled;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Authorities> authorities;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Team> team;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usernamesend")
	private Set<Message> messages_send;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usernamereceive")
	private Set<Message> messages_received;
	
	@ManyToMany
	@JoinTable(name="friends", joinColumns={@JoinColumn(name="userqueagrega")}, inverseJoinColumns={@JoinColumn(name="useragregado")})
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<User> friends;
	
	@Override
	public String toString() {
		return  username ;
	}
}

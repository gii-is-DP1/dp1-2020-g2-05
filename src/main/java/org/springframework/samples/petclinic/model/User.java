package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User{
	@Id
	String username;
	
	String email;
	
	String password;
	
	boolean enabled;
	
	Rol rol;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Team> team;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usernamesend")
	private Set<Message> messages_send;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usernamereceive")
	private Set<Message> messages_received;
	
	@Override
	public String toString() {
		return  username ;
	}
}

package org.springframework.samples.petclinic.model;

import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message extends BaseEntity{
	
	
	String asunto;
	
	String cuerpo;
	
	@ManyToOne
	@JoinColumn(name = "usernamesend")
	User usernamesend;
	
	@ManyToOne
	@JoinColumn(name = "usernamereceive")
	User usernamereceive;
	
	
}

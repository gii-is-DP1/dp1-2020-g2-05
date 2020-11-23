package org.springframework.samples.petclinic.model;

import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message extends BaseEntity{
	
	@NotNull
	@Column(name = "asunto")        
	String asunto;
	
	@NotNull
	@Column(name = "cuerpo")        
	String cuerpo;
	
	@NotNull
    @Column(columnDefinition = "integer default 0")
	Integer visto;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "usernamesend")
	User usernamesend;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "usernamereceive")
	User usernamereceive;

	
	
	
}

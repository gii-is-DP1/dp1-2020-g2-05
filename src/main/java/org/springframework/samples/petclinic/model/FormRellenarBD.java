package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import motogpAPI.Category;


@Entity
@Table(name = "FormRellenarBD")

public class FormRellenarBD extends BaseEntity {
	
	
	@Column(name = "anyoInicial")
	@NotEmpty
	@NotNull
	private Integer anyoInicial;
	
	@Column(name = "anyoFinal")
	@NotEmpty
	@NotNull
	private Integer anyoFinal;
	
	@Column(name = "Category")
	@NotEmpty
	@NotNull
	private Category Category;

	public Integer getAnyoInicial() {
		return anyoInicial;
	}

	public void setAnyoInicial(Integer anyoInicial) {
		this.anyoInicial = anyoInicial;
	}

	public Integer getAnyoFinal() {
		return anyoFinal;
	}

	public void setAnyoFinal(Integer anyoFinal) {
		this.anyoFinal = anyoFinal;
	}

	public Category getCategory() {
		return Category;
	}

	public void setCategory(motogpAPI.Category categoria) {
		Category = categoria;
	}
	
	@Override
	public String toString() {
		return "FormRellenarBD [anyoInicial =" + anyoInicial + ", anyoFinal=" + anyoFinal + ", Category=" + Category +  "]";
	}


}

package org.springframework.samples.dreamgp.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.sun.istack.NotNull;




public class FormRellenarBD {
	
	
	@Min(2015)
	@Max(2019)
	@NotNull
	private Integer anyoInicial;
	
	@Min(2016)
	@Max(2019)
	@NotNull
	private Integer anyoFinal;
	

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

	public void setCategory(Category categoria) {
		Category = categoria;
	}
	
	@Override
	public String toString() {
		return "FormRellenarBD [anyoInicial =" + anyoInicial + ", anyoFinal=" + anyoFinal + ", Category=" + Category +  "]";
	}


}

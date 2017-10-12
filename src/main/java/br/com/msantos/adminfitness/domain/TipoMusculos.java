package br.com.msantos.adminfitness.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class TipoMusculos extends GenericDomain{
	
	@Column(length = 50, nullable = false)
	private String musculo;

	public String getMusculo() {
		return musculo;
	}

	public void setMusculo(String musculo) {
		this.musculo = musculo;
	}
	
	

}

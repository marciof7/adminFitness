package br.com.msantos.adminfitness.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Exercicios extends GenericDomain{
	
	@Column(length = 50, nullable = false)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoMusculos tipoMusculos;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoMusculos getTipoMusculos() {
		return tipoMusculos;
	}

	public void setTipoMusculos(TipoMusculos tipoMusculos) {
		this.tipoMusculos = tipoMusculos;
	}

	
	

}

package br.com.msantos.adminfitness.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class Permissoes extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoUsuario tipoUsuario;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "permissoes")
	private List<ItemPermissoes> itensPermissoes;

	public List<ItemPermissoes> getItensPermissoes() {
		return itensPermissoes;
	}

	public void setItensPermissoes(List<ItemPermissoes> itensPermissoes) {
		this.itensPermissoes = itensPermissoes;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}

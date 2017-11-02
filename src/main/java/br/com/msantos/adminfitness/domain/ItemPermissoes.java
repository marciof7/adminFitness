package br.com.msantos.adminfitness.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ItemPermissoes extends GenericDomain{
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private SubMenu subMenu;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Permissoes permissoes;

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}

	public Permissoes getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Permissoes permissoes) {
		this.permissoes = permissoes;
	}

}

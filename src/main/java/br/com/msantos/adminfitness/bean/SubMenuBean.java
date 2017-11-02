package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.AcessoDAO;
import br.com.msantos.adminfitness.dao.SubMenuDAO;
import br.com.msantos.adminfitness.domain.Acesso;
import br.com.msantos.adminfitness.domain.SubMenu;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SubMenuBean implements GenericBean, Serializable {

	private SubMenu subMenu;
	private List<SubMenu> listaSubMenu;
	private List<Acesso> listaAcesso;

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}

	public List<SubMenu> getListaSubMenu() {
		return listaSubMenu;
	}

	public void setListaSubMenu(List<SubMenu> listaSubMenu) {
		this.listaSubMenu = listaSubMenu;
	}

	public List<Acesso> getlistaAcesso() {
		return listaAcesso;
	}

	public void setlistaAcesso(List<Acesso> listaAcesso) {
		this.listaAcesso = listaAcesso;
	}

	@PostConstruct
	public void listar() {
		try {
			SubMenuDAO subMenuDao = new SubMenuDAO();
			listaSubMenu = subMenuDao.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os submenus!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		try {
			subMenu = new SubMenu();

			AcessoDAO acessoDao = new AcessoDAO();
			listaAcesso = acessoDao.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar gerar um novo submenu!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			SubMenuDAO subMenuDao = new SubMenuDAO();
			subMenuDao.merge(subMenu);

			novo();
			listaSubMenu = subMenuDao.listarOrdenado("descricao");

			Messages.addGlobalInfo("SubMenu salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o subMenu!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			subMenu = (SubMenu) evento.getComponent().getAttributes().get("selecionado");

			SubMenuDAO subMenuDao = new SubMenuDAO();
			subMenuDao.excluir(subMenu);

			listaSubMenu =subMenuDao.listarOrdenado("descricao");

			Messages.addGlobalInfo("SubMenu excluído com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir o subMenu!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			subMenu = (SubMenu) evento.getComponent().getAttributes().get("selecionado");

			AcessoDAO acessoDao = new AcessoDAO();
			listaAcesso = acessoDao.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o subMenu!");
			erro.printStackTrace();
		}
	}

}

package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.TipoUsuarioDAO;
import br.com.msantos.adminfitness.domain.TipoUsuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoUsuarioBean implements GenericBean, Serializable {

	private TipoUsuario tipoUsuario;
	private List<TipoUsuario> listaTipoUsuarios;

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<TipoUsuario> getListaTipoUsuarios() {
		return listaTipoUsuarios;
	}

	public void setListaTipoUsuarios(List<TipoUsuario> listaTipoUsuarios) {
		this.listaTipoUsuarios = listaTipoUsuarios;
	}

	@Override
	@PostConstruct
	public void listar() {
		try {

			TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
			listaTipoUsuarios = tipoUsuarioDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os tipos de usuários!");
			erro.printStackTrace();
		}

	}

	@Override
	public void novo() {
		tipoUsuario = new TipoUsuario();

	}

	@Override
	public void salvar() {
		try {

			TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
			tipoUsuarioDAO.merge(tipoUsuario);

			novo();
			listaTipoUsuarios = tipoUsuarioDAO.listarOrdenado("descricao");

			Messages.addGlobalInfo("Tipo de usuário salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o tipo de usuário!");
			erro.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			tipoUsuario = (TipoUsuario) evento.getComponent().getAttributes().get("selecionado");

			TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();

			listaTipoUsuarios = tipoUsuarioDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o tipo de usuário!");
			erro.printStackTrace();
		}
	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			tipoUsuario = (TipoUsuario) evento.getComponent().getAttributes().get("selecionado");

			TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
			tipoUsuarioDAO.excluir(tipoUsuario);

			listaTipoUsuarios = tipoUsuarioDAO.listarOrdenado("descricao");
			
			Messages.addGlobalInfo("Tipo de usuários excluído com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o tipo de usuário!");
			erro.printStackTrace();
		}
	}

}

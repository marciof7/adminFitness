package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.TipoFuncionarioDAO;
import br.com.msantos.adminfitness.domain.TipoFuncionario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoFuncionarioBean implements GenericBean, Serializable {

	private TipoFuncionario tipoFuncionario;
	private List<TipoFuncionario> TipoFuncionarios;

	public TipoFuncionario getTipoFuncionario() {
		return tipoFuncionario;
	}

	public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}

	public List<TipoFuncionario> getTipoFuncionarios() {
		return TipoFuncionarios;
	}

	public void setTipoFuncionarios(List<TipoFuncionario> tipoFuncionarios) {
		TipoFuncionarios = tipoFuncionarios;
	}

	@Override
	@PostConstruct
	public void listar() {
		try {

			TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO();
			TipoFuncionarios = tipoFuncionarioDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os tipo de funcionários!");
			erro.printStackTrace();
		}

	}

	@Override
	public void novo() {
		tipoFuncionario = new TipoFuncionario();

	}

	@Override
	public void salvar() {
		try {

			TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO();
			tipoFuncionarioDAO.merge(tipoFuncionario);

			novo();
			TipoFuncionarios = tipoFuncionarioDAO.listarOrdenado("descricao");

			Messages.addGlobalInfo("Tipo de Funcionário salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o tipo de funcionário!");
			erro.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			tipoFuncionario = (TipoFuncionario) evento.getComponent().getAttributes().get("selecionado");

			TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO();

			TipoFuncionarios = tipoFuncionarioDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o estado!");
			erro.printStackTrace();
		}
	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			tipoFuncionario = (TipoFuncionario) evento.getComponent().getAttributes().get("selecionado");

			TipoFuncionarioDAO tipoFuncionarioDAO = new TipoFuncionarioDAO();
			tipoFuncionarioDAO.excluir(tipoFuncionario);

			TipoFuncionarios = tipoFuncionarioDAO.listarOrdenado("descricao");
			
			Messages.addGlobalInfo("Tipo de Funcionário excluído com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o estado!");
			erro.printStackTrace();
		}
	}

}

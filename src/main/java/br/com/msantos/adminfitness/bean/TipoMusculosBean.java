package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.TipoMusculosDAO;
import br.com.msantos.adminfitness.domain.TipoMusculos;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoMusculosBean implements GenericBean, Serializable {

	private TipoMusculos tipoMusculo;
	private List<TipoMusculos> tipoMusculos;

	public TipoMusculos getTipoMusculo() {
		return tipoMusculo;
	}

	public void setTipoMusculo(TipoMusculos tipoMusculo) {
		this.tipoMusculo = tipoMusculo;
	}

	public List<TipoMusculos> getTipoMusculos() {
		return tipoMusculos;
	}

	public void setTipoMusculos(List<TipoMusculos> tipoMusculos) {
		this.tipoMusculos = tipoMusculos;
	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			TipoMusculosDAO tipoMusculosDAO = new TipoMusculosDAO();
			tipoMusculos = tipoMusculosDAO.listarOrdenado("musculo");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os tipos de músculos!");
			erro.printStackTrace();
		}
	}

	@Override
	public void novo() {
		tipoMusculo = new TipoMusculos();

	}

	@Override
	public void salvar() {
		try {
			TipoMusculosDAO tipoMusculosDAO = new TipoMusculosDAO();
			tipoMusculosDAO.merge(tipoMusculo);

			novo();
			tipoMusculos = tipoMusculosDAO.listarOrdenado("musculo");

			Messages.addGlobalInfo("Tipo de músculo salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o tipo de músculo!");
			erro.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {

			tipoMusculo = (TipoMusculos) evento.getComponent().getAttributes().get("selecionado");

			TipoMusculosDAO tipoMusculosDAO = new TipoMusculosDAO();

			tipoMusculos = tipoMusculosDAO.listarOrdenado("musculo");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o tipo de músculo!");
			erro.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {

			tipoMusculo = (TipoMusculos) evento.getComponent().getAttributes().get("selecionado");

			TipoMusculosDAO tipoMusculosDAO = new TipoMusculosDAO();
			tipoMusculosDAO.excluir(tipoMusculo);

			tipoMusculos = tipoMusculosDAO.listarOrdenado("musculo");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar exclluir o tipo de músculo!");
			erro.printStackTrace();
		}
	}

}

package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.CategoriaMuscularDAO;
import br.com.msantos.adminfitness.domain.CategoriaMuscular;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CategoriaMuscularBean implements GenericBean, Serializable {

	private CategoriaMuscular categoriaMuscular;
	private List<CategoriaMuscular> listaCategorias;
	
	public CategoriaMuscular getCategoriaMuscular() {
		return categoriaMuscular;
	}

	public void setCategoriaMuscular(CategoriaMuscular categoriaMuscular) {
		this.categoriaMuscular = categoriaMuscular;
	}

	public List<CategoriaMuscular> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<CategoriaMuscular> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			CategoriaMuscularDAO categoriaMuscularDAO = new CategoriaMuscularDAO();
			listaCategorias = categoriaMuscularDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar as categorias musculares!");
			erro.printStackTrace();
		}
	}

	@Override
	public void novo() {
		categoriaMuscular = new CategoriaMuscular();

	}

	@Override
	public void salvar() {
		try {
			CategoriaMuscularDAO categoriaMuscularDAO = new CategoriaMuscularDAO();
			categoriaMuscularDAO.merge(categoriaMuscular);

			novo();
			listaCategorias = categoriaMuscularDAO.listarOrdenado("descricao");

			Messages.addGlobalInfo("Categoria muscular salva com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar a categoria muscular!");
			erro.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {

			categoriaMuscular = (CategoriaMuscular) evento.getComponent().getAttributes().get("selecionado");

			CategoriaMuscularDAO categoriaMuscularDAO = new CategoriaMuscularDAO();

			listaCategorias = categoriaMuscularDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar a categoria muscular!");
			erro.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {

			categoriaMuscular = (CategoriaMuscular) evento.getComponent().getAttributes().get("selecionado");

			CategoriaMuscularDAO categoriaMuscularDAO = new CategoriaMuscularDAO();
			categoriaMuscularDAO.excluir(categoriaMuscular);

			listaCategorias = categoriaMuscularDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar exclluir a categoria muscular!");
			erro.printStackTrace();
		}
	}

}

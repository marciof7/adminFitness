package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.AulaDAO;
import br.com.msantos.adminfitness.domain.Aula;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AulaBean implements GenericBean, Serializable {

	private Aula aula;
	private List<Aula> aulas;

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

	@PostConstruct
	public void listar() {
		try {
			AulaDAO aulaDAO = new AulaDAO();
			aulas = aulaDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar lista a aula!");
			erro.printStackTrace();
		}
	}

	@Override
	public void novo() {
		aula = new Aula();

	}

	@Override
	public void salvar() {
		try {
			AulaDAO aulaDAO = new AulaDAO();
			aulaDAO.merge(aula);
			
			novo();
			aulas = aulaDAO.listarOrdenado("descricao");

			Messages.addGlobalInfo("Aula salva com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar a aula!");
			erro.printStackTrace();
		}
	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			aula = (Aula) evento.getComponent().getAttributes().get("selecionado");

			AulaDAO aulaDAO = new AulaDAO();

			aulas = aulaDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar a aula!");
			erro.printStackTrace();
		}

	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			aula = (Aula) evento.getComponent().getAttributes().get("selecionado");

			AulaDAO aulaDAO = new AulaDAO();
			aulaDAO.excluir(aula);

			aulas = aulaDAO.listarOrdenado("descricao");
			
			Messages.addGlobalInfo("Aula excluída com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir a aula!");
			erro.printStackTrace();
		}

	}

}

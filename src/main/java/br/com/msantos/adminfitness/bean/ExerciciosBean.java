package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.ExerciciosDAO;
import br.com.msantos.adminfitness.dao.TipoMusculosDAO;
import br.com.msantos.adminfitness.domain.Exercicios;
import br.com.msantos.adminfitness.domain.TipoMusculos;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ExerciciosBean implements GenericBean, Serializable {

	private Exercicios exercicio;
	private List<Exercicios> listaExercicios;
	private List<TipoMusculos> tipoMusculos;

	public Exercicios getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicios exercicio) {
		this.exercicio = exercicio;
	}

	public List<Exercicios> getListaExercicios() {
		return listaExercicios;
	}

	public void setListaExercicios(List<Exercicios> listaExercicios) {
		this.listaExercicios = listaExercicios;
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
			ExerciciosDAO exerciciosDAO = new ExerciciosDAO();
			listaExercicios = exerciciosDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os exercícios!");
			erro.printStackTrace();
		}

	}

	@Override
	public void novo() {
		try {

			exercicio = new Exercicios();

			TipoMusculosDAO tipoMusculosDAO = new TipoMusculosDAO();
			tipoMusculos = tipoMusculosDAO.listarOrdenado("musculo");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar gerar um novo exercício!");
			erro.printStackTrace();
		}

	}

	@Override
	public void salvar() {
		try {
			ExerciciosDAO exerciciosDAO = new ExerciciosDAO();
			exerciciosDAO.merge(exercicio);

			novo();
			listaExercicios = exerciciosDAO.listarOrdenado("descricao");

			Messages.addGlobalInfo("Exercício salvo	 com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o exercício!");
			erro.printStackTrace();
		}

	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			exercicio = (Exercicios) evento.getComponent().getAttributes().get("selecionado");

			TipoMusculosDAO tipoMusculosDAO = new TipoMusculosDAO();
			tipoMusculos = tipoMusculosDAO.listarOrdenado("musculo");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o exercício!");
			erro.printStackTrace();
		}
	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			exercicio = (Exercicios) evento.getComponent().getAttributes().get("selecionado");
			
			ExerciciosDAO exerciciosDAO = new ExerciciosDAO();
			exerciciosDAO.excluir(exercicio);
			
			listaExercicios = exerciciosDAO.listarOrdenado("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir o exercício!");
			erro.printStackTrace();
		}
	}
}

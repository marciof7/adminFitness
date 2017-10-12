package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.EstadoDAO;
import br.com.msantos.adminfitness.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements GenericBean, Serializable{
	
	private Estado estado;
	private List<Estado> estados;
	
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listarOrdenado("nome");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os estados!");
			erro.printStackTrace();
		}
	}
	
	public void novo() {
		estado = new Estado();
	}
	
	public void salvar() {
		try {
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.merge(estado);
		
		novo();
		estados = estadoDAO.listarOrdenado("nome");
		
		Messages.addGlobalInfo("Estado salvo com sucesso!");
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o estado!");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
		estado = (Estado) evento.getComponent().getAttributes().get("selecionado");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.excluir(estado);
		
		estados = estadoDAO.listarOrdenado("nome");
		
		Messages.addGlobalInfo("Estado excluído com sucesso!");
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir o estado!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
		estado = (Estado) evento.getComponent().getAttributes().get("selecionado");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		
		estados = estadoDAO.listarOrdenado("nome");
		
		Messages.addGlobalInfo("Estado editado com sucesso!");
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o estado!");
			erro.printStackTrace();
		}
	}
	
	
	
}

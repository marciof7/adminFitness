package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.AcessoDAO;
import br.com.msantos.adminfitness.domain.Acesso;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AcessoBean implements GenericBean, Serializable{

	private Acesso acesso;
	private List<Acesso> listaAcesso;

	public Acesso getacesso() {
		return acesso;
	}

	public void setacesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public List<Acesso> getlistaAcesso() {
		return listaAcesso;
	}

	public void setlistaAcesso(List<Acesso> listaAcesso) {
		this.listaAcesso = listaAcesso;
	}

	@PostConstruct
	public void listar() {
		try{
			
		AcessoDAO acessoDAO = new AcessoDAO();
		listaAcesso = acessoDAO.listarOrdenado("descricao");
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os acessos!");
			erro.printStackTrace();
		}
	}

	@Override
	public void novo() {
		acesso = new Acesso();
		
	}

	@Override
	public void salvar() {
		try{
			AcessoDAO acessoDAO = new AcessoDAO();
			acessoDAO.merge(acesso);
			
			novo();
			
			listaAcesso = acessoDAO.listarOrdenado("descricao");
			
			Messages.addGlobalInfo("Acesso salvo com sucesso!");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o acesso!");
			erro.printStackTrace();
		}
		
	}

	@Override
	public void editar(ActionEvent evento) {
		try {
			acesso = (Acesso) evento.getComponent().getAttributes().get("selecionado");
			
			AcessoDAO acessoDAO = new AcessoDAO();
			
			listaAcesso = acessoDAO.listarOrdenado("descricao");
			
			}catch (RuntimeException erro) {
				Messages.addGlobalError("Erro ao tentar editar o acesso!");
				erro.printStackTrace();
			}
		
	}

	@Override
	public void excluir(ActionEvent evento) {
		try {
			acesso = (Acesso) evento.getComponent().getAttributes().get("selecionado");
			
			AcessoDAO acessoDAO = new AcessoDAO();
			acessoDAO.excluir(acesso);
			
			listaAcesso = acessoDAO.listarOrdenado("descricao");
			
			Messages.addGlobalInfo("acesso excluído com sucesso!");
			}catch (RuntimeException erro) {
				Messages.addGlobalError("Erro ao tentar excluir o acesso!");
				erro.printStackTrace();
			}
		
	}

}

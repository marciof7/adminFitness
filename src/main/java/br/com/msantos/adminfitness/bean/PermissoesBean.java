package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.PermissoesDAO;
import br.com.msantos.adminfitness.dao.SubMenuDAO;
import br.com.msantos.adminfitness.dao.TipoUsuarioDAO;
import br.com.msantos.adminfitness.domain.ItemPermissoes;
import br.com.msantos.adminfitness.domain.Permissoes;
import br.com.msantos.adminfitness.domain.SubMenu;
import br.com.msantos.adminfitness.domain.TipoUsuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PermissoesBean implements Serializable{
	private Permissoes permissoes;
	
	private List<SubMenu> listaSubMenus;
	private List<ItemPermissoes> itens;
	private List<TipoUsuario> ListaTipoUsuarios;
	
	public Permissoes getPermissoes() {
		return permissoes;
	}
	
	public void setPermissoes(Permissoes permissoes) {
		this.permissoes = permissoes;
	}
	
	public List<SubMenu> getListaSubMenus() {
		return listaSubMenus;
	}

	public void setListaSubMenus(List<SubMenu> listaSubMenus) {
		this.listaSubMenus = listaSubMenus;
	}
	
	public List<ItemPermissoes> getItens() {
		return itens;
	}
	
	public void setItens(List<ItemPermissoes> itens) {
		this.itens = itens;
	}
	
	public List<TipoUsuario> getListaTipoUsuarios() {
		return ListaTipoUsuarios;
	}
	
	public void setListaTipoUsuarios(List<TipoUsuario> listaTipoUsuarios) {
		ListaTipoUsuarios = listaTipoUsuarios;
	}


	@PostConstruct
	public void listar() {
		try{
			
			SubMenuDAO subMenuDAO = new SubMenuDAO();
			listaSubMenus = subMenuDAO.listar();
			
			itens = new ArrayList<>();
			
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os estados!");
			erro.printStackTrace();
		}
		
	}
	
	public void adicionar(ActionEvent evento){
		
		SubMenu itemsubMenu = (SubMenu) evento.getComponent().getAttributes().get("selecionado");
		
		int achou = -1;
		
		for(int posicao = 0; posicao < itens.size(); posicao ++){
			if(itens.get(posicao).getSubMenu().getDescricao().equals(itemsubMenu.getDescricao())){
				achou = posicao;
			}
		}
		
		if(achou < 0){
			ItemPermissoes itensPermissoes = new ItemPermissoes();
			itensPermissoes.setSubMenu(itemsubMenu);
			
			itens.add(itensPermissoes);
			
		}else{
			
			ItemPermissoes itensPermissoes = itens.get(achou);
			
			Messages.addGlobalError("Permissão " + itensPermissoes.getSubMenu().getDescricao() + " já adicionado!" );
		}
		
	}
	
	public void remover(ActionEvent evento){
		
		ItemPermissoes itemPermissoes = (ItemPermissoes) evento.getComponent().getAttributes().get("selecionado");
		
		int achou = -1;
		for(int posicao = 0; posicao < itens.size(); posicao ++){
			if(itens.get(posicao).equals(itemPermissoes)){
				achou = posicao;
			}
		}
		
		if(achou > -1){
			itens.remove(achou);
		}
	}
	
	public void finalizar(){
		try{
			
			permissoes = new Permissoes();
			
			TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
			ListaTipoUsuarios = tipoUsuarioDAO.listarOrdenado("descricao");
			
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar finalizar as permissões!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try{
			
			if(itens.size() == 0){
				Messages.addGlobalError("Adicione ao menos 1 permissão!");
				return;
			}
			
			PermissoesDAO permissoesDAO = new PermissoesDAO();
			permissoesDAO.salvar(permissoes, itens);
			
			listar();
			
			Messages.addGlobalInfo("Permissões salvas com sucesso!");
			
		}catch(RuntimeException erro){
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao tentar salvar as permisões!");
		}
		
	}

	public void editar(ActionEvent evento) {
		// TODO Auto-generated method stub
		
	}

	
}

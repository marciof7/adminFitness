package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.dao.PessoaDAO;
import br.com.msantos.adminfitness.dao.TipoUsuarioDAO;
import br.com.msantos.adminfitness.dao.UsuarioDAO;
import br.com.msantos.adminfitness.domain.Pessoa;
import br.com.msantos.adminfitness.domain.TipoUsuario;
import br.com.msantos.adminfitness.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements GenericBean, Serializable {

	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Pessoa> pessoas;
	private List<TipoUsuario> TipoFuncionarios;
	private TipoUsuario tipoFuncionario;
	private Pessoa pessoa;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<TipoUsuario> getTipoFuncionarios() {
		return TipoFuncionarios;
	}

	public void setTipoFuncionarios(List<TipoUsuario> tipoFuncionarios) {
		TipoFuncionarios = tipoFuncionarios;
	}
	
	public TipoUsuario getTipoFuncionario() {
		return tipoFuncionario;
	}
	
	public void setTipoFuncionario(TipoUsuario tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	@PostConstruct
	public void listar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os usuários!");
			erro.printStackTrace();
		}

	}

	@Override
	public void novo() {
		showElement();
		System.out.println("showElement");
		
		try {
			usuario = new Usuario();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listarOrdenado("nome");

			TipoUsuarioDAO tipoFuncionarioDAO = new TipoUsuarioDAO();
			TipoFuncionarios = tipoFuncionarioDAO.listarOrdenado("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar gerar um usuário!");
			erro.printStackTrace();
		}
	}

	@Override
	public void salvar() {
		
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
	
			String senha = usuario.getSenha();
			String confirmaSenha = usuario.getConfirmaSenha();
			
			if(validaSenha(senha, confirmaSenha) == true) {
				SimpleHash hash = new SimpleHash("md5", senha);
				
				usuario.setSenha(hash.toHex());
				
				usuarioDAO.merge(usuario);
				novo();
				usuarios = usuarioDAO.listar();

				Messages.addGlobalInfo("Usuário salvo com sucesso!");
			}else {
			
				Messages.addGlobalError("As senhas não conferem");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o usuário!");
			erro.printStackTrace();
		}
	}

	@Override
	public void editar(ActionEvent evento) {
		hideElement();
		System.out.println("hideElement");
		
		usuario = (Usuario) evento.getComponent().getAttributes().get("selecionado");
		String senha = usuario.getSenha();
		String confirmaSenha = usuario.getSenha();
		
		System.out.println("senha: " + senha + "/nConfirmar: " + confirmaSenha);
				
		usuario.setSenha(senha);
		usuario.setConfirmaSenha(confirmaSenha);
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		Long CodigoPessoa = Long.valueOf(usuario.getPessoa().getCodigo());
		pessoa = pessoaDAO.buscar(CodigoPessoa);
		pessoas = pessoaDAO.listar();
		
		TipoUsuarioDAO tipoFuncionarioDAO = new TipoUsuarioDAO();
		TipoFuncionarios = tipoFuncionarioDAO.listar();
		
		

	}

	@Override
	public void excluir(ActionEvent evento) {
		usuario = (Usuario) evento.getComponent().getAttributes().get("selecionado");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.excluir(usuario);
		
		usuarios = usuarioDAO.listar();

	}
	

	public boolean validaSenha(String senha, String confirmaSenha) {
			if(senha.equals(confirmaSenha)) {
				return true;
			}else {
				return false;
			}
		
	}
	
	/* show and hide elements */
	private boolean Element = true;  
	  
	public boolean getElement(){  
	   return Element;  
	}  
	  
	public void showElement(){  
	    this.Element = true;  
	}  
	  
	public void hideElement(){  
	     this.Element = false;  
	}  

}

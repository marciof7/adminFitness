package br.com.msantos.adminfitness.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;

import br.com.msantos.adminfitness.dao.CidadeDAO;
import br.com.msantos.adminfitness.dao.ClienteDAO;
import br.com.msantos.adminfitness.dao.EstadoDAO;
import br.com.msantos.adminfitness.dao.PessoaDAO;
import br.com.msantos.adminfitness.domain.Cidade;
import br.com.msantos.adminfitness.domain.Cliente;
import br.com.msantos.adminfitness.domain.Estado;
import br.com.msantos.adminfitness.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements GenericBean, Serializable {
	private Cliente cliente;
	private Pessoa pessoa;
	private Estado estado;

	private List<Cliente> clientes;
	private List<Pessoa> pessoas;
	private List<Estado> estados;
	private List<Cidade> cidades;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	@PostConstruct
	public void listar() {
		try {

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os clientes!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		try {
			cliente = new Cliente();
			pessoa = new Pessoa();
			
			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listarOrdenado("nome");

			cidades = new ArrayList<Cidade>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar as cidades!");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {

			PessoaDAO pessoaDAO = new PessoaDAO();

			Date dataCadastro = new Date(System.currentTimeMillis());
			
			pessoa.setDataCadastro(dataCadastro);
			String nome = pessoa.getNome();
			
			pessoaDAO.merge(pessoa);

			List<Pessoa> listaPessoas = pessoaDAO.listar();
			
			Pessoa result = buscaPessoaPorNome(listaPessoas, nome);
		
			ClienteDAO clienteDAO = new ClienteDAO();

			pessoa = pessoaDAO.buscar(result.getCodigo());
			cliente.setPessoa(pessoa);
			clienteDAO.merge(cliente);
			
			novo();
			clientes = clienteDAO.listar();


			Messages.addGlobalInfo("Cliente salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar as cidades!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("selecionado");
			
			ClienteDAO clienteDAO = new ClienteDAO();
			cliente.setLiberado(false);
			clienteDAO.editar(cliente);
			
			Messages.addGlobalInfo("Cliente excluído com sucesso!");
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir o cliente!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("selecionado");
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			
			Long CodigoPessoa = Long.valueOf(cliente.getPessoa().getCodigo());
			pessoa = pessoaDAO.buscar(CodigoPessoa);
			estado = cliente.getPessoa().getCidade().getEstado();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());

			}catch (RuntimeException erro) {
				Messages.addGlobalError("Erro ao tentar editar o cliente!");
				erro.printStackTrace();
			}
	}

	public void popular() {
		try {
			if (estado != null) {
				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());

			} else {
				cidades = new ArrayList<Cidade>();

			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar filtrar as cidades!");
			erro.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent evento) {

	}
	
	public static Pessoa buscaPessoaPorNome(List<Pessoa> listaPessoas, String nome) {
		for (Pessoa pessoa : listaPessoas) {
			if (pessoa.getNome().equals(nome)) {
				return pessoa;
			}
		}
		return null;
	}


}

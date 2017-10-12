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

import br.com.msantos.adminfitness.dao.CidadeDAO;
import br.com.msantos.adminfitness.dao.EstadoDAO;
import br.com.msantos.adminfitness.dao.FuncionarioDAO;
import br.com.msantos.adminfitness.dao.PessoaDAO;
import br.com.msantos.adminfitness.domain.Cidade;
import br.com.msantos.adminfitness.domain.Estado;
import br.com.msantos.adminfitness.domain.Funcionario;
import br.com.msantos.adminfitness.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements GenericBean, Serializable{

	private Funcionario funcionario;
	private Pessoa pessoa;
	private Estado estado;

	private List<Funcionario> funcionarios;
	private List<Pessoa> pessoas;
	private List<Estado> estados;
	private List<Cidade> cidades;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
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

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os clientes!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		try {
			funcionario = new Funcionario();
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

			System.out.println("Pessoa:" + result.toString());

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

			pessoa = pessoaDAO.buscar(result.getCodigo());
			funcionario.setPessoa(pessoa);
			funcionarioDAO.merge(funcionario);

			novo();
			funcionarios = funcionarioDAO.listar();

			Messages.addGlobalInfo("Funcionário salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar as cidades!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("selecionado");
			
			String nome = funcionario.getPessoa().getNome();

			PessoaDAO pessoaDAO = new PessoaDAO();
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

			List<Pessoa> listaPessoas = pessoaDAO.listar();

			Pessoa result = buscaPessoaPorNome(listaPessoas, nome);

			pessoa = pessoaDAO.buscar(result.getCodigo());
			funcionarioDAO.excluir(funcionario);
			pessoaDAO.excluir(pessoa);
			
			funcionarios = funcionarioDAO.listar();

			Messages.addGlobalInfo("Funcionário excluído com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir o funcionário!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("selecionado");

			PessoaDAO pessoaDAO = new PessoaDAO();

			Long CodigoPessoa = Long.valueOf(funcionario.getPessoa().getCodigo());
			pessoa = pessoaDAO.buscar(CodigoPessoa);
			estado = funcionario.getPessoa().getCidade().getEstado();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());

			Messages.addGlobalInfo("Funcionário editado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o funcionário	!");
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

	public static Pessoa buscaPessoaPorNome(List<Pessoa> listaPessoas, String nome) {
		for (Pessoa pessoa : listaPessoas) {
			if (pessoa.getNome().equals(nome)) {
				return pessoa;
			}
		}
		return null;
	}
}

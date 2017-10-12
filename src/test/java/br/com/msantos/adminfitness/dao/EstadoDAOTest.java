package br.com.msantos.adminfitness.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.msantos.adminfitness.dao.EstadoDAO;
import br.com.msantos.adminfitness.domain.Estado;

public class EstadoDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Estado estado = new Estado();
		estado.setSigla("SP");
		estado.setNome("São Paulo");

		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);

	}

	@Test
	@Ignore
	public void listar() {
		EstadoDAO estadoDao = new EstadoDAO();
		List<Estado> resultado = estadoDao.listar();

		System.out.println("Registros: " + resultado.size());

		for (Estado estado : resultado) {
			System.out.println(estado.getSigla() + " - " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 1L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {
			System.out.println("Nenhum Registro Encontrado!");
		} else {
			System.out.println("Registro Encontrado!");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}

	}

	@Test
	@Ignore
	public void excluir() {
		Long codigo = 1L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if (estado == null) {
			System.out.println("Nenhum Registro Encontrado!");
		} else {
			estadoDAO.excluir(estado);
			System.out.println("Registro removido!");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
		
		
		
	}
	
	@Test
	@Ignore
	public void editar() {
		Long codigo = 1L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if (estado == null) {
			System.out.println("Nenhum Registro Encontrado!");
		} else {
			System.out.println("Registro Atual!");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
			
			estado.setSigla("PE");
			estado.setNome("Pernambuco");
			estadoDAO.editar(estado);
			
			
			System.out.println("Registro Alterado!");
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
		
		
		
	}

}

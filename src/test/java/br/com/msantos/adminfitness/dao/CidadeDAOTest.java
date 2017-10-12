package br.com.msantos.adminfitness.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.msantos.adminfitness.domain.Cidade;
import br.com.msantos.adminfitness.domain.Estado;

public class CidadeDAOTest {
	
	@Test
	@Ignore
	public void salvar(){
		
		EstadoDAO estadoDAO = new EstadoDAO();
		
		Estado estado = estadoDAO.buscar(1L);
		
		Cidade cidade = new Cidade();
		cidade.setNome("Recife");
		cidade.setEstado(estado);
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		cidadeDAO.salvar(cidade);																													
	}
	
	@Test
	@Ignore
	public void listar(){
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> resultado  = cidadeDAO.listar();
		
		for(Cidade cidade : resultado){
			System.out.println("Código: " + cidade.getCodigo());
			System.out.println("Nome: " + cidade.getNome());
			System.out.println("UF: " + cidade.getEstado().getSigla());
			System.out.println("Estado: " + cidade.getEstado().getNome());
			System.out.println();
		}
	}
	
	@Test
	@Ignore
	public void buscar(){
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(1L);
		
		System.out.println("Código: " + cidade.getCodigo());
		System.out.println("Nome: " + cidade.getNome());
		System.out.println("UF: " + cidade.getEstado().getSigla());
		System.out.println("Estado: " + cidade.getEstado().getNome());
		
		
	}
	
	@Test
	@Ignore
	public void excluir(){
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(1L);
		
		cidadeDAO.excluir(cidade);
		
	}
	
	@Test
	@Ignore
	public void editar(){
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = cidadeDAO.buscar(1L);
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(cidade.getEstado().getCodigo());
		
		estado.setSigla("BA");
		estado.setNome("Bahia");
		
		cidade.setNome("Paulista");
		cidade.setEstado(estado);
		
		cidadeDAO.editar(cidade);
		
	}

}

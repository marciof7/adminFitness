package br.com.msantos.adminfitness.bean;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

public interface GenericBean {
	
	@PostConstruct
	public void listar();
	
	public void novo();
	
	public void  salvar();
	
	public void editar(ActionEvent evento);
	
	public void excluir(ActionEvent evento);

}

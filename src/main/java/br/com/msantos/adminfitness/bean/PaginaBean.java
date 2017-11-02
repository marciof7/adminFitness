package br.com.msantos.adminfitness.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PaginaBean implements Serializable{
	
	private String pagina;
	
	public String getPagina() {
		return pagina;
	}
	
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	public void clientes(){
		setPagina("pages/clintes.xhtml");
	}

}

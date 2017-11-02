package br.com.msantos.adminfitness.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.msantos.adminfitness.domain.ItemPermissoes;
import br.com.msantos.adminfitness.domain.Permissoes;
import br.com.msantos.adminfitness.util.HibernateUtil;

public class PermissoesDAO extends GenericDAO<Permissoes>{
	
	public void salvar(Permissoes permissoes, List<ItemPermissoes> itensPermissoes){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		
		try{
			
			transacao = sessao.beginTransaction();
			
			sessao.save(permissoes);
			
			for(int posicao = 0; posicao < itensPermissoes.size(); posicao ++){
				ItemPermissoes itens = itensPermissoes.get(posicao);
				itens.setPermissoes(permissoes);
				
				sessao.save(itensPermissoes);
			}
			
			transacao.commit();
			
		}catch (RuntimeException erro) {
			if(transacao != null ){
				transacao.rollback();
			}
			throw erro;
		}finally {
			sessao.close();
		}
		
	}

}

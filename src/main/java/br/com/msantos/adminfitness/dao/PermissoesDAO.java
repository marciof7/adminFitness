package br.com.msantos.adminfitness.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.msantos.adminfitness.domain.ItemPermissoes;
import br.com.msantos.adminfitness.domain.Permissoes;
import br.com.msantos.adminfitness.util.HibernateUtil;

public class PermissoesDAO extends GenericDAO<Permissoes> {

	public void salvar(Permissoes permissoes, List<ItemPermissoes> itensPermissoes) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {

			transacao = sessao.beginTransaction();

			sessao.save(permissoes);
			sessao.clear();
			sessao.flush();

			for (ItemPermissoes item : itensPermissoes) {
				item.setPermissoes(permissoes);
				System.out.println("teste");
				sessao.save((ItemPermissoes) item);
				sessao.clear();
				sessao.flush();

			}

			transacao.commit();

		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}

	}

}

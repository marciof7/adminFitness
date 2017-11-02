package br.com.msantos.adminfitness.util;

import java.io.IOException;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.msantos.adminfitness.bean.AutenticacaoBean;
import br.com.msantos.adminfitness.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent evento) {
		try {

			String paginaAtual = Faces.getViewId();

			boolean ehPaginaDeAutenticacao = paginaAtual.contains("login.xhtml");

			if (!ehPaginaDeAutenticacao) {
				AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

				if (autenticacaoBean == null) {
					Faces.redirect("/Adminfitness/pages/login.xhtml");
					return;
				}

				Usuario usuario = autenticacaoBean.getUsuarioLogado();

				if (usuario == null) {
					Faces.redirect("/Adminfitness/pages/login.xhtml");
					return;
				}
			}

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}

	}

	@Override
	public void beforePhase(PhaseEvent evento) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}

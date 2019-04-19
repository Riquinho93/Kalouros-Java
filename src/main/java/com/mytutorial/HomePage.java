package com.mytutorial;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import com.mytutorial.model.Conta;
import com.mytutorial.model.Usuario;
import com.mytutorial.view.ServiceForm;
import com.mytutorial.view.ContaForm;
import com.mytutorial.view.ContatoForm;
import com.mytutorial.view.FuncionarioForm;
import com.mytutorial.view.Login;
import com.mytutorial.view.TelaPrincipal;
import com.mytutorial.view.UsuarioForm;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage() {
		Usuario userName = (Usuario) getSession().getAttribute("userName");
		if (userName == null) {
			setResponsePage(Login.class);
			return;
		}
		
		add(telaPrincipal());
		add(bancoForm());
		add(contaForm());
		add(contatoForm(userName));
		add(new Link<Void>("sair") {

			private static final long serialVersionUID = 1L;

			public void onClick() {
				getSession().invalidate();
				setResponsePage(TelaPrincipal.class);
			}
		});

	}

	/*
	 * // Metodo de chamada de Criar Conta AjaxLink<FuncionarioForm> usuarioForm() {
	 * AjaxLink<FuncionarioForm> ajaxLink = new
	 * AjaxLink<FuncionarioForm>("usuarioForm") {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @Override public void onClick(AjaxRequestTarget target) {
	 * setResponsePage(UsuarioForm.class); } }; ajaxLink.setOutputMarkupId(true);
	 * add(ajaxLink); return ajaxLink; }
	 */
	private AjaxLink<TelaPrincipal> telaPrincipal() {
		AjaxLink<TelaPrincipal> ajaxLink = new AjaxLink<TelaPrincipal>("telaPrincipal") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(TelaPrincipal.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	private AjaxLink<ServiceForm> bancoForm() {
		AjaxLink<ServiceForm> ajaxLink = new AjaxLink<ServiceForm>("bancoForm") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ServiceForm.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	private AjaxLink<ContaForm> contaForm() {
		AjaxLink<ContaForm> ajaxLink = new AjaxLink<ContaForm>("contaForm") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ContaForm.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	/*
	 * private AjaxLink<FuncionarioForm> clienteForm() { AjaxLink<FuncionarioForm>
	 * ajaxLink = new AjaxLink<FuncionarioForm>("clienteForm") {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @Override public void onClick(AjaxRequestTarget target) {
	 * setResponsePage(FuncionarioForm.class); } };
	 * ajaxLink.setOutputMarkupId(true); add(ajaxLink); return ajaxLink; }
	 */
	
	
	private AjaxLink<ContatoForm> contatoForm(Usuario usuario) {
		AjaxLink<ContatoForm> ajaxLink = new AjaxLink<ContatoForm>("contatoForm") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new ContatoForm(usuario));
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}
}

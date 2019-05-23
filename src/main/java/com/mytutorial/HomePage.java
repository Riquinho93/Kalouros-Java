package com.mytutorial;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import com.mytutorial.model.Usuario;
import com.mytutorial.view.CategoriaForm;
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
		add(categoriaForm());
		add(usuarioForm());

		add(new Link<Void>("sair") {

			private static final long serialVersionUID = 1L;

			public void onClick() {
				getSession().invalidate();
				setResponsePage(TelaPrincipal.class);
			}
		});

	}

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

	protected AjaxLink<CategoriaForm> categoriaForm() {
		AjaxLink<CategoriaForm> ajaxLink = new AjaxLink<CategoriaForm>("categoriaForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CategoriaForm.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	protected AjaxLink<UsuarioForm> usuarioForm() {
		AjaxLink<UsuarioForm> ajaxLink = new AjaxLink<UsuarioForm>("usuarioForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(UsuarioForm.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

}

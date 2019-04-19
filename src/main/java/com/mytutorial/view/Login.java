package com.mytutorial.view;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.AlertFeedback;
import com.mytutorial.service.UsuarioService;

public class Login extends WebPage {

	private static final long serialVersionUID = -2850628051987758424L;

	private Form<Usuario> formularioLogin;
	private Usuario filtrarUsuario;
	@SpringBean(name = "usuarioService")
	private UsuarioService usuarioService;

	public Login() {

		AlertFeedback alertFeedback = new AlertFeedback("feedbackMessage");

		add(aberturaConta());

		filtrarUsuario = new Usuario();
		final TextField<String> email = new TextField<String>("email");
		final PasswordTextField senha = new PasswordTextField("senha");
		email.setRequired(true);
		senha.setRequired(true);
		email.setOutputMarkupId(true);
		senha.setOutputMarkupId(true);
//
//		final Label errorLogin = new Label("errorLogin", Model.of("Login Incorreto!!"));
//		errorLogin.setOutputMarkupId(true).setVisible(false);

		formularioLogin = new Form<Usuario>("formularioLogin", new CompoundPropertyModel<>(filtrarUsuario)) {

			private static final long serialVersionUID = -5095534494215850537L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
				Search search = new Search(Usuario.class);

				search.addFilterEqual("email", email.getModelObject());
				search.addFilterEqual("senha", senha.getModelObject());

				List<Usuario> lista = usuarioService.search(search);

				if (lista != null && !lista.isEmpty()) {
					Usuario usuario = lista.get(0);
					getSession().setAttribute("userName", lista.get(0));
					setResponsePage(new TelaPrincipal(usuario));
				} else {

					alertFeedback.error("Login Incorreto");
//					errorLogin.setVisible(true);
				}

			}

		};
		add(alertFeedback);
		add(formularioLogin);
		formularioLogin.add(email, senha).setOutputMarkupId(true);
	}

	private AjaxLink<Register> aberturaConta() {
		AjaxLink<Register> ajaxLink = new AjaxLink<Register>("aberturaConta") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Register.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}
}

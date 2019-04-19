package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mytutorial.model.Perfil;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.UsuarioService;

public class Register extends WebPage {

	private static final long serialVersionUID = 1L;

	private Form<Usuario> form;

	@SpringBean(name = "usuarioService")
	private UsuarioService usuarioService;

	public Register() {

		Usuario usuario = new Usuario();
		form = new Form<Usuario>("form", new CompoundPropertyModel<Usuario>(usuario));
		TextField<String> nome = new TextField<>("nome");
		TextField<String> email = new TextField<String>("email");
		PasswordTextField senha = new PasswordTextField("senha");
		PasswordTextField confirmarSenha = new PasswordTextField("confirmarSenha");
		
		
		nome.setOutputMarkupId(true);
		email.setOutputMarkupId(true);
		senha.setOutputMarkupId(true);
		confirmarSenha.setOutputMarkupId(true);
		nome.setRequired(true);
		email.setRequired(true);
		senha.setRequired(true);
		confirmarSenha.setRequired(true);

		form.add(nome, email, senha, confirmarSenha);

		
		AjaxButton button = new AjaxButton("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				Usuario user = (Usuario) form.getModelObject();
				user.setPerfil(Perfil.USUARIO);
				usuarioService.SalvarOuAlterar(user);
				target.add(nome, email, senha, confirmarSenha);

				setResponsePage(Login.class);
			}

		};

		button.setOutputMarkupId(true);
		form.add(button);
		add(form);
		voltar();
	}

	private void voltar() {
		AjaxLink<Login> ajaxLink = new AjaxLink<Login>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Login.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}

}

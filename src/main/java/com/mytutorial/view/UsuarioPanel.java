package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.mytutorial.model.Usuario;

public class UsuarioPanel extends Panel {

	private static final long serialVersionUID = 8991195474675368668L;

	private Form<Usuario> form;

	public UsuarioPanel(String id) {
		this(id, new Usuario());
	}

	public UsuarioPanel(String id, Usuario usuario) {
		super(id);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

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

			private static final long serialVersionUID = 994698440577863113L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				executarAoSalvar(target, usuario);
				target.add(nome, email, senha, confirmarSenha);

				target.add(feedbackPanel);

			}
		};
		add(feedbackPanel);
		button.setOutputMarkupId(true);
		form.add(nome);
		form.add(button);
		add(form);
		voltar();

	}

	// Enviando os dados para o HomePage
	public void executarAoSalvar(AjaxRequestTarget target, Usuario usuario) {

	}

	private void voltar() {
		AjaxLink<Login> ajaxLink = new AjaxLink<Login>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(UsuarioForm.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}

}

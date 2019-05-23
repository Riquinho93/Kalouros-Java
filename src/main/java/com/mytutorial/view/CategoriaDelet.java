package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.mytutorial.model.Categoria;

public class CategoriaDelet extends Panel {

	private static final long serialVersionUID = 1L;

	private Categoria user = new Categoria();

	public CategoriaDelet(String id, Categoria answer) {
		super(id);

		this.user = answer;

		Form<Categoria> form = new Form<>("resposta");

		// Confirmando a operação
		AjaxButton yesButton = new AjaxButton("sim") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					user.setAnswer(true);
					executarAoSalvar(target, user);

				}
			}
		};
		// Resposta de cancelar operação
		AjaxButton noButton = new AjaxButton("nao") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					user.setAnswer(false);
					executarAoSalvar(target, user);

				}
			}
		};
		add(form);
		yesButton.setOutputMarkupId(true);
		noButton.setOutputMarkupId(true);

		form.add(yesButton);
		form.add(noButton);

	}

	// Enviando os dados para o HomePage
	public void executarAoSalvar(AjaxRequestTarget target, Categoria categoriaModel) {

	}
}

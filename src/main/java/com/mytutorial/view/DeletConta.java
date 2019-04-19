package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.mytutorial.model.Conta;

public class DeletConta extends Panel {

	private static final long serialVersionUID = 1L;
	private Conta user = new Conta();

	public DeletConta(String id, final Conta answer) {
		super(id);
		this.user = answer;
		Form<Conta> form = new Form<>("resposta");

		add(new Label("msg", "Deseja realmente excluir esta Conta?"));

		// Se a resposta == sim
		AjaxButton yesButton = new AjaxButton("sim") {

			private static final long serialVersionUID = 963978570032062983L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					user.setAnswer(true);
					executarAoExcluir(target, user);
				}
			}

		};

		// Se resposta == nao
		AjaxButton naoButton = new AjaxButton("nao") {

			private static final long serialVersionUID = -4614172469024292429L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					user.setAnswer(false);
					executarAoExcluir(target, user);
				}

			}
		};
		add(form);
		yesButton.setOutputMarkupId(true);
		naoButton.setOutputMarkupId(true);

		form.add(yesButton);
		form.add(naoButton);

	}

	public void executarAoExcluir(AjaxRequestTarget target, Conta conta) {

	}

}

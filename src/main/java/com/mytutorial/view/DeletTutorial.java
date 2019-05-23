package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.mytutorial.model.Tutorial;
import com.mytutorial.model.Usuario;

public class DeletTutorial extends Panel {

	private static final long serialVersionUID = 1L;
	private Tutorial tutorial = new Tutorial();

	public DeletTutorial(String id, Tutorial answer) {
		super(id);
		this.tutorial = answer;

		Form<Usuario> form = new Form<>("resposta");

		add(new Label("msg", "Do you really want to delete this tutorial?"));

		// Se a resposta == sim
		AjaxButton yesButton = new AjaxButton("sim") {

			private static final long serialVersionUID = 963978570032062983L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					tutorial.setAnswer(true);
					executarAoExcluir(target, tutorial);
				}
			}

		};

		// Se resposta == nao
		AjaxButton naoButton = new AjaxButton("nao") {

			private static final long serialVersionUID = -4614172469024292429L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					tutorial.setAnswer(false);
					executarAoExcluir(target, tutorial);
				}

			}
		};
		add(form);
		yesButton.setOutputMarkupId(true);
		naoButton.setOutputMarkupId(true);

		form.add(yesButton);
		form.add(naoButton);

	}

	public void executarAoExcluir(AjaxRequestTarget target, Tutorial tutorial) {

	}

}

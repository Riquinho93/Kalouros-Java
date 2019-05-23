package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.mytutorial.model.Categoria;

public class CategoriaPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public CategoriaPanel(String id) {
		this(id, new Categoria());
	}

	public CategoriaPanel(String id, Categoria categoriaModel) {
		super(id);

		Form<Categoria> form = new Form<Categoria>("form", new CompoundPropertyModel<Categoria>(categoriaModel));

		final TextField<String> nome = new TextField<String>("nome");

		nome.setRequired(true);
		nome.setOutputMarkupId(true);

		// Criando botão de enviar
		// Botão Ajax
		AjaxButton ajaxButton = new AjaxButton("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				executarAoSalvar(target, categoriaModel);
				target.add(nome);
			}
		};
		ajaxButton.setOutputMarkupId(true);
		add(form);
		form.add(nome);
		form.add(ajaxButton);
	}

	// Enviando os dados para o HomePage
	public void executarAoSalvar(AjaxRequestTarget target, Categoria categoria) {

	}

}

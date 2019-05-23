package com.mytutorial.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mytutorial.model.Categoria;
import com.mytutorial.model.Tutorial;
import com.mytutorial.service.CategoriaService;
import com.mytutorial.service.TutorialService;

public class TutorialPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private List<Categoria> listaCategorias = new ArrayList<>();
	private List<Tutorial> listaTutoriais = new ArrayList<>();
	private Categoria categoria;
	private Form<Tutorial> form;

	@SpringBean(name = "categoriaService")
	private CategoriaService categoriaService;

	@SpringBean(name = "tutorialService")
	private TutorialService tutorialService;

	public TutorialPanel(String id) {
		this(id, new Tutorial());
	}

	public TutorialPanel(String id, Tutorial tutorial) {
		super(id);

		listaCategorias = categoriaService.listar();

		form = new Form<Tutorial>("form", new CompoundPropertyModel<Tutorial>(tutorial));

		TextField<Tutorial> title = new TextField<>("title");
//		TextArea<Tutorial> corpo = new TextArea<>("corpo");

		title.setOutputMarkupId(true);
//		corpo.setOutputMarkupId(true);

		categoria = new Categoria();

		DropDownChoice<Categoria> categorias = new DropDownChoice<Categoria>("categoria",
				new PropertyModel<Categoria>(tutorial, "categoria"), listaCategorias,
				new ChoiceRenderer<Categoria>("nome"));

		AjaxButton ajaxButton = new AjaxButton("salvar") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				executarAoSalvar(target, tutorial);
				listaTutoriais.add(tutorial);
				form.clearInput();
				form.modelChanged();
				form.setDefaultModelObject(tutorial);
				target.add(form, title);
			}
		};

		ajaxButton.setOutputMarkupId(true);
		form.add(ajaxButton, title, categorias);
		add(form);
		cancelar();
	}

	public void executarAoSalvar(AjaxRequestTarget target, Tutorial tutorial) {

	}

	private void cancelar() {
		AjaxLink<TelaPrincipal> ajaxLink = new AjaxLink<TelaPrincipal>("cancelar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(TelaPrincipal.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}
}

package com.mytutorial.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.StringValidator;

import com.mytutorial.HomePage;
import com.mytutorial.model.Categoria;
import com.mytutorial.model.Comentario;
import com.mytutorial.model.Tutorial;
import com.mytutorial.service.CategoriaService;
import com.mytutorial.service.TutorialService;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.ajax.TinyMceAjaxButton;
import wicket.contrib.tinymce.ajax.TinyMceAjaxSubmitModifier;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;

public class TutorialForm extends HomePage {

	private static final long serialVersionUID = 1L;

	private List<Categoria> listaCategorias = new ArrayList<>();
	private List<Tutorial> listaTutoriais = new ArrayList<>();
	private Categoria categoria;
	private Form<Tutorial> form;

	@SpringBean(name = "categoriaService")
	private CategoriaService categoriaService;

	@SpringBean(name = "tutorialService")
	private TutorialService tutorialService;

	public TutorialForm() {
		this(new Tutorial());
	}

	public TutorialForm(Tutorial tutorial) {

		listaCategorias = categoriaService.listar();

		form = new Form<Tutorial>("form", new CompoundPropertyModel<Tutorial>(tutorial));

		TextField<Tutorial> title = new TextField<>("title");
		TextArea<Tutorial> editor = new TextArea<>("editor");
		// tinymce

//		editor.add(new TinyMceBehavior(new TinyMCESettings(Theme.advanced)));
		
		 // Add one file input field
        FileUploadField image = new FileUploadField("image");

		editor.add(StringValidator.maximumLength(4000));

		title.setOutputMarkupId(true);
		editor.setOutputMarkupId(true);

		categoria = new Categoria();

		DropDownChoice<Categoria> categorias = new DropDownChoice<Categoria>("categoria",
				new PropertyModel<Categoria>(tutorial, "categoria"), listaCategorias,
				new ChoiceRenderer<Categoria>("nome"));
		
		form.add(title, editor, categorias, image);
		form.add(new TinyMceAjaxButton("salvar")
		{
          
			private static final long serialVersionUID = 1L;

			@Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            	super.onSubmit(target, form);
            	listaTutoriais.add(tutorial); 
            	tutorialService.SalvarOuAlterar(tutorial);
				target.add(editor, title, image);
				setResponsePage(TelaPrincipal.class);
            }
		});

		/*
		 * AjaxButton ajaxButton = new AjaxButton("salvar") {
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		 * super.onSubmit(target, form);
		 * 
		 * listaTutoriais.add(tutorial); tutorialService.SalvarOuAlterar(tutorial);
		 * form.clearInput(); form.modelChanged(); form.setDefaultModelObject(tutorial);
		 * setResponsePage(TelaPrincipal.class); target.add(form, title, editor); } };
		 * // ajaxButton.add(new TinyMceAjaxSubmitModifier());
		 * ajaxButton.setOutputMarkupId(true); form.add(ajaxButton, title, categorias,
		 * editor);
		 */
		add(form);
		cancelar();

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

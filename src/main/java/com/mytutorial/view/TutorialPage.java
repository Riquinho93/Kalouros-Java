package com.mytutorial.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.value.ValueMap;

import com.mytutorial.HomePage;
import com.mytutorial.model.Comentario;
import com.mytutorial.model.Tutorial;
import com.mytutorial.service.CategoriaService;
import com.mytutorial.service.ComentarioService;
import com.mytutorial.service.TutorialService;

public class TutorialPage extends HomePage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean(name = "comentarioService")
	private ComentarioService comentarioService;

	@SpringBean(name = "tutorialService")
	private TutorialService tutorialService;
	
	private Tutorial idTutorial = new Tutorial();


	private static final List<Comentario> comentarioLista = Collections.synchronizedList(new ArrayList<Comentario>());

	public TutorialPage(PageParameters parameters) {
		
		idTutorial = tutorialService.buscarPorId(parameters.get("id").toInteger());  
		
		//VOU TER QUE BUSCAR POR ID PARA FAZER A ALTERAÇÃO
		
		add(new Label("title", parameters.get("title")));
		add(new Label("editor", parameters.get("editor")));

		// Add comentario no formulario
		add(new CommentarioForm("ComentarioForm"));
		// Add os comentarios que já existe
		add(new PropertyListView<Comentario>("comentarios", comentarioLista) {

			@Override
			public void populateItem(final ListItem<Comentario> listItem) {
				listItem.add(new Label("data"));
				listItem.add(new MultiLineLabel("text"));
			}
		}).setVersioned(false);

	}

	// Formulario do comentario
	public final class CommentarioForm extends Form<ValueMap> {
		public CommentarioForm(final String id) {
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
			// Fazendo unit feliz
			setMarkupId("CommentarioForm");
			// add os dados
			add(new TextArea<String>("text").setType(String.class));
			/*
			 * // Add automaticamento no comentario add(new
			 * TextField<String>("comentario").setType(String.class));
			 */
		}

		@Override
		public final void onSubmit() {
			ValueMap values = getModelObject();
			// Verificando se está preenchido
			values.get("comentario");
			
			// Construido uma copia do comentario
			Comentario comment = new Comentario();
			// colocando uma data no comentario
			comment.setData(new Date());
			comment.setText((String) values.get("text"));
						
			comentarioLista.add(0, comment);
			comment.setTutorial(idTutorial);
			System.out.println("Id: " + comment.getTutorial().getId());
			System.out.println("Comentario: " + comment.getText());
			comentarioService.SalvarOuAlterar(comment);
			idTutorial.setListaComentarios(comentarioLista);
			tutorialService.SalvarOuAlterar(idTutorial);
			
			// limpar o componente
			values.put("text", "");
		}

	}

	// Limpando os comentario
	public static void clear() {
		comentarioLista.clear();
	}

}

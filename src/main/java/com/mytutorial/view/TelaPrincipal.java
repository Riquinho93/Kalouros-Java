package com.mytutorial.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.HomePage;
import com.mytutorial.model.Categoria;
import com.mytutorial.model.Tutorial;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.CategoriaService;
import com.mytutorial.service.TutorialService;
import com.mytutorial.service.UsuarioService;

public class TelaPrincipal extends HomePage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "usuarioService")
	private UsuarioService usuarioService;

	@SpringBean(name = "tutorialService")
	private TutorialService tutorialService;

	@SpringBean(name = "categoriaService")
	private CategoriaService categoriaService;

	private Categoria categoria;

	private List<Tutorial> listaTutoriais = new LinkedList<>();
	private List<Categoria> listaCategorias = new ArrayList<>();

	private Form<?> form = new Form<>("form");
	private Form<Tutorial> form2;
	private ModalWindow modalWindowDel;

	private Tutorial tutorialFiltrar;
	private Tutorial tutorial;

	private WebMarkupContainer listContainer;

	private LoadableDetachableModel<List<Tutorial>> atualizarLista;

	private PageableListView<Tutorial> listView;

	public TelaPrincipal() {
		this(new Usuario());
	}

	public TelaPrincipal(Usuario usuario) {

		listaTutoriais = tutorialService.listar();
		listaCategorias = categoriaService.listar();

		// Modal Window do delete
		modalWindowDel = new ModalWindow("modalWindowDel");
		// Tamanho
		modalWindowDel.setInitialHeight(250);
		modalWindowDel.setInitialWidth(350);
		modalWindowDel.setOutputMarkupId(true);
		add(modalWindowDel);
//		Tutorial t = tutorialService.buscarPorId(0);
//		System.out.println(t.getTitle());

		add(container());

		add(tutorialForm());
		add(filtrar());

	}

	protected AjaxLink<TutorialForm> tutorialForm() {
		AjaxLink<TutorialForm> ajaxLink = new AjaxLink<TutorialForm>("tutorialForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(TutorialForm.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	private WebMarkupContainer container() {

		listContainer = new WebMarkupContainer("theContainer");
		listContainer.setOutputMarkupId(true);

		atualizarLista = new LoadableDetachableModel<List<Tutorial>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Tutorial> load() {
				return listaTutoriais;
			}
		};

		listView = new PageableListView<Tutorial>("listView", atualizarLista, 10) {

			private static final long serialVersionUID = -8503564664744203394L;

			@Override
			protected void populateItem(ListItem<Tutorial> item) {
				Tutorial tutorial = item.getModelObject();
				item.add(new Label("title", tutorial.getTitle()));
				item.add(editando(tutorial));
				item.add(remover(tutorial.getId()));
				item.add(visualizar(item.getIndex(), tutorial));
			}
		};
		add(listView);
		listView.setOutputMarkupId(true);
		listContainer.add(listView);

		add(new PagingNavigator("pag", listView));
		return listContainer;

	}

	public Form<Tutorial> filtrar() {
		tutorialFiltrar = new Tutorial();
		form2 = new Form<Tutorial>("form2", new CompoundPropertyModel<Tutorial>(tutorialFiltrar));
		TextField<String> title = new TextField<String>("title");
		title.setOutputMarkupId(true);
		categoria = new Categoria();

		DropDownChoice<Categoria> categoria = new DropDownChoice<Categoria>("categoria",
				new PropertyModel<Categoria>(tutorial, "categoria"), listaCategorias,
				new ChoiceRenderer<Categoria>("nome"));

		form2.add(title, categoria);

		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(Tutorial.class);

				if (tutorialFiltrar.getTitle() != null && !tutorialFiltrar.getTitle().equals("")) {
					search.addFilterLike("title", "%" + tutorialFiltrar.getTitle() + "%");
				}

				/*
				 * if (tutorialFiltrar.getCategoria().getNome() != null &&
				 * !tutorialFiltrar.getCategoria().getNome().equals("")) {
				 * search.addFilterLike("categoria", "%" +
				 * tutorialFiltrar.getCategoria().getNome() + "%"); }
				 */

				listaTutoriais = tutorialService.search(search);
				target.add(listContainer);
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}

	// Enviando para Pagina TutorialPage
	AjaxLink<TutorialPage> visualizar(final int index, final Tutorial tutorial) {
		AjaxLink<TutorialPage> button1 = new AjaxLink<TutorialPage>("vis") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				PageParameters parameters = new PageParameters();
				parameters.add("id", tutorial.getId());
				parameters.add("title", tutorial.getTitle());
				parameters.add("editor", tutorial.getEditor());
				setResponsePage(TutorialPage.class, parameters);

			}

		};

		button1.setOutputMarkupId(true);
		form.add(button1);
		return button1;
	}

	// Removendo
	public Component remover(final Integer index) {

		AjaxLink<Tutorial> button = new AjaxLink<Tutorial>("excluir") {
			Tutorial answer = new Tutorial();

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DeletTutorial deletTutorial = new DeletTutorial(modalWindowDel.getContentId(), answer) {

					private static final long serialVersionUID = 1L;

					public void executarAoExcluir(AjaxRequestTarget target, Tutorial tutorial) {
						if (tutorial.isAnswer() == true) {
							// enderecoService.excluir(index);
							tutorialService.excluir(index);
							listaTutoriais = tutorialService.listar();
							target.appendJavaScript("sucessDelet();");
							target.add(listContainer);
						}
						modalWindowDel.close(target);
					};
				};
				deletTutorial.setOutputMarkupId(true);
				modalWindowDel.setContent(deletTutorial);
				modalWindowDel.show(target);
			}
		};
		button.setOutputMarkupId(true);
		return button;
	}

	// Editando
	AjaxLink<Tutorial> editando(Tutorial tutorial) {
		AjaxLink<Tutorial> editar = new AjaxLink<Tutorial>("alterar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new TutorialForm(tutorial));
			}
		};
		editar.setOutputMarkupId(true);
		return editar;
	}

}

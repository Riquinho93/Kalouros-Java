package com.mytutorial.view;

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.HomePage;
import com.mytutorial.model.Categoria;
import com.mytutorial.service.CategoriaService;

public class CategoriaForm extends HomePage {

	private static final long serialVersionUID = 1L;

	private Form<?> form = new Form<>("form");
	private Form<Categoria> form2;
	private ModalWindow modalCategoria;
	private ModalWindow modalWindowDel;
	private PageableListView<Categoria> listView = null;
	private List<Categoria> listaCategorias = new LinkedList<Categoria>();
	// Criando um container
	private WebMarkupContainer listContainer = null;
	private LoadableDetachableModel<List<Categoria>> loadList;
	private Categoria categoriaForm;

	@SpringBean(name = "categoriaService")
	private CategoriaService categoriaService;

	public CategoriaForm() {

		listaCategorias = categoriaService.listar();

		// Metodo do container
		add(divConteiner());
		add(filtrar());

		// Modal Window do delete
		modalWindowDel = new ModalWindow("modalWindowDel");
		// Tamanho
		modalWindowDel.setInitialHeight(250);
		modalWindowDel.setInitialWidth(350);
		modalWindowDel.setOutputMarkupId(true);
		add(modalWindowDel);

		modalCategoria = new ModalWindow("modalCategoria");
		// Tamanho do Modal de categoriaPanel
		modalCategoria.setInitialHeight(300);
		modalCategoria.setInitialWidth(400);
		modalCategoria.setOutputMarkupId(true);
		add(modalCategoria);

		// Criando o botão para o modal de Categoria
		add(new AjaxLink<String>("abrirModalCategoria") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CategoriaPanel categoriaPanel = new CategoriaPanel(modalCategoria.getContentId()) {
					private static final long serialVersionUID = 1L;

					public void executarAoSalvar(AjaxRequestTarget target, Categoria categoria) {
						categoriaService.SalvarOuAlterar(categoria);
						listaCategorias.add(categoria);
						listaCategorias = categoriaService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalCategoria.close(target);
					}
				};
				categoriaPanel.setOutputMarkupId(true);
				add(categoriaPanel);
				modalCategoria.setContent(categoriaPanel);
				modalCategoria.show(target);
			}

		});

	}

	// ListView
	private WebMarkupContainer divConteiner() {
		listContainer = new WebMarkupContainer("theContainer");
		listContainer.setOutputMarkupId(true);
		loadList = new LoadableDetachableModel<List<Categoria>>() {

			private static final long serialVersionUID = 1L;

			protected List<Categoria> load() {
				return listaCategorias;
			}
		};
		// Criando a lista View
		listView = new PageableListView<Categoria>("listview", loadList, 5) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Categoria> item) {
				Categoria user = item.getModelObject();

				// item.add(new Label("ID", user.getId()));
				item.add(new Label("nome", user.getNome()));
				item.add(editando(user));
				item.add(removendo(user.getId()));
				// item.add(filtrar());

			}

		};
		add(listView);
		listView.setOutputMarkupId(true);
		// Encapsular a ListView aqui WebMarkupContainer

		// listContainer.add(new
		// AjaxSelfUpdatingTimerBehavior(Duration.seconds(3)));
		// Aparecer no container
//			listContainer.setOutputMarkupId(true);
		listContainer.add(listView);
		add(new PagingNavigator("pag", listView));

		return listContainer;
	}

	// Editando os campos
	AjaxLink<Categoria> editando(final Categoria categoriaModel) {
		AjaxLink<Categoria> button1 = new AjaxLink<Categoria>("edit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CategoriaPanel categoriaPanel = new CategoriaPanel(modalCategoria.getContentId(), categoriaModel) {

					private static final long serialVersionUID = 1L;

					public void executarAoSalvar(AjaxRequestTarget target, Categoria categoriaModel) {

						target.add(listContainer);
						categoriaService.SalvarOuAlterar(categoriaModel);
						modalCategoria.close(target);
					};
				};

				categoriaPanel.setOutputMarkupId(true);
				modalCategoria.setContent(categoriaPanel);
				modalCategoria.show(target);

			}
		};

		button1.setOutputMarkupId(true);
		form.add(button1);
		return button1;
	}

	// Removendo modelo com ajaxLink
	protected AjaxLink<Categoria> removendo(final Integer index) {

		AjaxLink<Categoria> button1 = new AjaxLink<Categoria>("delet") {
			Categoria answer = new Categoria();

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CategoriaDelet deletColecao = new CategoriaDelet(modalWindowDel.getContentId(), answer) {

					private static final long serialVersionUID = 1L;

					public void executarAoSalvar(AjaxRequestTarget target, Categoria categoriaModel) {
						if (categoriaModel.isAnswer() == true) {
							// colecaoModels.remove(index);
							categoriaService.excluir(index);
							listaCategorias = categoriaService.listar();
							target.appendJavaScript("sucessDelet();");
							target.add(listContainer);
						}
						modalWindowDel.close(target);
					};
				};
				deletColecao.setOutputMarkupId(true);
				modalWindowDel.setContent(deletColecao);
				modalWindowDel.show(target);
			}
		};
		button1.setOutputMarkupId(true);
		form.add(button1);
		return button1;
	}

	public Form<Categoria> filtrar() {
		categoriaForm = new Categoria();
		form2 = new Form<Categoria>("form2", new CompoundPropertyModel<Categoria>(categoriaForm));
		TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		form2.add(nome);
		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(Categoria.class);

				if (categoriaForm.getNome() != null && !categoriaForm.getNome().equals("")) {
					search.addFilterLike("nome", "%" + categoriaForm.getNome() + "%");
				}

				listaCategorias = categoriaService.search(search);
				target.add(listContainer);
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}

}

package com.mytutorial.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.HomePage;
import com.mytutorial.Relatorio.RelatorioUsuario;
import com.mytutorial.model.Endereco;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.EnderecoService;
import com.mytutorial.service.UsuarioService;

import net.sf.jasperreports.engine.JRException;

public class UsuarioForm extends HomePage {

	private static final long serialVersionUID = 2474313326427632580L;

	private Form<Usuario> formFunc = new Form<>("formFunc");
	// private Form<Endereco> formEnd;
	private Form<Usuario> form2;
	private Endereco endereco;
	private List<Usuario> listaUsuarios = new ArrayList<>();
	private PageableListView<Usuario> listView;
	private LoadableDetachableModel<List<Usuario>> atualizarLista;
	private WebMarkupContainer listContainer = null;
	private ModalWindow modalWindow;
	private ModalWindow modalWindowDel;
	@SpringBean(name = "usuarioService")
	private UsuarioService usuarioService;
	@SpringBean(name = "enderecoService")
	private EnderecoService enderecoService;
	private Usuario filtrar;

	public UsuarioForm() {

		endereco = new Endereco();
		// formEnd = new Form<>("formEnd", new
		// CompoundPropertyModel<Endereco>(endereco));

		listaUsuarios = usuarioService.listar();

		add(filtrar());

		add(gerarRelatorio(listaUsuarios));

		// add(formEnd);

		// Chamando a listView
		add(container());

		modalWindow = new ModalWindow("modalWindow");
		// Tamanho do Modal
		modalWindow.setInitialHeight(750);
		modalWindow.setInitialWidth(1200);
		modalWindow.setOutputMarkupId(true);
		add(modalWindow);

		// Modal Window do delete
		modalWindowDel = new ModalWindow("modalWindowDel");
		// Tamanho
		modalWindowDel.setInitialHeight(250);
		modalWindowDel.setInitialWidth(350);
		modalWindowDel.setOutputMarkupId(true);
		add(modalWindowDel);
		// Criando o botao para o Modal
		add(new AjaxLink<String>("viewLink") {

			private static final long serialVersionUID = -7766269695313736383L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				UsuarioPanel usuarioPanel = new UsuarioPanel(modalWindow.getContentId()) {

					private static final long serialVersionUID = 277997013286385910L;

					public void executarAoSalvar(AjaxRequestTarget target, Usuario usuario) {
						usuarioService.SalvarOuAlterar(usuario);
						listaUsuarios.add(usuario);
						listaUsuarios = usuarioService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalWindow.close(target);
					};

				};
				usuarioPanel.setOutputMarkupId(true);
				add(usuarioPanel);
				modalWindow.setContent(usuarioPanel);
				modalWindow.show(target);
			};

		});

	}

	private WebMarkupContainer container() {

		listContainer = new WebMarkupContainer("theContainer");
		listContainer.setOutputMarkupId(true);

		atualizarLista = new LoadableDetachableModel<List<Usuario>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Usuario> load() {
				return listaUsuarios;
			}
		};

		listView = new PageableListView<Usuario>("listView", atualizarLista, 5) {

			private static final long serialVersionUID = -8503564664744203394L;

			@Override
			protected void populateItem(ListItem<Usuario> item) {
				Usuario user = item.getModelObject();
				item.add(new Label("nome", user.getNome()));
				item.add(editando(user));
				item.add(remover(user.getId()));
			}
		};
		add(listView);
		listView.setOutputMarkupId(true);
		listContainer.add(listView);

		add(new PagingNavigator("pag", listView));
		return listContainer;

	}

	public Form<Usuario> filtrar() {
		filtrar = new Usuario();
		form2 = new Form<Usuario>("form2", new CompoundPropertyModel<Usuario>(filtrar));
		TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		form2.add(nome);
		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(Usuario.class);

				if (filtrar.getNome() != null && !filtrar.getNome().equals("")) {
					search.addFilterLike("nome", "%" + filtrar.getNome() + "%");
				}

				listaUsuarios = usuarioService.search(search);
				target.add(listContainer);
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}

	// Editando
	AjaxLink<Usuario> editando(Usuario usuario) {
		AjaxLink<Usuario> editar = new AjaxLink<Usuario>("alterar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Usuario user = usuarioService.alterar(usuario.getId());
				UsuarioPanel funcionarioPanel = new UsuarioPanel(modalWindow.getContentId(), user) {

					private static final long serialVersionUID = 1L;

					public void executarAoSalvar(AjaxRequestTarget target, Usuario usuario) {

						usuarioService.SalvarOuAlterar(usuario);
						listaUsuarios = usuarioService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalWindow.close(target);
					};
				};
				funcionarioPanel.setOutputMarkupId(true);
				modalWindow.setContent(funcionarioPanel);
				modalWindow.show(target);
			}
		};
		editar.setOutputMarkupId(true);
		formFunc.add(editar);
		return editar;
	}

	// Removendo
	public Component remover(final Integer index) {

		AjaxLink<Usuario> button = new AjaxLink<Usuario>("excluir") {
			Usuario answer = new Usuario();

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DeletUsuario deletFuncionario = new DeletUsuario(modalWindowDel.getContentId(), answer) {

					private static final long serialVersionUID = 1L;

					public void executarAoExcluir(AjaxRequestTarget target, Usuario funcionario) {
						if (funcionario.isAnswer() == true) {
							// enderecoService.excluir(index);
							usuarioService.excluir(index);
							listaUsuarios = usuarioService.listar();
							target.appendJavaScript("sucessDelet();");
							target.add(listContainer);
						}
						modalWindowDel.close(target);
					};
				};
				deletFuncionario.setOutputMarkupId(true);
				modalWindowDel.setContent(deletFuncionario);
				modalWindowDel.show(target);
			}
		};
		button.setOutputMarkupId(true);
		formFunc.add(button);
		return button;
	}

	// Gerar relatorio de Produto
	public Link<?> gerarRelatorio(List<Usuario> user) {
		final RelatorioUsuario r = new RelatorioUsuario();
		final HashMap<String, Object> usuarios = new HashMap<String, Object>();
		// Chave para o Jasper
		usuarios.put("user", user);
		Link<?> button = new Link<Object>("relatorio") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				try {
					final byte[] bytes;
					bytes = r.gerarRelatorio(usuarios);
					if (bytes != null) {
						AbstractResourceStreamWriter Stream = new AbstractResourceStreamWriter() {

							private static final long serialVersionUID = 1L;

							@Override
							public void write(OutputStream output) throws IOException {
								output.write(bytes);
								output.close();
							}

						};

						ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(Stream);
						handler.setContentDisposition(ContentDisposition.ATTACHMENT);
						// nome do pdf
						handler.setFileName("Usuarios.pdf");
						getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
					}
				} catch (JRException e) {
					e.printStackTrace();
				}

			}
		};
		button.setOutputMarkupId(true);
		return button;

	}

}

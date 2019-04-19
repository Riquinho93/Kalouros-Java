package com.mytutorial.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mytutorial.model.Conta;
import com.mytutorial.model.Situacao;
import com.mytutorial.model.TipoConta;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.ContaService;
import com.mytutorial.service.UsuarioService;

public class ContaPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private List<Usuario> listaUsuarios = new ArrayList<>();
	private List<Conta> listaContas;
	private Form<Conta> form;

	@SpringBean(name = "usuarioService")
	private UsuarioService usuarioService;

	private Usuario usuario;
	@SpringBean(name = "contaService")
	private ContaService contaService;

	public ContaPanel(String id) {
		this(id, new Conta());
	}

	public ContaPanel(String id, Conta conta) {
		super(id);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		listaContas = new ArrayList<>();

		form = new Form<Conta>("form", new CompoundPropertyModel<Conta>(conta));

		NumberTextField<Integer> numeroConta = new NumberTextField<Integer>("numeroConta");
		PasswordTextField senha = new PasswordTextField("senha");
		PasswordTextField confirmarSenha = new PasswordTextField("confirmarSenha");
		// NumberTextField<Double> limite = new NumberTextField<>("limite");

		numeroConta.setOutputMarkupId(true);
		senha.setOutputMarkupId(true);
		confirmarSenha.setOutputMarkupId(true);
		// limite.setOutputMarkupId(true).setVisible(false);

		// Situacao
		ChoiceRenderer<Situacao> renderer2 = new ChoiceRenderer<Situacao>("descricao");
		IModel<List<Situacao>> model2 = new LoadableDetachableModel<List<Situacao>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Situacao> load() {
				return Situacao.situacao();
			}
		};

		DropDownChoice<Situacao> situacoes = new DropDownChoice<>("situacao", model2, renderer2);

		// Select usuarios
		/*
		 * IModel<List<Usuario>> model = new LoadableDetachableModel<List<Usuario>>() {
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @Override protected List<Usuario> load() { return usuario; } };
		 */

		usuario = new Usuario();
		DropDownChoice<Usuario> usuarios = new DropDownChoice<Usuario>("usuario",
				new PropertyModel<Usuario>(conta, "usuario"), listaUsuarios, new ChoiceRenderer<Usuario>("nome"));

		// Tipo da Conta
		ChoiceRenderer<TipoConta> renderer = new ChoiceRenderer<TipoConta>("descricao");
		IModel<List<TipoConta>> model = new LoadableDetachableModel<List<TipoConta>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<TipoConta> load() {
				return TipoConta.contas();
			}
		};

		DropDownChoice<TipoConta> tipos = new DropDownChoice<>("tipoConta", model, renderer);

		AjaxButton ajaxButton = new AjaxButton("salvar") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				
				if (usuarios.getModelObject() == null) {
					feedbackPanel.error("Nome do usuario é obrigatorio!");
				}
				if (numeroConta.getModelObject() == null) {
					feedbackPanel.error("Numero da conta é obrigatorio!");
				}
				if (tipos.getModelObject() == null) {
					feedbackPanel.error("Tipo da conta é obrigatorio!");
				}
				if (senha.getModelObject() == null) {
					feedbackPanel.error("Senha é obrigatorio!");
				}
				if (!confirmarSenha.getModelObject().equals(senha.getModelObject())) {
					feedbackPanel.error("As senhas devem ser iguais!");
				}
				if (usuarios.getModelObject() != null
						&& numeroConta.getModelObject() != null && tipos.getModelObject() != null
						&& tipos.getModelObject() != null
						&& senha.getModelObject().equals(confirmarSenha.getModelObject())) {

					executarAoSalvar(target, conta);
					listaContas.add(conta);
					Usuario usuario = usuarioService.buscarPorId(conta.getUsuario().getId());
//					usuario.setListacontas(listaContas);

					listaContas = new ArrayList<>();
					form.clearInput();
					form.modelChanged();
					form.setDefaultModelObject(conta);
					target.add(form);
					target.add(numeroConta, senha, confirmarSenha);
				}
				target.add(feedbackPanel);
			}
		};
		add(feedbackPanel);
		ajaxButton.setOutputMarkupId(true);
		form.add(ajaxButton, tipos, usuarios, situacoes);
		form.add(numeroConta, senha, confirmarSenha);
		add(form);
		voltar();
	}

	public void executarAoSalvar(AjaxRequestTarget target, Conta conta) {

	}

	private void voltar() {
		AjaxLink<Login> ajaxLink = new AjaxLink<Login>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ContaForm.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}

}

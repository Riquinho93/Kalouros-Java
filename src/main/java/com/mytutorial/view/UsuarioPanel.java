package com.mytutorial.view;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.mytutorial.model.Endereco;
import com.mytutorial.model.Funcao;
import com.mytutorial.model.Perfil;
import com.mytutorial.model.Situacao;
import com.mytutorial.model.Usuario;

public class UsuarioPanel extends Panel {

	private static final long serialVersionUID = 8991195474675368668L;

	private Form<Usuario> formFunc;

	public UsuarioPanel(String id) {
		this(id, new Usuario());
	}

	public UsuarioPanel(String id, Usuario usuario) {
		super(id);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		formFunc = new Form<Usuario>("formFunc", new CompoundPropertyModel<Usuario>(usuario));

		TextField<String> nome = new TextField<>("nome");
		NumberTextField<Integer> identidade = new NumberTextField<>("identidade");
		NumberTextField<Integer> cpf = new NumberTextField<>("cpf");
		NumberTextField<Double> renda = new NumberTextField<>("renda");
		TextField<String> telefone = new TextField<>("telefone");
		TextField<String> email = new TextField<>("email");

		TextField<String> cep = new TextField<String>("endereco.cep");
		TextField<String> logradouro = new TextField<>("endereco.logradouro");
		NumberTextField<Integer> numero = new NumberTextField<>("endereco.numero");
		TextField<String> bairro = new TextField<>("endereco.bairro");
		TextField<String> cidade = new TextField<>("endereco.cidade");
		TextField<String> estado = new TextField<>("endereco.estado");

		/*
		 * nome.setRequired(true); identidade.setRequired(true); cpf.setRequired(true);
		 * telefone.setRequired(true); logradouro.setRequired(true);
		 * bairro.setRequired(true); cidade.setRequired(true); estado.setRequired(true);
		 */

		nome.setOutputMarkupId(true);
		identidade.setOutputMarkupId(true);
		cpf.setOutputMarkupId(true);
		telefone.setOutputMarkupId(true);
		renda.setOutputMarkupId(true);
		email.setOutputMarkupId(true);
		cep.setOutputMarkupId(true);
		logradouro.setOutputMarkupId(true);
		numero.setOutputMarkupId(true);
		bairro.setOutputMarkupId(true);
		cidade.setOutputMarkupId(true);
		estado.setOutputMarkupId(true);

		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sexo");
		radioGroupAtivo.setRequired(true);
		radioGroupAtivo
				.add(new Radio<Boolean>("sim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sim")));
		radioGroupAtivo
				.add(new Radio<Boolean>("nao", new Model<Boolean>(false)).add(new AttributeModifier("id", "nao")));
		formFunc.add(radioGroupAtivo);

		// Data de Nascimento
		DatePicker datePickerInicial = new DatePicker() {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean alignWithIcon() {
				return true;
			}

			@Override
			protected boolean enableMonthYearSelection() {
				return false;
			}
		};

		DateTextField data = new DateTextField("dataNascimento", "dd/MM/yyyy");
		datePickerInicial.setAutoHide(true);
		data.add(datePickerInicial);
		data.setOutputMarkupId(true);
		formFunc.add(data);

		// Funcao
		ChoiceRenderer<Funcao> renderer = new ChoiceRenderer<Funcao>("descricao");
		IModel<List<Funcao>> model = new LoadableDetachableModel<List<Funcao>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Funcao> load() {
				return Funcao.funcoes();
			}
		};

		DropDownChoice<Funcao> funcoes = new DropDownChoice<>("funcao", model, renderer);

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

		AjaxButton button = new AjaxButton("submit") {

			private static final long serialVersionUID = 994698440577863113L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				if (nome.getModelObject() == null) {
					feedbackPanel.error("Nome é obrigatorio!");
				}
				if (identidade.getModelObject() == null) {
					feedbackPanel.error("Identidade é obrigatorio!");
				}
				if (cpf.getModelObject() == null) {
					feedbackPanel.error("Cpf é obrigatorio!");
				}
				if (telefone.getModelObject() == null) {
					feedbackPanel.error("Telefone é obrigatorio!");
				}
				if (renda.getModelObject() == null) {
					feedbackPanel.error("Renda é obrigatorio!");
				}
				if (radioGroupAtivo.getModelObject() == null) {
					feedbackPanel.error("Sexo é obrigatorio!");
				}
				if (data.getModelObject() == null) {
					feedbackPanel.error("Data de Nascimento é obrigatorio!");
				}
				if (funcoes.getModelObject() == null) {
					feedbackPanel.error("Funcao é obrigatorio!");
				}
				if (situacoes.getModelObject() == null) {
					feedbackPanel.error("Situacao é obrigatorio!");
				}
				if (logradouro.getModelObject() == null) {
					feedbackPanel.error("logradouro é obrigatorio!");
				}
				if (bairro.getModelObject() == null) {
					feedbackPanel.error("Bairro é obrigatorio!");
				}
				if (cidade.getModelObject() == null) {
					feedbackPanel.error("Cidade é obrigatorio!");
				}
				if (estado.getModelObject() == null) {
					feedbackPanel.error("Estado é obrigatorio!");
				} else {

					executarAoSalvar(target, usuario);
					target.add(nome);
					target.add(identidade);
					target.add(cpf);
					target.add(telefone);
					target.add(email);
					target.add(cep);
					target.add(logradouro);
					target.add(numero);
					target.add(bairro);
					target.add(cidade);
					target.add(estado);
					target.add(renda);
				}
				target.add(feedbackPanel);

			}
		};
		add(feedbackPanel);
		button.setOutputMarkupId(true);
		formFunc.add(funcoes, situacoes);
		formFunc.add(nome, identidade, cpf, renda, telefone, email, cep, logradouro, numero, bairro, cidade, estado);
		formFunc.add(button);
		add(formFunc);
		voltar();

	}

	// Enviando os dados para o HomePage
	public void executarAoSalvar(AjaxRequestTarget target, Usuario usuario) {

	}

	private void voltar() {
		AjaxLink<Login> ajaxLink = new AjaxLink<Login>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(UsuarioForm.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		formFunc.add(ajaxLink);
	}

}

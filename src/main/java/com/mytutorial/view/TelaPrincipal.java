package com.mytutorial.view;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.HomePage;
import com.mytutorial.model.Conta;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.ContaService;
import com.mytutorial.service.UsuarioService;

public class TelaPrincipal extends HomePage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "contaService")
	private ContaService contaService;

	public TelaPrincipal() {
		this(new Usuario());
	}

	public TelaPrincipal(Usuario usuario) {
		/*
		 * add(new Label("usuario", conta.getUsuario().getNome())); add(new
		 * Label("numeroConta", conta.getNumeroConta())); add(new Label("banco",
		 * conta.getBanco().getNome())); add(new Label("saldo", conta.getSaldo()));
		 * add(new Label("tipoConta", conta.getTipoConta()));
		 */
	}

}

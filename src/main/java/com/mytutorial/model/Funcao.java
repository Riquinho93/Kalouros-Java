package com.mytutorial.model;

import java.util.ArrayList;
import java.util.List;

public enum Funcao {

	GERENTE("Gerente"), FUNCIONARIO("Funcionario"), CLIENTE("Cliente");
	private String descricaoFuncao;

	private Funcao(String descricaoFuncao) {
		this.descricaoFuncao = descricaoFuncao;
	}

	public static List<Funcao> funcoes() {
		List<Funcao> funcao = new ArrayList<>();
		funcao.add(CLIENTE);
		funcao.add(FUNCIONARIO);
		funcao.add(GERENTE);
		return funcao;
	}

	public String getDescricaoFuncao() {
		return descricaoFuncao;
	}

	public void setDescricaoFuncao(String descricaoFuncao) {
		this.descricaoFuncao = descricaoFuncao;
	}

	public String getDescricao() {
		return descricaoFuncao;
	}

	public void setDescricao(String descricao) {
		this.descricaoFuncao = descricao;
	}

}

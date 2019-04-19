package com.mytutorial.model;

import java.util.ArrayList;
import java.util.List;

public enum Situacao {

	ATIVADO("Ativado"), DESATIVADO("Desativado");
	private String descricao;

	private Situacao(String descricao) {
		this.descricao = descricao;
	}

	public static List<Situacao> situacao() {
		List<Situacao> situacoes = new ArrayList<>();
		situacoes.add(ATIVADO);
		situacoes.add(DESATIVADO);
		return situacoes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

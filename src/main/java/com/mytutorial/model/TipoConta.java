package com.mytutorial.model;

import java.util.ArrayList;
import java.util.List;

public enum TipoConta {
	CORRENTE("Corrente"), POUPANÇA("Poupança");
	
	private String descricao;
	
	private TipoConta(String descricao) {
		this.descricao = descricao;
	}
	//Metodos
	
	public static List<TipoConta> contas(){
		List<TipoConta> tiposContas = new ArrayList<>();
		tiposContas.add(CORRENTE);
		tiposContas.add(POUPANÇA);
		return tiposContas;
	}
	
	
	//Getters e Setters

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}

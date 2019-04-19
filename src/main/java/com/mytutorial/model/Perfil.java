package com.mytutorial.model;

import java.util.ArrayList;
import java.util.List;

public enum Perfil {

	USUARIO("Usuario"), ADMIN("Admin");
	private String descricao;

	private Perfil(String descricao) {
		this.descricao = descricao;
	}

	public static List<Perfil> perfil() {
		List<Perfil> perfils = new ArrayList<>();
		perfils.add(USUARIO);
		perfils.add(ADMIN);
		return perfils;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

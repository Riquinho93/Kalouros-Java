package com.mytutorial.service;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.model.Conta;

public interface IContaService {
	
	public void SalvarOuAlterar(Conta conta);

	public Conta buscarPorid(Integer id);

	public void excluir(Integer id);

	public List<Conta> listar();

	public List<Conta> search(Search search);
}

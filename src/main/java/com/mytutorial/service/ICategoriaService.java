package com.mytutorial.service;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.model.Categoria;

public interface ICategoriaService {
	
	public void SalvarOuAlterar(Categoria pessoa);

	public Categoria buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Categoria> listar();
	
	public List<Categoria> search(Search search);

}

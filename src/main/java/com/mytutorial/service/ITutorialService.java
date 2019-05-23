package com.mytutorial.service;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.model.Tutorial;

public interface ITutorialService {
	
	public void SalvarOuAlterar(Tutorial tutorial);

	public Tutorial buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Tutorial> listar();
	
	public List<Tutorial> search(Search search);
}

package com.mytutorial.service;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.model.Comentario;

public interface IComentarioService {
	
	public void SalvarOuAlterar(Comentario comentario);

	public Comentario buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Comentario> listar();
	
	public List<Comentario> search(Search search);

}

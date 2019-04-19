package com.mytutorial.service;

import java.util.List;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.model.Usuario;

public interface IUsuarioService {
	
	public void SalvarOuAlterar(Usuario pessoa);

	public Usuario buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Usuario> listar();
	
	public List<Usuario> search(Search search);
}

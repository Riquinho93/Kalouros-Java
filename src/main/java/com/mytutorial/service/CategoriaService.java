package com.mytutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.dao.CategoriaDao;
import com.mytutorial.model.Categoria;

@Service
public class CategoriaService implements ICategoriaService {

	private CategoriaDao categoriaDao;

	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Categoria categoria) {
		categoriaDao.SalvarOuAlterar(categoria);
	}

	@Override
	@Transactional
	public Categoria buscarPorId(Integer id) {
		return categoriaDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		categoriaDao.excluir(id);
	}

	@Override
	@Transactional
	public List<Categoria> listar() {
		return categoriaDao.listar();
	}

	@Override
	@Transactional
	public List<Categoria> search(Search search) {
		return categoriaDao.searchDao(search);
	}

}

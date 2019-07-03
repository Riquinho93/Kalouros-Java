package com.mytutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.dao.ComentarioDao;
import com.mytutorial.model.Comentario;

@Service
public class ComentarioService implements IComentarioService {
	
	private ComentarioDao comentarioDao;
	
	public void setComentarioDao(ComentarioDao comentarioDao) {
		this.comentarioDao = comentarioDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Comentario comentario) {
		comentarioDao.SalvarOuAlterar(comentario);
	}

	@Override
	@Transactional
	public Comentario buscarPorId(Integer id) {
		return comentarioDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		comentarioDao.excluir(id);
	}

	@Override
	@Transactional
	public List<Comentario> listar() {
		return comentarioDao.listar();
	}

	@Override
	public List<Comentario> search(Search search) {
		return comentarioDao.searchDao(search);
	}

}

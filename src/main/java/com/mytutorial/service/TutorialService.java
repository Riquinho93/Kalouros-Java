package com.mytutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.dao.TutorialDao;
import com.mytutorial.model.Tutorial;

@Service
public class TutorialService implements ITutorialService {

	private TutorialDao tutorialDao;

	public void setTutorialDao(TutorialDao tutorialDao) {
		this.tutorialDao = tutorialDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Tutorial tutorial) {
		tutorialDao.SalvarOuAlterar(tutorial);
	}

	@Override
	@Transactional
	public Tutorial buscarPorId(Integer id) {
		return tutorialDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		tutorialDao.excluir(id);
	}

	@Override
	@Transactional
	public List<Tutorial> listar() {
		return tutorialDao.listar();
	}

	@Override
	public List<Tutorial> search(Search search) {
		return tutorialDao.searchDao(search);
	}

}

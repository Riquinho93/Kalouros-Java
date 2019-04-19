package com.mytutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mytutorial.dao.EnderecoDao;
import com.mytutorial.model.Endereco;


@Service
public class EnderecoService implements IEnderecoService{
	
	private EnderecoDao enderecoDao;

	public void setEnderecoDao(EnderecoDao enderecoDao) {
		this.enderecoDao = enderecoDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Endereco endereco) {
		enderecoDao.SalvarOuAlterar(endereco);		
	}

	@Override
	@Transactional(readOnly = true)
	public Endereco buscarPorId(Integer id) {
		return enderecoDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		enderecoDao.excluir(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Endereco> listar() {
		return enderecoDao.listar();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Endereco> search(Search search) {
		return enderecoDao.searchDao(search);
	}
	
}

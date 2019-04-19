package com.mytutorial.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mytutorial.model.Endereco;

@Repository
public class EnderecoDao extends GenericDao<Endereco, Serializable> {

	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<Endereco> listar() {
		Query query = getSessionFactory().getCurrentSession().createQuery("select c from Endreco c order by c.nome");
		List<Endereco> userList = query.list();
		return userList;
	}
}

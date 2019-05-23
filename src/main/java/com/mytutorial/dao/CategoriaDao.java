package com.mytutorial.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mytutorial.model.Categoria;


@Repository
public class CategoriaDao extends GenericDao<Categoria, Serializable> {
	
	@SuppressWarnings("unchecked")
	public List<Categoria> listar() {
		String hql = "select f from Categoria f";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Categoria> userList = query.list();
		return userList;
	}

}

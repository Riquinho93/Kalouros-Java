package com.mytutorial.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mytutorial.model.Comentario;

@Repository
public class ComentarioDao extends GenericDao<Comentario, Serializable>{
	
	@SuppressWarnings("unchecked")
	public List<Comentario> listar() {
		String hql = "select f from Comentario f order by text";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Comentario> userList = query.list();
		return userList;
	}
}

package com.mytutorial.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.mytutorial.model.Tutorial;

@Repository
public class TutorialDao extends GenericDao<Tutorial, Serializable>{
	
	@SuppressWarnings("unchecked")
	public List<Tutorial> listar() {
		String hql = "select f from Tutorial f";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Tutorial> userList = query.list();
		return userList;
	}
	
	
}

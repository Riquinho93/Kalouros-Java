package com.mytutorial.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

public abstract class GenericDao<Entidade, id extends Serializable> extends GenericDAOImpl<Entidade, id> {

	private Session session;

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public Entidade buscarPorId(Integer id) {

		session = getSessionFactory().openSession();

		Entidade t = (Entidade) session.get(persistentClass, id);

		return t;
	}

//	@Transactional
	public void SalvarOuAlterar(Entidade entidades) {
		session = getSessionFactory().openSession();
		session.beginTransaction();

		session.saveOrUpdate(entidades);

		session.getTransaction().commit();
		session.close();
	}

//	@Transactional
	@SuppressWarnings("unchecked")
	public void excluir(Integer id) {
		session = getSessionFactory().openSession();
		session.beginTransaction();
		Entidade funcionarioRemover = (Entidade) session.get(persistentClass, id);
		session.delete(funcionarioRemover);
		session.getTransaction().commit();
		session.close();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public List<Entidade> searchDao(Search search) {
		session = getSessionFactory().openSession();
		session.beginTransaction();
		List<Entidade> userList = search(search);
		session.close();
		return userList;

	}

	/*
	 * public void pesquisar(String nome) { try { Connection conexao = null; String
	 * sql = "select * from Contato where nome like ?"; PreparedStatement ps =
	 * (PreparedStatement) conexao.prepareStatement(sql); ps.setString(1, nome +
	 * "%"); ps.executeQuery(); } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}

package com.manager.sys.base.dao;

import static com.manager.sys.common.StringUtil.isEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.manager.sys.common.StringUtil;

public class HibernateDao implements IDAOHibernate4 {
	private SessionFactory sessionFactory;
	private boolean alwaysUseNewSession = false;

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Object> findPageByQuery(String queryString,
			Object[] parameters, Integer firstResult, Integer maxResult)
			throws Exception {
		Session session = getSession();
		if (session == null) {
			throw new Exception("Session is null");
		}
		Query query = session.createQuery(queryString);
		if (parameters != null && parameters.length > 0) {
			for (int i = 0, j = parameters.length; i < j; i++) {
				Object obj = parameters[i];
				query.setParameter(i, obj);
			}
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
		if (query != null) {
			@SuppressWarnings("unchecked")
			List<Object> list = query.list();
			return list;
		}
		return new ArrayList<Object>();
	}

	@Override
	public List<Object[]> findPageBySQL(String queryString,
			Object[] parameters, Integer firstResult, Integer maxResult)
			throws Exception {
		Session session = getSession();
		if (session == null) {
			throw new Exception("Session is null");
		}
		Query query = session.createSQLQuery(queryString);
		if (parameters != null && parameters.length > 0) {
			for (int i = 0, j = parameters.length; i < j; i++) {
				Object obj = parameters[i];
				query.setParameter(i, obj);
			}
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
		if (query != null) {
			@SuppressWarnings("unchecked")
			List<Object[]> list = query.list();
			return list;
		}
		return new ArrayList<Object[]>();
	}

	/**
	 * 根据HQL语句查询实体对象
	 */
	@Override
	public List<Object> findByQuery(String queryString, Object[] parameters)
			throws Exception {
		Session session = getSession();
		if (session == null) {
			throw new Exception("Session is null");
		}
		Query query = session.createQuery(queryString);
		if (parameters != null && parameters.length > 0) {
			for (int i = 0, j = parameters.length; i < j; i++) {
				Object obj = parameters[i];
				query.setParameter(i, obj);
			}
		}
		@SuppressWarnings("unchecked")
		List<Object> list = query.list();
		return list;
	}

	@Override
	public java.io.Serializable save(Object entity) throws Exception {
		Session session = getSession();
		java.io.Serializable result = null;
		if (entity != null && !"".equals(entity)) {
			result = session.save(entity);
		}
		return result;
	}

	@Override
	public java.io.Serializable saveOrUpdate(Object entity)
			throws DataAccessException {
		Session session = getSession();
		if (entity != null && !"".equals(entity)) {
			session.saveOrUpdate(entity);
		}
		return null;
	}

	public Session getSession(FlushMode flushModel) {
		Session session = getSession();
		session.setFlushMode(flushModel);
		return session;
	}

	@Override
	public Session getSession() {
		if (isAlwaysUseNewSession()) {
			return SessionFactoryUtils.openSession(this.sessionFactory);
		}
		try {
			return this.sessionFactory.getCurrentSession();
		} catch (Exception ex) {
			throw new DataAccessResourceFailureException(
					"Could not get current session ", ex);
		}
	}

	/**
	 * @return the alwaysUserNewSession
	 */
	public boolean isAlwaysUseNewSession() {
		return alwaysUseNewSession;
	}

	/**
	 * @param alwaysUserNewSession
	 *            the alwaysUserNewSession to set
	 */
	public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
		this.alwaysUseNewSession = alwaysUseNewSession;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void delete(Object entity) throws DataAccessException {
		Session session = getSession();
		session.delete(entity);
	}

	public void delete(Class<?> clazz, Serializable id) throws Exception {
		Session session = getSession();
		Object obj = session.get(clazz, id);
		if (!isEmpty(obj)) {
			session.delete(obj);
			session.flush();
		} else {
			throw new Exception("没有查询到相关的信息");
		}
	}

	@Override
	public Object get(Class<?> clazz, Serializable id) {
		System.out.println("GET的数据：:" + id);
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		Session session = getSession();
		Object obj = session.get(clazz, id);
		return obj;
	}

	@Override
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
			throws DataAccessException {
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		Session session = getSession();
		Object obj = session.get(clazz, id);
		return obj;
	}

	@Override
	public Object load(Class<Object> clazz, Serializable id)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 只能取其中的一个属性
	 */
	public Long getCount(Class<?> clazz, Object obj, String attr)
			throws Exception {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append(" select  count(c)  from  ");
		query.append(clazz.getName());
		query.append(" c");
		query.append(" where 1 = 1 ");
		if (!isEmpty(obj)) {
			query.append("and c.");
			query.append(attr);
			query.append(" like ?");
			objList.add("%" + obj + "%");

		}
		List<Object> countObj = findByQuery(query.toString(), objList.toArray());
		long total = 0;
		if (countObj != null && !countObj.isEmpty()) {
			total = (Long) countObj.get(0);
		}
		return total;
	}

	public Long getCount2(Class<?> clazz, Object obj, String attr,Object obj2, String attr2)
			throws Exception {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append(" select  count(c)  from  ");
		query.append(clazz.getName());
		query.append(" c");
		query.append(" where 1 = 1 ");
		if (!isEmpty(obj)) {
			query.append("and c.");
			query.append(attr);
			query.append(" like ?");
			objList.add("%" + obj + "%");
			query.append("and c.");
			query.append(attr2);
			query.append(" like ?");
			objList.add("%" + obj2 + "%" );

		}
		List<Object> countObj = findByQuery(query.toString(), objList.toArray());
		long total = 0;
		if (countObj != null && !countObj.isEmpty()) {
			total = (Long) countObj.get(0);
		}
		return total;
	}
	
	@Override
	public List<?> findBySQLQuery(String queryString, Object[] parameters)
			throws Exception {
		Session session = getSession();
		Query query = session.createSQLQuery(queryString);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				Object obj = parameters[i];
				query.setParameter(i, obj);
			}
		}
		List<?> list = query.list();
		return list;
	}
}

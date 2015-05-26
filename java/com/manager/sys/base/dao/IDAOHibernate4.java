package com.manager.sys.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataAccessException;

/**
 * Hibernate基础接口
 * 
 * @author HMY
 * 
 */
public interface IDAOHibernate4 {

	/**
	 * 获取session对象
	 * 
	 * @return
	 */
	public Session getSession();

	/**
	 * 删除实体信息
	 * 
	 * @param entity
	 */
	public void delete(Object entity) throws DataAccessException;

	/**
	 * 根据hql获取分页查询结果
	 * 
	 * @param queryString
	 * @param firstResult
	 * @param maxResult
	 * @return
	 * @throws DataException
	 */
	public List<?> findPageByQuery(String queryString, Object[] parameters, Integer firstResult,
			Integer maxResult) throws Exception;

	/**
	 * 根据sql查询分页信息
	 * 
	 * @param queryString
	 * @param parameters
	 * @param firstResult
	 * @param maxResult
	 * @return
	 * @throws Exception
	 */
	public List<?> findPageBySQL(String queryString, Object[] parameters, Integer firstResult,
			Integer maxResult) throws Exception;

	/**
	 * 根据hql查询实体对象的List集合
	 * 
	 * @param queryString
	 * @param parameters
	 * @return
	 * @throws DataException
	 */
	public List<Object> findByQuery(String queryString, Object[] parameters) throws Exception;

	/**
	 * 根据sql查询实体对象的List集合
	 * 
	 * @param queryString
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public List<?> findBySQLQuery(String queryString, Object[] parameters) throws Exception;

	public Object get(Class<?> clazz, Serializable id);

	/**
	 * 根据ID获取实体
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode) throws DataAccessException;

	/**
	 * 根据Class和参数及相应的实体属性获取总共的记录数
	 * 
	 * @param clazz
	 * @param obj
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public Long getCount(Class<?> clazz, Object obj, String attr) throws Exception;

	/**
	 * 根据ID laod实体
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object load(Class<Object> clazz, Serializable id) throws DataAccessException;

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public java.io.Serializable save(Object entity) throws Exception;

	/**
	 * 保存及更新实体
	 * 
	 * @param entity
	 */
	public java.io.Serializable saveOrUpdate(Object entity) throws Exception;

}

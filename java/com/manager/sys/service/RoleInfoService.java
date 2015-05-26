package com.manager.sys.service;

import java.util.List;
import java.util.Map;

import com.manager.sys.domain.RoleInfo;

public interface RoleInfoService {
	/**
	 * 获取所有角色信息的id和角色名称，并存放到Map集合中
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAllRoles() throws Exception;

	/**
	 * 根据角色编号获取角色信息的集合 返回的对象为RoleInfo实体
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Object> find(Integer roleId) throws Exception;

	/**
	 * 分页显示数据
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findByPage(Object obj, Integer page, Integer rows) throws Exception;

	/**
	 * 保存角色信息
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public java.io.Serializable save(RoleInfo role) throws Exception;

	/**
	 * 根据ID编号获取角色信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RoleInfo get(java.io.Serializable id) throws Exception;

	/**
	 * 删除角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public void delete(String[] ids) throws IllegalAccessException , Exception ;

}

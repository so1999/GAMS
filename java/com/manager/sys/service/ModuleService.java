package com.manager.sys.service;

import java.util.List;
import java.util.Map;

import com.manager.sys.domain.Module;

public interface ModuleService {
	/**
	 * 根据栏目标识符，查询栏目名称
	 * 
	 * @param sign
	 * @return
	 */
	public String findModuleName(String sign)throws Exception;

	/**
	 * 根据父栏目编号获取栏目信息的集合，其中"List<Object>"中存放的是Module实体
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Object> findModule(Integer parentId) throws Exception;

	/**
	 * 通过
	 * 
	 * @param paramter
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findTree(Integer parentId, Integer roleId) throws Exception;

	/**
	 * 分页显示栏目信息
	 * 
	 * @param page
	 * @param rows
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findByPage(Integer page, Integer rows, Object objs) throws Exception;

	/**
	 * 删除栏目信息
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) throws Exception;

	/**
	 * 根据栏目编号获取单个的栏目信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Module get(Integer id) throws Exception;

	/**
	 * 保存及更新栏目信息
	 * 
	 * @param mod
	 * @throws Exception
	 */
	public void saveOrupdate(Module mod) throws Exception;

	/**
	 * 获取栏目的父栏目信息的集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getParentModules() throws Exception;
}

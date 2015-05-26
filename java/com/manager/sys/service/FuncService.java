package com.manager.sys.service;

import java.util.List;
import java.util.Map;

import com.manager.sys.domain.FuncInfo;

public interface FuncService {
	
	/**
	 * 根据功能标识获取功能ID
	 * @param funcSign
	 * @return
	 */
	public Integer findByFuncSign(String funcSign);
	/**
	 * 通过栏目编号获取栏目下的功能
	 * @return
	 */
	public List<Map<String, Object>> findFuncByModuleId(Integer moduleId)throws Exception;
	/**
	 * 分页显示信息
	 * 
	 * @return
	 */
	public Map<String, Object> findByPage(Object objs, Integer page, Integer rows) throws Exception;

	/**
	 * 保存及更新功能信息
	 * @param func
	 * @throws Exception
	 */
	public void saveOrUpdate(FuncInfo func) throws Exception;
	/**
	 * 删除栏目下功能的信息
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) throws Exception ;
	
	/**
	 * 根据功能ID获取功能信息
	 * @param id
	 * @return
	 */
	public FuncInfo get(Integer id);
	
}

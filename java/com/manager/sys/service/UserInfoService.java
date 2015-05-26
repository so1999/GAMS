package com.manager.sys.service;

import java.util.Map;

import com.manager.sys.domain.UserInfo;

public interface UserInfoService {
	/**
	 * 修改密码
	 * 
	 * @throws Exception
	 */
	public void updatePswd(String usrname, String password) throws Exception;

	/**
	 * 根据用户名查询单个的用户信息
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public UserInfo findUserByName(String userName) throws Exception;

	/**
	 * 验证用户权限
	 * 
	 * @param usrId
	 * @param roleId
	 * @param moduleId
	 * @param funcId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkAuthorize(String usrId, String funcId)
			throws Exception;

	/**
	 * 用户登录
	 * 
	 * @param userId
	 *            为用户的帐号 id
	 * @param password
	 * @return
	 */
	public String findUsrName(String userId) throws Exception;

	/**
	 * 显示分页查的数据
	 * 
	 * @param start
	 * @param limit
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findByPage(Integer start, Integer limit,
			Object objs) throws Exception;

	/**
	 * 保存及更新实体对象
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public java.io.Serializable saveOrUpdate(UserInfo entity) throws Exception;

	public void update(UserInfo usr) throws Exception;

	/**
	 * 根据主键获取单个的实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserInfo get(Integer id) throws Exception;

	/**
	 * 删除实体
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) throws Exception;
}

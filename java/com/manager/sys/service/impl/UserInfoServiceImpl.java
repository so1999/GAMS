package com.manager.sys.service.impl;

import static com.manager.sys.common.StringUtil.isEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.FuncInfo;
import com.manager.sys.domain.Module;
import com.manager.sys.domain.RoleInfo;
import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService {

	private HibernateDao hdao;

	/**
	 * 分页显示用户信息
	 */
	@Override
	public Map<String, Object> findByPage(Integer page, Integer rows,
			Object param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> paramsList = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from UserInfo usr where 1=1 ");
		if (!isEmpty(param)) {
			queryString.append(" and usr.usrName like ? ");
			paramsList.add("%" + param + "%");
		}
		Map<Integer, String> statusMap = new HashMap<Integer, String>();
		statusMap.put(1, "启用");
		statusMap.put(0, "禁用");
		List<Object> users = hdao.findPageByQuery(queryString.toString(),
				paramsList.toArray(), page, rows);
		List<UserInfo> list = new ArrayList<UserInfo>();
		if (users != null) {
			for (Object obj : users) {
				UserInfo us = new UserInfo();
				UserInfo u = (UserInfo) obj;
				us.setId(u.getId());
				us.setUsrId(String.valueOf(u.getId()));
				us.setUsrName(u.getUsrName());
				us.setPart(u.getPart());
				us.setPartid(u.getPartid());
				us.setStatusStr(statusMap.get(u.getStatus()));
				us.setCreateTimeStr(StringUtil.formate(u.getCreateTime()));
				us.setRemark(u.getRemark());
				list.add(us);
			}
		}
		map.put("total", hdao.getCount(UserInfo.class, param, "usrName"));
		map.put("rows", list);
		return map;
	}

	/**
	 * @param hibernateDao
	 *            the hibernateDao to set
	 */
	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	/**
	 * @param entity
	 *            保存及更新实体
	 */
	@Override
	public Serializable saveOrUpdate(UserInfo entity) throws Exception {
		return hdao.saveOrUpdate(entity);
	}

	/**
	 * @param id
	 *            UserInfo的主键
	 */
	@Override
	public UserInfo get(Integer id) throws Exception {
		return (UserInfo) hdao.get(UserInfo.class, id);
	}

	@Override
	public void delete(String[] ids) throws Exception {
		if (!isEmpty(ids) && ids.length > 0) {
			for (String id : ids) {
				if (!isEmpty(id)) {
					hdao.delete(UserInfo.class,
							!isEmpty(id) ? Integer.valueOf(id) : 0);
				}
			}
		}
	}

	/**
	 * 验证的思路： 首先：通过登录的用户帐号获取用户的角色信息。
	 * 第二：遍历角色信息，通过角色获取栏目信息和栏目下功能的信息，因为栏目与功能的编号都是唯一的
	 * ，所以直接把功能编号和栏目编号放置到map集合中，在调用的未知通过判断map的key是否包含所传入的功能标识来判断其是否具有权限
	 */
	@Override
	public Map<String, String> checkAuthorize(String usrName, String funcSign)
			throws Exception {
		Map<String, String> authMap = new HashMap<String, String>();
		/* 如果用户编号为空，则直接返回为null */
		if (isEmpty(usrName)) {
			return null;
		}
		System.out.println("usrName:" + usrName);
		/* 如果查询不到用户信息，则直接返回为null */
		UserInfo usrInfo = findUserByName(usrName);
		// UserInfo usrInfo = get( );
		if (isEmpty(usrInfo)) {
			return null;
		}
		Set<RoleInfo> roles = usrInfo.getRoles();
		System.out.println("角色：" + roles);
		/* 如果角色信息不为空,则循环遍历角色信息 */
		if (!isEmpty(roles) && roles.size() > 0) {
			for (RoleInfo role : roles) {
				/* 如果角色实体不为空，则通过角色获取按钮权限和栏目权限 */
				if (!isEmpty(role)) {
					// 获取按钮权限即功能权限
					Set<FuncInfo> funcs = role.getFuncs();
					System.out.println("功能权限：" + funcs);
					// 如果获取的按钮权限不为空且按钮权限的数量大于0
					if (!isEmpty(funcs) && funcs.size() > 0) {
						for (FuncInfo func : funcs) {
							if (!isEmpty(func)) {
								if (func.getFuncSign().equals(funcSign)) {
									authMap.put(func.getFuncSign(), null);
								}
							}
						}
					}
					// 获取栏目权限
					Set<Module> modules = role.getModules();
					System.out.println("栏目权限：" + modules);
					// 如果获取的栏目信息不为空，且栏目数量大于0
					if (!isEmpty(modules) && modules.size() > 0) {
						for (Module mods : modules) {
							if (!isEmpty(mods)) {
								authMap.put(mods.getModuleSign(), null);
							}
						}
					}
				}
			}
		}
		return authMap;
	}

	@Override
	public String findUsrName(String usrName) throws Exception {
		List<Object> objUsr = hdao.findByQuery(
				"select usr.id from UserInfo usr where usr.usrName =? ",
				new Object[] { usrName });
		if (objUsr != null && objUsr.size() > 0) {
			return String.valueOf(objUsr.get(0));
		}
		return null;
	}

	@Override
	public UserInfo findUserByName(String usrName) throws Exception {
		List<Object> objUsr = hdao.findByQuery(
				"select usr from UserInfo usr where usr.usrName =? ",
				new Object[] { usrName });
		if (objUsr != null && objUsr.size() > 0) {
			Object obj = objUsr.get(0);
			return (UserInfo) obj;
		}
		return null;
	}

	@Override
	public void update(UserInfo usr) throws Exception {
		if (!isEmpty(usr) && usr.getId() != null) {
			UserInfo usrInfo = (UserInfo) hdao.get(UserInfo.class, usr.getId());
			usrInfo.setCreateTime(usr.getCreateTime());
			usrInfo.setRemark(usr.getRemark());
			usrInfo.setUsrName(usr.getUsrName());
			usrInfo.setPart(usr.getPart());
			usrInfo.setPartid(usr.getPartid());
			usrInfo.setStatus(usr.getStatus());
			usrInfo.setRoles(usr.getRoles());
		} else {
			throw new IllegalArgumentException("修改的信息为空或者其编号为空");
		}
	}

	@Override
	public void updatePswd(String usrname, String password) throws Exception {
		System.out.println(usrname);
		if (!isEmpty(usrname)) {
			UserInfo usr = findUserByName(usrname);
			if (!isEmpty(usr)) {
				usr.setPassword(password);
			} else {
				throw new IllegalArgumentException("帐号信息不存在");
			}
		}
	}
}

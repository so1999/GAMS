package com.manager.sys.service.impl;

import static com.manager.sys.common.StringUtil.isEmpty;
import static com.manager.sys.common.StringUtil.isNum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.RoleInfo;
import com.manager.sys.service.RoleInfoService;

public class RoleInfoServiceImpl implements RoleInfoService {
	private HibernateDao hdao;

	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	@Override
	public List<Object> find(Integer roleId) throws Exception {
		StringBuilder queryString = new StringBuilder();
		List<Object> objs = new ArrayList<Object>();
		queryString.append(" from RoleInfo roleInfo");
		if (roleId != null) {
			queryString.append(" where roleInfo.roleId = ?");
			objs.add(roleId);
		}
		List<Object> list = hdao.findByQuery(queryString.toString(),
				objs.toArray());
		return list;
	}

	@Override
	public Map<String, Object> findByPage(Object obj, Integer page, Integer rows)
			throws Exception {
		List<Object> objList = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select ");
		queryString.append(" roleInfo from RoleInfo roleInfo ");
		if (!isEmpty(obj)) {
			queryString.append(" where roleInfo.roleName like ?");
			objList.add("%" + obj + "%");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		List<Object> roles = hdao.findPageByQuery(queryString.toString(),
				objList.toArray(), page, rows);
		if (roles != null) {
			for (int i = 0, j = roles.size(); i < j; i++) {
				RoleInfo role = (RoleInfo) roles.get(i);
				RoleInfo r = new RoleInfo();
				r.setId(role.getId());
				r.setRoleName(role.getRoleName());
				r.setRoleDesc(role.getRoleDesc());
				r.setCreateTimeStr(StringUtil.formate(role.getCreateTime()));
				r.setCreateUsr(role.getCreateUsr());
				list.add(r);
			}
		}
		map.put("total", hdao.getCount(RoleInfo.class, obj, "roleName"));
		map.put("rows", list);
		return map;
	}

	@Override
	public Serializable save(RoleInfo role) throws Exception {
		return hdao.saveOrUpdate(role);
	}

	@Override
	public RoleInfo get(Serializable id) throws Exception {
		return (RoleInfo) hdao.get(RoleInfo.class, id);
	}

	@Override
	public void delete(String[] ids) throws IllegalAccessException, Exception {
		if (null != ids && ids.length > 0) {
			for (String id : ids) {
				/* 1:管理员，2：普通用户 */
				if ("1".equals(id) || "2".equals(id)) {
					throw new IllegalAccessException("删除的数据中有不能删除的系统数据");
				} else {
					hdao.delete(RoleInfo.class, isNum(id) ? Integer.valueOf(id)
							: 0);
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getAllRoles() throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<Object> roleList = find(null);
		System.out.println("角色系想你：" + roleList);
		if (null != roleList && roleList.size() > 0) {
			for (Object roleObj : roleList) {
				RoleInfo role = (RoleInfo) roleObj;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", role.getId());
				map.put("roleName", role.getRoleName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}

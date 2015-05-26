package com.manager.sys.service.impl;

import static com.manager.sys.common.StringUtil.isEmpty;
import static com.manager.sys.common.StringUtil.isNum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.domain.FuncInfo;
import com.manager.sys.domain.Module;
import com.manager.sys.service.FuncService;

public class FuncServiceImpl implements FuncService {

	private HibernateDao hdao;

	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	/**
	 * 根据功能名称查询
	 */
	@Override
	public Map<String, Object> findByPage(Object objs, Integer page, Integer rows) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FuncInfo> funcList = new ArrayList<FuncInfo>();
		List<Object> params = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		queryString.append("select func from FuncInfo as func  ");
		queryString.append(" where 1 = 1 ");
		if (!isEmpty(objs)) {
			queryString.append(" and func.funcName like ? ");
			params.add("%" + objs + "%");
		}
		List<Object> objList = hdao.findPageByQuery(queryString.toString(), params.toArray(), page, rows);
		if (null != objList && objList.size() > 0) {
			for (Object obj : objList) {
				FuncInfo funcObj = (FuncInfo) obj;
				Module mod = funcObj.getModule();
				FuncInfo func = new FuncInfo();
				func.setModuleName(!isEmpty(mod) ? mod.getModuleName() : null);
				func.setId(funcObj.getId());
				func.setFuncName(funcObj.getFuncName());
				func.setFuncSign(funcObj.getFuncSign());
				func.setFuncDesc(funcObj.getFuncDesc());
				funcList.add(func);
			}
		}
		System.err.println(funcList);
		map.put("rows", funcList);
		map.put("total", hdao.getCount(FuncInfo.class, objs, "funcName"));
		return map;
	}

	@Override
	public void saveOrUpdate(FuncInfo func) throws Exception {
		hdao.saveOrUpdate(func);
	}

	@Override
	public void delete(String[] ids) throws Exception {
		if (!isEmpty(ids) && ids.length > 0) {
			for (String id : ids) {
				hdao.delete(FuncInfo.class, isNum(id) ? Integer.valueOf(id) : 0);
			}
		}
	}

	@Override
	public FuncInfo get(Integer id) {
		if (!isEmpty(id)) {
			return (FuncInfo) hdao.get(FuncInfo.class, id);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findFuncByModuleId(Integer moduleId) throws Exception {
		List<Map<String, Object>> funcList = new ArrayList<Map<String, Object>>();
		List<Object> funcObjs = hdao.findByQuery("select func from FuncInfo func join func.module as mods where mods.id = ?",
				new Object[] { moduleId });
		System.out.println("模块功能：" + funcObjs);
		if (null != funcObjs && funcObjs.size() > 0) {
			for (Object funcObj : funcObjs) {
				FuncInfo func = (FuncInfo) funcObj;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", func.getId());
				map.put("text", func.getFuncName());
				funcList.add(map);
			}
		}
		return funcList;
	}

	@Override
	public Integer findByFuncSign(String funcSign) {
		try {
			List<Object> funcs = hdao.findByQuery("select func.id from FuncInfo as func where func.funcSign = ? ",
					new Object[] { funcSign });
			if (null != funcs && funcs.size() > 0) {
				Integer id = (Integer) funcs.get(0);
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

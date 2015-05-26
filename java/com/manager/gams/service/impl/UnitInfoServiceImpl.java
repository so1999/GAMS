package com.manager.gams.service.impl;

import static com.manager.sys.common.StringUtil.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.manager.gams.domain.UnitInfo;
import com.manager.gams.service.UnitInfoService;
import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.domain.UserInfo;

import org.springframework.beans.BeanUtils;

public class UnitInfoServiceImpl implements UnitInfoService {
	private HibernateDao hdao;

	//查询和List
/*	@Override
	public Map<String, Object> findByPage(String queryString, Integer page,
			Integer rows) throws Exception {
		if (StringUtil.isEmpty(page)) {
			page = 1;
		}
		if (StringUtil.isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		List<Object> param = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append(" from UnitInfo ut where 1 = 1");
		if (!StringUtil.isEmpty(queryString)) {
			query.append(" and ut.utno like ? ");
			param.add("%" + queryString + "%");
		}
		List<Object> list = hdao.findPageByQuery(query.toString(),
				param.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", hdao.getCount(UnitInfo.class, queryString, "utno"));
		return map;
	}*/
	
	@Override
	public Map<String, Object> findByPage(String utname, String scon, Integer page, Integer rows)
			throws Exception {
		if (isEmpty(page)) {
			page = 1;
		}
		if (isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		List<Object> objs = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("select ut from UnitInfo ut where 1=1");
		if (!isEmpty(utname)) {
			query.append(" and ut.utname = ? ");
			objs.add(utname);
		}
		List<?> objList = hdao.findPageByQuery(query.toString(), objs.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		List<?> list = hdao.findByQuery(query.toString(), objs.toArray());
		map.put("rows", objList);
		map.put("total", !isEmpty(list) && list.size() > 0 ? list.size() : 0);
		return map;
	}

	@Override
	public void saveOrUpdate(UnitInfo ut) {
		hdao.saveOrUpdate(ut);
	}

	@Override
	public UnitInfo get(Integer id) {
		return (UnitInfo) hdao.get(UnitInfo.class, id);
	}

	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	//更新信息
	@Override
	public void update(UnitInfo ut) throws Exception {
		if (!isEmpty(ut) && ut.getId() != null) {
			UnitInfo unitInfo = (UnitInfo) hdao.get(UnitInfo.class, ut.getId());
			unitInfo.setUtstatus(ut.getUtstatus());

		} else {
			throw new IllegalArgumentException("修改的信息为空或者其编号为空");
		}
	}
	
	//更新信息
	@Override
	public void updatein(UnitInfo ut) throws Exception {
		if (!isEmpty(ut) && ut.getId() != null) {
			UnitInfo unitInfo = (UnitInfo) hdao.get(UnitInfo.class, ut.getId());
			unitInfo.setUtstatus(ut.getUtstatus());
			unitInfo.setUtplace(ut.getUtplace());
		} else {
			throw new IllegalArgumentException("修改的信息为空或者其编号为空");
		}
	}
	
	//删除记录
	@Override
	public void delete(String[] ids) throws Exception {
		if (!isEmpty(ids) && ids.length > 0) {
			for (String id : ids) {
				if (!isEmpty(id)) {
					hdao.delete(UnitInfo.class,
							!isEmpty(id) ? Integer.valueOf(id) : 0);
				}
			}
		}
	}
	
	//查询导出Excel
	@Override
	public List<UnitInfo> findById(Integer id) throws Exception {
		List<Object> objs = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("select ut from UnitInfo ut where 1 = 1");
		List<UnitInfo> utList = new ArrayList<UnitInfo>();
		List<?> objList = hdao.findByQuery(query.toString(), objs.toArray());
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				UnitInfo ut = (UnitInfo) obj;
				UnitInfo utd = new UnitInfo();
				BeanUtils.copyProperties(ut, utd);
				utList.add(utd);
			}
		}
		return utList;
	}

	//查询表中的最大Id
	@Override
	public Integer findTop() throws Exception {
		StringBuilder query = new StringBuilder();
		query.append(" select max(id) from UnitInfo ");
		List<?> objList = hdao.findByQuery(query.toString(), null);
		if (objList != null && objList.size() > 0) {
			Object obj = objList.get(0);
			Integer id = 0;
			if (obj != null) {
				try {
					id = Integer.valueOf(obj.toString());
				} catch (Exception e) {
					id = 0;
				}
			}
			return id;
		}
		return null;
	}
	
	//显示个人Unit
	@Override
	public Map<String, Object> findMyUnitByPage(HttpServletRequest req,
			String queryString, Integer page, Integer rows)
			throws Exception{
		if (isEmpty(page)) {
			page = 1;
		}
		if (isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		List<Object> objs = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		UserInfo usr = (UserInfo) req.getSession().getAttribute("usrInfo");
		String usrname = usr.getUsrName();
		query.append(" from UnitInfo ut where 1 = 1");
		query.append(" and ut.utcharger like ? ");
		objs.add(usrname);
		List<?> objList = hdao.findPageByQuery(query.toString(), objs.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		List<?> list = hdao.findByQuery(query.toString(), objs.toArray());
		map.put("rows", objList);
		map.put("total", !isEmpty(list) && list.size() > 0 ? list.size() : 0);
		return map;
	}
}

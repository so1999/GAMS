package com.manager.gams.service.impl;
import static com.manager.sys.common.StringUtil.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manager.gams.domain.PartInfo;
import com.manager.gams.service.PartInfoService;
import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.common.StringUtil;

public class PartInfoServiceImpl implements PartInfoService {

	private HibernateDao hdao;

	@Override
	public void saveOrUpdate(PartInfo part) {
		hdao.saveOrUpdate(part);
	}

	@Override
	public PartInfo get(Integer id) {
		return (PartInfo) hdao.get(PartInfo.class, id);
	}

	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	@Override
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
		query.append(" from PartInfo part  ");
		List<Object> list = hdao.findPageByQuery(query.toString(),
				param.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", hdao.getCount(PartInfo.class, null, null));
		return map;
	}

	@Override
	public void delete(String[] ids) throws Exception {
		if (!isEmpty(ids) && ids.length > 0) {
			for (String id : ids) {
				if (!isEmpty(id)) {
					hdao.delete(PartInfo.class,
							!isEmpty(id) ? Integer.valueOf(id) : 0);
				}
			}
		}
	}

	@Override
	public List<PartInfo> findPartList() throws Exception {
		List<PartInfo> list = new ArrayList<PartInfo>();
		List<Object> objList = hdao.findByQuery(
				" select allpart from PartInfo allpart order by part asc", null);
		PartInfo pjObj = new PartInfo();
		pjObj.setId(null);
		list.add(pjObj);
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				PartInfo pj = (PartInfo) obj;
				list.add(pj);
			}
		}
		return list;
	}
	

	@Override
	public List<PartInfo> findPartidList(String part) throws Exception {
		List<PartInfo> list = new ArrayList<PartInfo>();
		List<Object> objList = hdao.findByQuery(
				" from PartInfo where PART=?",new Object[] {part});
		PartInfo pjObj = new PartInfo();
		pjObj.setId(null);
		list.add(pjObj);
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				PartInfo pj = (PartInfo) obj;
				list.add(pj);
			}
		}
		return list;
	}
	
}

package com.manager.gams.service.impl;

import static com.manager.sys.common.StringUtil.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.manager.gams.domain.PcInfo;
import com.manager.gams.service.PcInfoService;
import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.UserInfo;

import org.springframework.beans.BeanUtils;

public class PcInfoServiceImpl implements PcInfoService {
	private HibernateDao hdao;

	// 查询和List
	@Override
	public Map<String, Object> findByPage(String queryString,
			String queryString2, Integer page, Integer rows)
			throws Exception {
		if (StringUtil.isEmpty(page)) {
			page = 1;
		}
		if (StringUtil.isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		List<Object> param = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append(" from PcInfo pc where 1 = 1");
		if (!StringUtil.isEmpty(queryString2)) {
			query.append(" and pc.pcno like ? ");
			param.add("%" + queryString2 + "%");
		}
		if (!StringUtil.isEmpty(queryString)) {
			query.append(" and pc.pcno like ? ");
			param.add("%" + queryString + "%");
			query.append(" or pc.pcmodel like ? ");
			param.add("%" + queryString + "%");
			query.append(" or pc.pcsn like ? ");
			param.add("%" + queryString + "%");
			query.append(" or pc.pccharger like ? ");
			param.add("%" + queryString + "%");
		}
		List<Object> list = hdao.findPageByQuery(query.toString(),
				param.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", hdao.getCount(PcInfo.class, queryString,
				"pcno"));
		return map;
	}

	@Override
	public void saveOrUpdate(PcInfo pc) {
		hdao.saveOrUpdate(pc);
	}

	@Override
	public PcInfo get(Integer id) {
		return (PcInfo) hdao.get(PcInfo.class, id);
	}

	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	// 编辑
	public void update(PcInfo pc) throws Exception {
		if (!isEmpty(pc)) {
			PcInfo pcInfo = (PcInfo) hdao.get(PcInfo.class,
					pc.getId());
			pcInfo.setPcuse(pc.getPcuse());
			pcInfo.setPcplace(pc.getPcplace());
			pcInfo.setPcremark(pc.getPcremark());
		} else {
			throw new IllegalArgumentException("修改的信息为空或者其编号为空");
		}
	}

	// 更新借出信息
	@Override
	public void updateout(PcInfo pc) throws Exception {
		if (!isEmpty(pc)) {
			PcInfo pcInfo = (PcInfo) hdao.get(PcInfo.class,
					pc.getId());
			pcInfo.setPcstatus(pc.getPcstatus());
		} else {
			throw new IllegalArgumentException("修改的信息为空或者其编号为空");
		}
	}

	// 更新归还信息
	@Override
	public void updatein(PcInfo pc) throws Exception {
		if (!isEmpty(pc)) {
			PcInfo pcInfo = (PcInfo) hdao.get(PcInfo.class,
					pc.getId());
			pcInfo.setPcstatus(pc.getPcstatus());
			pcInfo.setPcplace(pc.getPcplace());
		} else {
			throw new IllegalArgumentException("修改的信息为空或者其编号为空");
		}
	}

	// 删除记录
	@Override
	public void delete(String[] ids) throws Exception {
		if (!isEmpty(ids) && ids.length > 0) {
			for (String id : ids) {
				if (!isEmpty(id)) {
					hdao.delete(PcInfo.class,
							!isEmpty(id) ? Integer
									.valueOf(id)
									: 0);
				}
			}
		}
	}

	// 查询导出Excel
	@Override
	public List<PcInfo> findById(Integer id) throws Exception {
		List<Object> objs = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("select pc from PcInfo pc where 1 = 1");
		List<PcInfo> pcList = new ArrayList<PcInfo>();
		List<?> objList = hdao.findByQuery(query.toString(),
				objs.toArray());
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				PcInfo pc = (PcInfo) obj;
				PcInfo pcd = new PcInfo();
				BeanUtils.copyProperties(pc, pcd);
				pcList.add(pcd);
			}
		}
		return pcList;
	}

	// 查询表中的最大Id
	@Override
	public Integer findTop() throws Exception {
		StringBuilder query = new StringBuilder();
		query.append(" select max(id) from PcInfo ");
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

	@Override
	public List<PcInfo> findModelList(String pcmodel) throws Exception {
		List<PcInfo> list = new ArrayList<PcInfo>();
		List<Object> objList = hdao.findByQuery(
				" from PcInfo where pcmodel=?",
				new Object[] { pcmodel });
		PcInfo pcObj = new PcInfo();
		pcObj.setId(null);
		list.add(pcObj);
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				PcInfo pc = (PcInfo) obj;
				list.add(pc);
				System.out.println(list.get(1).getPcno());
			}
		}
		return list;

	}

	@Override
	public Map<String, Object> findMyPcByPage(HttpServletRequest req,
			String queryString, Integer page, Integer rows)
			throws Exception {
		if (StringUtil.isEmpty(page)) {
			page = 1;
		}
		if (StringUtil.isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		List<Object> param = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		UserInfo usr = (UserInfo) req.getSession().getAttribute(
				"usrInfo");
		String usrname = usr.getUsrName();
		query.append(" from PcInfo pc where 1 = 1");
		query.append(" and pc.pccharger like ? ");
		param.add(usrname);
		List<Object> list = hdao.findPageByQuery(query.toString(),
				param.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", hdao.getCount(PcInfo.class, queryString,
				"pcno"));
		return map;
	}
}

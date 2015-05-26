package com.manager.gams.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manager.gams.domain.BorrowInfo;
import com.manager.gams.domain.BorrowUnitInfo;
import com.manager.gams.domain.PcInfo;
import com.manager.gams.domain.UnitInfo;
import com.manager.gams.service.BorrowInfoService;
import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.common.StringUtil;


public class BorrowInfoServiceImpl implements BorrowInfoService {
	private HibernateDao hdao;
	//分页显示PC借用归还记录
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
		query.append(" from BorrowInfo borr where 1 = 1");
		if (!StringUtil.isEmpty(queryString)) {
			query.append(" and borr.iopcno like ? ");
			param.add("%" + queryString + "%");
			query.append(" or borr.iopcmodel like ? ");
			param.add("%" + queryString + "%");
			query.append(" or borr.iopcsn like ? ");
			param.add("%" + queryString + "%");
		}
		List<Object> list = hdao.findPageByQuery(query.toString(),
				param.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", hdao.getCount(BorrowInfo.class, queryString, "iopcno"));
		return map;
	}
	//分页显示Unit借用归还记录
	@Override
	public Map<String, Object> findUnitByPage(String queryString, Integer page,
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
		query.append(" from BorrowUnitInfo borrunit where 1 = 1");
		if (!StringUtil.isEmpty(queryString)) {
			query.append(" and borrunit.ioutno like ? ");
			param.add("%" + queryString + "%");
		}
		List<Object> list = hdao.findPageByQuery(query.toString(),
				param.toArray(), page, rows);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", hdao.getCount(BorrowUnitInfo.class, queryString, "ioutno"));
		return map;
	}
	
	@Override
	public void saveOrUpdate(BorrowInfo borr) {
		hdao.saveOrUpdate(borr);
	}

	@Override
	public void saveOrUpdate(BorrowUnitInfo borrunit) {
		hdao.saveOrUpdate(borrunit);
	}
	@Override
	public BorrowInfo get(Integer bid) {
		return (BorrowInfo) hdao.get(BorrowInfo.class, bid);
	}

	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}
	
	@Override
	public List<PcInfo> findModelListByNo(String pcno) throws Exception {
		List<PcInfo> list = new ArrayList<PcInfo>();
		List<Object> objList = hdao.findByQuery(
				" from PcInfo where pcno=?",new Object[] {pcno});
		PcInfo pcObj = new PcInfo();
		pcObj.setId(null);
		list.add(pcObj);
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				PcInfo pc = (PcInfo) obj;
				list.add(pc);
			}
		}
		return list;
		
	}
	
	@Override
	public List<UnitInfo> findUnitListByNo(String utno) throws Exception {
		List<UnitInfo> list = new ArrayList<UnitInfo>();
		List<Object> objList = hdao.findByQuery(
				" from UnitInfo where utno=?",new Object[] {utno});
		UnitInfo utObj = new UnitInfo();
		utObj.setId(null);
		list.add(utObj);
		if (objList != null && objList.size() > 0) {
			for (Object obj : objList) {
				UnitInfo ut = (UnitInfo) obj;
				list.add(ut);
			}
		}
		return list;
		
	}	
	//分页显示Unit借用归还记录
	@Override
	public Map<String, Object> findTotal(String queryString, String queryString2 ) throws Exception {
		//HW统计
		queryString2 = "在库" ;
		queryString ="HW" ;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hwtotalin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long hwtotalin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "外部借入" ;
		map.put("hwtotalbin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long hwtotalbin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "内部借出" ;
		map.put("hwtotalbiout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long hwtotalbiout = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus");
		queryString2 = "外部借出" ;
		map.put("hwtotalbout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		queryString2 = "报废" ;
		map.put("hwtotalscr", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long	hwtotalis = hwtotalin +  hwtotalbin + hwtotalbiout;
		map.put("hwtotalis", hwtotalis);
		
		//SW统计
		queryString2 = "在库" ;
		queryString ="SW" ;
		map.put("swtotalin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long swtotalin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "外部借入" ;
		map.put("swtotalbin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long swtotalbin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "内部借出" ;
		map.put("swtotalbiout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long swtotalbiout = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus");
		queryString2 = "外部借出" ;
		map.put("swtotalbout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		queryString2 = "报废" ;
		map.put("swtotalscr", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long	swtotalis = swtotalin + swtotalbin + swtotalbiout;
		map.put("swtotalis", swtotalis);
		
		//CFM统计
		queryString2 = "在库" ;
		queryString ="CFM" ;
		map.put("cfmtotalin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long cfmtotalin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "外部借入" ;
		map.put("cfmtotalbin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long cfmtotalbin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "内部借出" ;
		map.put("cfmtotalbiout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long cfmtotalbiout = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus");
		queryString2 = "外部借出" ;
		map.put("cfmtotalbout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		queryString2 = "报废" ;
		map.put("cfmtotalscr", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long	cfmtotalis = cfmtotalin +  cfmtotalbin + cfmtotalbiout;
		map.put("cfmtotalis", cfmtotalis);
		
		//QRE统计
		queryString2 = "在库" ;
		queryString ="QRE" ;
		map.put("qretotalin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long qretotalin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "外部借入" ;
		map.put("qretotalbin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long qretotalbin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "内部借出" ;
		map.put("qretotalbiout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long qretotalbiout = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus");
		queryString2 = "外部借出" ;
		map.put("qretotalbout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		queryString2 = "报废" ;
		map.put("qretotalscr", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long	qretotalis = qretotalin +  qretotalbin + qretotalbiout;
		map.put("qretotalis", qretotalis);
		
		//ME统计
		queryString2 = "在库" ;
		queryString ="ME" ;
		map.put("metotalin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long metotalin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "外部借入" ;
		map.put("metotalbin", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long metotalbin = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus") ;
		queryString2 = "内部借出" ;
		map.put("metotalbiout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long metotalbiout = hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus");
		queryString2 = "外部借出" ;
		map.put("metotalbout", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		queryString2 = "报废" ;
		map.put("metotalscr", hdao.getCount2(PcInfo.class, queryString, "pcno" ,queryString2, "pcstatus"));
		long	metotalis = metotalin +  metotalbin + metotalbiout;
		map.put("metotalis", metotalis);
		
		return map;
	}
}

package com.manager.gams.service;

import java.util.List;
import java.util.Map;

import com.manager.gams.domain.BorrowInfo;
import com.manager.gams.domain.BorrowUnitInfo;
import com.manager.gams.domain.PcInfo;
import com.manager.gams.domain.UnitInfo;

public interface BorrowInfoService {
	
	public void saveOrUpdate(BorrowInfo borr);
	
	public void saveOrUpdate(BorrowUnitInfo borrunit);

	public Map<String, Object> findByPage(String queryString,Integer page, Integer rows) throws Exception;

	public BorrowInfo get(Integer bid);
	
	public List<PcInfo> findModelListByNo(String string ) throws Exception;

	public Map<String, Object> findUnitByPage(String queryString,Integer page, Integer rows) throws Exception;

	public List<UnitInfo> findUnitListByNo(String string) throws Exception;

	public Map<String, Object> findTotal(String queryString, String queryString2) throws Exception;

}

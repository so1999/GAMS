package com.manager.gams.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.manager.gams.domain.UnitInfo;

public interface UnitInfoService {

	// public Map<String, Object> findByPage(String queryString,Integer
	// page, Integer rows) throws Exception;

	public void saveOrUpdate(UnitInfo ut);

	public void update(UnitInfo ut) throws Exception;

	public UnitInfo get(Integer id);

	public void delete(String[] ids) throws Exception;

	public List<UnitInfo> findById(Integer id) throws Exception;

	public Integer findTop() throws Exception;

	public Map<String, Object> findByPage(String utname, String scon,
			Integer page, Integer rows) throws Exception;

	public Map<String, Object> findMyUnitByPage(HttpServletRequest req,
			String queryString, Integer page, Integer rows)
			throws Exception;

	public void updatein(UnitInfo ut) throws Exception;
}

package com.manager.gams.service;

import java.util.List;
import java.util.Map;

import com.manager.gams.domain.PartInfo;

public interface PartInfoService {
	
	public void saveOrUpdate(PartInfo part);

	public void delete(String[] ids) throws Exception;

	public Map<String, Object> findByPage(String queryString, Integer page,
			Integer rows) throws Exception;

	public PartInfo get(Integer id);

	public List<PartInfo> findPartList() throws Exception;

	public List<PartInfo> findPartidList(String string) throws Exception;

}

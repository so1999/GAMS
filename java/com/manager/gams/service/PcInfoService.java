package com.manager.gams.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.manager.gams.domain.PcInfo;

public interface PcInfoService {

	public Map<String, Object> findByPage(String queryString,String queryString2 ,Integer page, Integer rows) throws Exception;

	public void saveOrUpdate(PcInfo pc);

	public void update(PcInfo pc) throws Exception;

	public PcInfo get(Integer id);
	
	public void delete(String[] ids) throws Exception;
	
	public List<PcInfo> findById(Integer id) throws Exception;
	
	public Integer findTop() throws Exception;
	
	public List<PcInfo> findModelList(String string ) throws Exception;

	public Map<String, Object> findMyPcByPage(HttpServletRequest req,
			String queryString, Integer page, Integer rows)
			throws Exception;

	public void updatein(PcInfo pc) throws Exception;

	public void updateout(PcInfo pc) throws Exception;

}

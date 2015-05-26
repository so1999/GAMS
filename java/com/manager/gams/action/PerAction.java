package com.manager.gams.action;


import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.gams.service.PcInfoService;
import com.manager.gams.service.UnitInfoService;
import com.manager.sys.common.StringUtil;

@Controller
@RequestMapping(value = "/per")
public class PerAction {
	private PcInfoService pcInfoService;
	private UnitInfoService unitInfoService;
	private Map<String, Object> map = new HashMap<String, Object>();

	@RequestMapping(value = "/listpc")
	public String listpc() {
		return "activeUsr/listpc";
	}
	
	@RequestMapping(value = "/listunit")
	public String listunit() {
		return "activeUsr/listunit";
	}

	@RequestMapping(value = "/listMyPc")
	@ResponseBody
	public String listMyPc(HttpServletRequest req,String queryString, Integer page, Integer rows)
			throws Exception {
		map = pcInfoService.findMyPcByPage(req ,queryString, page, rows);
		return StringUtil.toJson(map);
	}
	
	@RequestMapping(value = "/listMyUnit")
	@ResponseBody
	public String listMyUnit(HttpServletRequest req,String queryString , Integer page, Integer rows)
			throws Exception {
		map = unitInfoService.findMyUnitByPage(req ,queryString , page, rows);
		return StringUtil.toJson(map);
	}

	public void setPcInfoService(PcInfoService pcInfoService) {
		this.pcInfoService = pcInfoService;
	}
	
	public void setUnitInfoService(UnitInfoService unitInfoService) {
		this.unitInfoService = unitInfoService;
	}

}

package com.manager.gams.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.gams.domain.UnitInfo;
import com.manager.gams.service.UnitInfoService;
import com.manager.sys.common.ExcelUtil;
import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.UserInfo;
import static com.manager.sys.common.StringUtil.toJson;
import static com.manager.sys.common.StringUtil.isEmpty;

@Controller
@RequestMapping(value = "/unit")
public class UnitAction {
	private UnitInfoService unitInfoService;
	private Map<String, Object> map = new HashMap<String, Object>();
	private String opt;

	@RequestMapping(value = "/add")
	public String add() {
		return "unit/add";
	}

	@RequestMapping(value = "/list")
	public String list() {
		return "unit/list";
	}

	@RequestMapping(value = "/listUnit")
	@ResponseBody
	public String listUnit(String utname, String scon, Integer page, Integer rows)
			throws Exception {
		map = unitInfoService.findByPage(utname,scon, page, rows);
		return StringUtil.toJson(map);
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest req, UnitInfo ut) {
		try {
			// 设置登录日期
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			ut.setUtdate(df.format(new Date()));
			UserInfo usr = (UserInfo) req.getSession().getAttribute("usrInfo");

			// 保存登录用户
			String usrname = usr.getUsrName();
			ut.setUtcharger(usrname);
			unitInfoService.saveOrUpdate(ut);
			
			// 生成资财编号
			/**
			 * E：CPU
			 * B: HDD
			 * C: MEMORY
			 * D: MB
			 * */
			String op = ut.getUtname();
			if(op.equals("CPU")){
				this.opt = "E" ;
			}else if(op.equals("HDD")){
				this.opt = "B" ;
			}else if(op.equals("MEMORY")){
				this.opt = "C" ;
			}else if(op.equals("MB")){
				this.opt = "D" ;
			}else if(op.equals("OTHER")){
				this.opt = "X" ;
			}
			String partid = usr.getPartid();
			Integer topId = unitInfoService.findTop();
			if (topId != null) {
				topId += 1;
			}
			DecimalFormat idf = new DecimalFormat("000000");
			String utid = idf.format(topId);
			String utno = partid + opt + utid;
			ut.setUtno(utno);
			unitInfoService.saveOrUpdate(ut);
			
			map.put("success", true);
			map.put("msg", "信息提交成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}


	
	public void setUnitInfoService(UnitInfoService unitInfoService) {
		this.unitInfoService = unitInfoService;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest req, String ids) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!isEmpty(ids)) {
			String[] id = ids.split(",");
			if (!isEmpty(id) && id.length > 0) {
				try {
					unitInfoService.delete(id);
					map.put("success", true);
					map.put("msg", "信息删除成功");
				} catch (Exception e) {
					map.put("success", false);
					map.put("msg", "提示:" + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			map.put("success", false);
			map.put("msg", "请选择删除的信息");
		}
		return toJson(map);
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest req, UnitInfo ut)
			throws IOException {

		try {
			if (!isEmpty(ut.getId())) {
				ut.setUtuse(ut.getUtuse());
				ut.setUtplace(ut.getUtplace());
				ut.setUtremark(ut.getUtremark());
				unitInfoService.update(ut);
				map.put("success", true);
				map.put("msg", "信息保存成功");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);

	}

	@RequestMapping(value = "/edit/{Id}")
	public String edit(HttpServletRequest req, @PathVariable("Id") Integer Id)
			throws Exception {
		UnitInfo unitInfo = unitInfoService.get(Id);
		req.setAttribute("ut", unitInfo); // 可能会有问题
		return "unit/edit";
	}

	@RequestMapping(value = "/dwUnitXls")
	@ResponseBody
	public String downloadXls(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		OutputStream os = resp.getOutputStream();
		DateFormat dc = new SimpleDateFormat("yyyy-MM-dd");
		resp.setHeader("Content-Disposition", "attachment;filename=UnitInfo("
				+ dc.format(new Date()) + ").xls");
		resp.setCharacterEncoding("UTF-8");
		List<String> columns = new ArrayList<String>();
		columns.add("登录日期");
		columns.add("资财编号");
		columns.add("名称");
		columns.add("规格");
		columns.add("用途");
		columns.add("Place");
		columns.add("QT'Y");
		columns.add("状态");
		columns.add("Remark");
		columns.add("Charger");
		List<Object[]> data = new ArrayList<Object[]>();
		List<UnitInfo> unitList = unitInfoService.findById(null);
		if (unitList != null && unitList.size() > 0) {
			for (UnitInfo ut : unitList) {
				Object[] obj = { ut.getUtdate(), ut.getUtno(), ut.getUtname(),
						ut.getUtspec(), ut.getUtuse(), ut.getUtplace(),
						ut.getUtqty(), ut.getUtstatus(), ut.getUtremark(),
						ut.getUtcharger() };
				data.add(obj);
			}
		}
		ExcelUtil.createExcel(null, data, columns, os);
		return null;
	}
}

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

import com.manager.gams.domain.PcInfo;
import com.manager.gams.service.PcInfoService;
import com.manager.sys.common.ExcelUtil;
import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.UserInfo;

import static com.manager.sys.common.StringUtil.toJson;
import static com.manager.sys.common.StringUtil.isEmpty;

@Controller
@RequestMapping(value = "/pc")
public class PcAction {
	private PcInfoService pcInfoService;
	private Map<String, Object> map = new HashMap<String, Object>();

	@RequestMapping(value = "/stis")
	public String stis() {
		return "pc/stis";
	}
	
	@RequestMapping(value = "/add")
	public String add() {
		return "pc/add";
	}

	@RequestMapping(value = "/list")
	public String list() {
		return "pc/list";
	}

	@RequestMapping(value = "/listPc")
	@ResponseBody
	public String listPc(String queryString,String queryString2, Integer page, Integer rows)
			throws Exception {
		map = pcInfoService.findByPage(queryString,queryString2, page, rows);
		return StringUtil.toJson(map);
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest req, PcInfo pc) {
		try {
			// 设置登录日期
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			pc.setPcdate(df.format(new Date()));
			UserInfo usr = (UserInfo) req.getSession()
					.getAttribute("usrInfo");

			// 保存登录用户
			String usrname = usr.getUsrName();
			pc.setPccharger(usrname);
			pcInfoService.saveOrUpdate(pc);

			// 生成资财编号并更新
			String partid = usr.getPartid();
			Integer topId = pcInfoService.findTop();
			if (topId != null) {
				topId += 1;
			}
			DecimalFormat idf = new DecimalFormat("000000");
			String pcid = idf.format(topId);
			String pcno = partid + "A" + pcid;
			pc.setPcno(pcno);
			System.out.println(pc.getId());
			pcInfoService.saveOrUpdate(pc);

			map.put("success", true);
			map.put("msg", "信息提交成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}

	public void setPcInfoService(PcInfoService pcInfoService) {
		this.pcInfoService = pcInfoService;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest req, String ids)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!isEmpty(ids)) {
			String[] id = ids.split(",");
			if (!isEmpty(id) && id.length > 0) {
				try {
					pcInfoService.delete(id);
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
	public String update(HttpServletRequest req, PcInfo pc)
			throws IOException {
		try {
			if (!isEmpty(pc.getId())) {
				pcInfoService.update(pc);
				map.put("success", true);
				map.put("msg", "信息保存成功");
			} else {
				map.put("success", false);
				map.put("msg", "Null");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);

	}

	@RequestMapping(value = "/edit/{Id}")
	public String edit(HttpServletRequest req,
			@PathVariable("Id") Integer Id) throws Exception {
		PcInfo pcInfo = pcInfoService.get(Id);
		req.setAttribute("pc", pcInfo);
		return "pc/edit";
	}
	//PC借出
	@RequestMapping(value = "/addout/{Id}")
	public String addout(HttpServletRequest req,
			@PathVariable("Id") Integer Id) throws Exception {
		PcInfo pcInfo = pcInfoService.get(Id);
		try {
			if (!pcInfo.getPcstatus().equals("在库")) {
				map.put("success", false);
				map.put("msg", "不能借出");
				return "borrow/list";		
			} else {
				req.setAttribute("pc", pcInfo);
				return "borrow/addout";
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}

	@RequestMapping(value = "/dwPcXls")
	@ResponseBody
	public String downloadXls(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		OutputStream os = resp.getOutputStream();
		DateFormat dc = new SimpleDateFormat("yyyy-MM-dd");
		resp.setHeader("Content-Disposition",
				"attachment;filename=PCInfo("
						+ dc.format(new Date())
						+ ").xls");
		resp.setCharacterEncoding("UTF-8");
		List<String> columns = new ArrayList<String>();
		columns.add("登录日期");
		columns.add("资财编号");
		columns.add("MODEL");
		columns.add("S/N");
		columns.add("CPU");
		columns.add("Memory");
		columns.add("HDD");
		columns.add("用途");
		columns.add("QT'Y");
		columns.add("Place");
		columns.add("状态");
		columns.add("Remark");
		columns.add("Charger");
		List<Object[]> data = new ArrayList<Object[]>();
		List<PcInfo> pcList = pcInfoService.findById(null);
		if (pcList != null && pcList.size() > 0) {
			for (PcInfo pc : pcList) {
				Object[] obj = { pc.getPcdate(), pc.getPcno(),
						pc.getPcmodel(), pc.getPcsn(),
						pc.getPccpu(),
						pc.getPcmemory(),
						pc.getPchdd(), pc.getPcuse(),
						pc.getPcqty(), pc.getPcplace(),
						pc.getPcstatus(),
						pc.getPcremark(),
						pc.getPccharger() };
				data.add(obj);
			}
		}
		ExcelUtil.createExcel(null, data, columns, os);
		return null;
	}

	@RequestMapping(value = "/getModel")
	@ResponseBody
	public String getModel(String pcmodel) throws Exception {

		List<PcInfo> list = pcInfoService.findModelList(pcmodel);
		return toJson(list);
	}

	// 分部门显示
	@RequestMapping(value = "/listhw")
	public String listhw() {
		return "pc/listhw";
	}
	@RequestMapping(value = "/listhwPc")
	@ResponseBody
	public String listhwPc(String queryString, Integer page, Integer rows)
			throws Exception {
		String queryString2 = "HW" ;
		map = pcInfoService.findByPage(queryString,queryString2, page, rows);
		return StringUtil.toJson(map);
	}
	
	@RequestMapping(value = "/listsw")
	public String listsw() {
		return "pc/listsw";
	}
	@RequestMapping(value = "/listswPc")
	@ResponseBody
	public String listswPc(String queryString, Integer page, Integer rows)
			throws Exception {
		String queryString2 = "SW" ;
		map = pcInfoService.findByPage(queryString,queryString2, page, rows);
		return StringUtil.toJson(map);
	}
	
	@RequestMapping(value = "/listqre")
	public String listqre() {
		return "pc/listqre";
	}
	@RequestMapping(value = "/listqrePc")
	@ResponseBody
	public String listqrePc(String queryString, Integer page, Integer rows)
			throws Exception {
		String queryString2 = "QRE" ;
		map = pcInfoService.findByPage(queryString,queryString2, page, rows);
		return StringUtil.toJson(map);
	}
	
	@RequestMapping(value = "/listcfm")
	public String listcfm() {
		return "pc/listcfm";
	}
	@RequestMapping(value = "/listcfmPc")
	@ResponseBody
	public String listcfmPc(String queryString, Integer page, Integer rows)
			throws Exception {
		String queryString2 = "CFM" ;
		map = pcInfoService.findByPage(queryString,queryString2, page, rows);
		return StringUtil.toJson(map);
	}
	
	@RequestMapping(value = "/listme")
	public String listme() {
		return "pc/listme";
	}
	@RequestMapping(value = "/listmePc")
	@ResponseBody
	public String listmePc(String queryString, Integer page, Integer rows)
			throws Exception {
		String queryString2 = "ME" ;
		map = pcInfoService.findByPage(queryString,queryString2, page, rows);
		return StringUtil.toJson(map);
	}
	
	//分部门添加
	@RequestMapping(value = "/addhw")
	public String addhw() {
		return "pc/addhw";
	}
	@RequestMapping(value = "/addsw")
	public String addsw() {
		return "pc/addsw";
	}
	@RequestMapping(value = "/addqre")
	public String addqre() {
		return "pc/addqre";
	}
	@RequestMapping(value = "/addcfm")
	public String addcfm() {
		return "pc/addcfm";
	}
	@RequestMapping(value = "/addme")
	public String addme() {
		return "pc/addme";
	}
}

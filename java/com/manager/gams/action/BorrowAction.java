package com.manager.gams.action;

import static com.manager.sys.common.StringUtil.isEmpty;
import static com.manager.sys.common.StringUtil.toJson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.gams.domain.BorrowInfo;
import com.manager.gams.domain.BorrowUnitInfo;
import com.manager.gams.domain.PcInfo;
import com.manager.gams.domain.UnitInfo;
import com.manager.gams.service.BorrowInfoService;
import com.manager.gams.service.PcInfoService;
import com.manager.gams.service.UnitInfoService;
import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.UserInfo;

/**
 * @author Cheng
 */
@Controller
@RequestMapping(value = "/borr")
public class BorrowAction {
	private BorrowInfoService borrService;
	private PcInfoService pcInfoService;
	private UnitInfoService unitInfoService;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	// 左边显示借出
	@RequestMapping(value = "/addout2")
	public String addout2() {
		return "borrow/addout2";
	}

	// 显示所有PC借出归还记录
	@RequestMapping(value = "/list")
	public String index() {
		return "borrow/list";
	}

	@RequestMapping(value = "/listBorr")
	@ResponseBody
	public String listRew(String queryString, Integer page, Integer rows)
			throws Exception {
		map = borrService.findByPage(queryString, page, rows);
		return StringUtil.toJson(map);
	}

	// 保存借出PC信息
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest req, BorrowInfo borr, PcInfo pc) {
		try {
			DateFormat df = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss ");
			UserInfo usr = (UserInfo) req.getSession()
					.getAttribute("usrInfo");
			String usrname = usr.getUsrName();
			borr.setIoopperson(usrname);
			borr.setIoopdate(df.format(new Date()));
			borr.setIou("借出");
			borrService.saveOrUpdate(borr);
			pc.setPcstatus(borr.getIopcstatus());
			System.out.println(pc.getPcstatus());
			pcInfoService.updateout(pc);
			map.put("success", true);
			map.put("msg", "借用信息提交成功");

		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}

	// 归还PC
	@RequestMapping(value = "/addin")
	public String addin() {
		return "borrow/addin";
	}

	// 保存归还PC信息
	@RequestMapping(value = "/savein")
	@ResponseBody
	public String savein(HttpServletRequest req, BorrowInfo borr, PcInfo pc) {
		try {
			if (!isEmpty(pc.getId())) {
				borr.setIou("归还");
				DateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss ");
				UserInfo usr = (UserInfo) req.getSession()
						.getAttribute("usrInfo");
				String usrname = usr.getUsrName();
				borr.setIoopperson(usrname);
				borr.setIoopdate(df.format(new Date()));
				borrService.saveOrUpdate(borr);
				pc.setId(pc.getId());
				System.out.println(pc.getId());
				System.out.println(borr.getIoperson());

				pc.setPcstatus("在库");
				pc.setPcplace(borr.getIopcplace());
				pcInfoService.updatein(pc);
				map.put("success", true);
				map.put("msg", "归还信息提交成功");
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

	String pcno;

	// 根据资财编号获取要归还的PC信息
	@RequestMapping(value = "/getModel")
	@ResponseBody
	public String getModel(String pcno) throws Exception {
		List<PcInfo> list = borrService.findModelListByNo(pcno);
		return toJson(list);
	}

	// 以下方法根据资财编号显示一台PC借出归还记录
	@RequestMapping(value = "/detailHdm/{iopcno}")
	public String detailHdm(Model model,
			@PathVariable("iopcno") String iopcno) {
		pcno = iopcno;
		return "borrow/listPcDetail";
	}

	@RequestMapping(value = "/listPcBorr")
	@ResponseBody
	public String listPcDetail(HttpServletRequest req, Integer page,
			Integer rows) throws Exception {
		map = borrService.findByPage(pcno, page, rows);
		return StringUtil.toJson(map);
	}

	// 没有以下方法会导致空指针异常
	public void setBorrService(BorrowInfoService borrService) {
		this.borrService = borrService;
	}

	public void setPcInfoService(PcInfoService pcInfoService) {
		this.pcInfoService = pcInfoService;
	}

	public void setUnitInfoService(UnitInfoService unitInfoService) {
		this.unitInfoService = unitInfoService;
	}

	/**
	 * 以下为借用Unit操作
	 */
	// 显示所有Unit借出归还记录
	@RequestMapping(value = "/listunit")
	public String index1() {
		return "borrow/listunit";
	}

	@RequestMapping(value = "/listBorrUnit")
	@ResponseBody
	public String listBorrUnit(String queryString, Integer page,
			Integer rows) throws Exception {
		map = borrService.findUnitByPage(queryString, page, rows);
		return StringUtil.toJson(map);
	}

	// 以下方法根据资财编号显示单个Unit借出归还记录
	String utno;

	@RequestMapping(value = "/detailBorrUnit/{ioutno}")
	public String detailBorrUnit(Model model,
			@PathVariable("ioutno") String ioutno) {
		utno = ioutno;
		return "borrow/listUnitDetail";
	}

	@RequestMapping(value = "/listUnitBorr")
	@ResponseBody
	public String listUnitDetail(HttpServletRequest req, Integer page,
			Integer rows) throws Exception {
		map = borrService.findUnitByPage(utno, page, rows);
		return StringUtil.toJson(map);
	}
	// 左边显示借出
	@RequestMapping(value = "/addunitout2")
	public String addunitout2() {
		return "borrow/addunitout2";
	}

	// 借出Unit
	@RequestMapping(value = "/addunitout/{Id}")
	public String addout(HttpServletRequest req,
			@PathVariable("Id") Integer Id) throws Exception {
		UnitInfo unitInfo = unitInfoService.get(Id);
		try {
			if (!unitInfo.getUtstatus().equals("在库")) {
				map.put("success", false);
				map.put("msg", "不能借出");
				return "borrow/listunitt";
			} else {
				req.setAttribute("ut", unitInfo);
				return "borrow/addunitout";
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}

	// 保存借出PC信息
	@RequestMapping(value = "/saveborrunit")
	@ResponseBody
	public String saveborrunit(HttpServletRequest req,
			BorrowUnitInfo borrunit, UnitInfo ut) {
		try {
			DateFormat df = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss ");
			UserInfo usr = (UserInfo) req.getSession()
					.getAttribute("usrInfo");
			String usrname = usr.getUsrName();
			borrunit.setIoopperson(usrname);
			borrunit.setIoopdate(df.format(new Date()));
			borrunit.setIou("借出");
			borrService.saveOrUpdate(borrunit);
			ut.setUtstatus(borrunit.getIoutstatus());
			System.out.println(ut.getUtstatus());
			unitInfoService.update(ut);
			map.put("success", true);
			map.put("msg", "借用信息提交成功");

		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "提示:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}

	// 归还PC
	@RequestMapping(value = "/addunitin")
	public String addunitin() {
		return "borrow/addunitin";
	}

	// 保存归还PC信息
	@RequestMapping(value = "/saveunitin")
	@ResponseBody
	public String saveunitin(HttpServletRequest req,
			BorrowUnitInfo borrunit, UnitInfo ut) {
		try {
			if (!isEmpty(ut.getId())) {
				borrunit.setIou("归还");
				DateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss ");
				UserInfo usr = (UserInfo) req.getSession()
						.getAttribute("usrInfo");
				String usrname = usr.getUsrName();
				borrunit.setIoopperson(usrname);
				borrunit.setIoopdate(df.format(new Date()));
				borrService.saveOrUpdate(borrunit);
				ut.setId(ut.getId());
				ut.setUtstatus("在库");
				ut.setUtplace(borrunit.getIoutplace());
				unitInfoService.updatein(ut);
				map.put("success", true);
				map.put("msg", "归还信息提交成功");
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

	// 根据资财编号获取要归还的Unit信息
	@RequestMapping(value = "/getUnit")
	@ResponseBody
	public String getUnit(String utno) throws Exception {
		List<UnitInfo> list = borrService.findUnitListByNo(utno);
		return toJson(list);
	}

	// 统计
	@RequestMapping(value = "/countpc")
	@ResponseBody
	public String countpc(String queryString,String queryString2, HttpServletRequest req)
			throws Exception {
		map = borrService.findTotal(queryString ,queryString2);
		return StringUtil.toJson(map);
	}
}

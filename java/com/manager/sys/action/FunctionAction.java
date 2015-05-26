package com.manager.sys.action;

import static com.manager.sys.common.StringUtil.isEmpty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.sys.domain.FuncInfo;
import com.manager.sys.domain.Module;
import com.manager.sys.service.FuncService;
import com.manager.sys.service.ModuleService;

@Controller
@RequestMapping(value = "/func")
public class FunctionAction extends AbstractAction {

	private FuncService funcService;
	private ModuleService moduleService;

	@RequestMapping(value = "/index")
	public String index() {
		return "func/list";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(HttpServletRequest req, Integer page, Integer rows) throws Exception {
		if (isEmpty(page)) {
			page = 1;
		}
		if (isEmpty(rows)) {
			rows = 10;
		}
		Object objs = req.getParameter("queryString");
		if (isEmpty(objs)) {
			objs = null;
		}
		page = (page - 1) * rows;
		Map<String, Object> funcMap = funcService.findByPage(objs, page, rows);
		// sendMsg(resp, toJson(funcMap));
		return toJson(funcMap);
	}

	@RequestMapping(value = "/add")
	public String add() {
		return "func/add";
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest req, FuncInfo func) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!isEmpty(func) && !isEmpty(func.getFuncName()) && !isEmpty(func.getFuncSign())) {
			Module module = moduleService.get(func.getModuleId());
			if (!isEmpty(module)) {
				func.setModule(module);
			}
			try {
				funcService.saveOrUpdate(func);
				map.put("success", true);
				map.put("msg", "信息保存成功");
			} catch (Exception e) {
				map.put("success", false);
				map.put("msg", "提示:" + e.getMessage());
			}
		} else {
			map.put("success", false);
			map.put("msg", "功能的名称或者功能标识符不能为空");
		}
		// sendMsg(resp, toJson(map));
		return toJson(map);
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest req, String ids) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!isEmpty(ids)) {
			String[] idss = ids.split(",");
			if (!isEmpty(idss) && idss.length > 0) {
				try {
					funcService.delete(idss);
					map.put("success", true);
					map.put("msg", "信息删除成功");
				} catch (Exception e) {
					map.put("success", false);
					map.put("msg", "提示" + e.getMessage());
					e.printStackTrace();
				}
			}
			// sendMsg(resp, toJson(map));
		} else {
			map.put("success", false);
			map.put("msg", "请选择删除的信息编号");
		}
		return toJson(map);
	}

	@RequestMapping(value = "/edit/{funcId}")
	public String edit(HttpServletRequest req, @PathVariable("funcId") Integer funcId) {
		if (!isEmpty(funcId)) {
			FuncInfo func = funcService.get(funcId);
			req.setAttribute("func", func);
		}
		return "func/add";
	}

	@ResponseBody
	@RequestMapping(value = "/checkSign")
	public String checkFuncSign(String sign) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer funcSign = funcService.findByFuncSign(sign);
		map.put("sign", sign);
		if (isEmpty(funcSign)) {
			map.put("success", true);
			map.put("msg", "标识符可以使用");
		} else {
			map.put("success", false);
			map.put("msg", "标识符不可用");
		}
		return toJson(map);
	}

	public void setFuncService(FuncService funcService) {
		this.funcService = funcService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
}

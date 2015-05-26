package com.manager.sys.action;

import static com.manager.sys.common.StringUtil.isEmpty;
import static com.manager.sys.common.StringUtil.isNum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.manager.sys.domain.FuncInfo;
import com.manager.sys.domain.Module;
import com.manager.sys.domain.RoleInfo;
import com.manager.sys.service.ModuleService;
import com.manager.sys.service.RoleInfoService;

@Controller
@RequestMapping(value = "/module")
public class ModuleAction extends AbstractAction {
	private ModuleService moduleService;
	private RoleInfoService roleInfoService;

	/**
	 * 跳转到栏目信息列表的界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index() {
		return "module/list";
	}

	/**
	 * 显示栏目信息的集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(HttpServletRequest req, Integer page, Integer rows) throws Exception {
		String queryString = req.getParameter("queryString");
		if (isEmpty(queryString)) {
			queryString = null;
		}
		if (isEmpty(page)) {
			page = 1;
		}
		if (isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		Map<String, Object> mods = moduleService.findByPage(page, rows, queryString);
		return toJson(mods);
	}

	/**
	 * 跳转到添加信息的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest req) throws Exception {
		Module module = new Module();
		module.setParentId(0);
		req.setAttribute("module", module);
		return "module/add";
	}

	/**
	 * 保存即更新栏目信息
	 * 
	 * @param resp
	 * @param module
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest req, Module module) throws IOException {
	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!isEmpty(module) && !isEmpty(module.getModuleName()) && !isEmpty(module.getModuleSign())) {
				Module modparent = moduleService.get(module.getParentId());
				module.setParent(modparent);
				moduleService.saveOrupdate(module);
				map.put("success", true);
				map.put("msg", "信息保存成功");
			} else {
				map.put("success", false);
				map.put("msg", "栏目名称或者栏目标识不能为空");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "信息保存失败:" + e.getMessage());
			e.printStackTrace();
		}
		return toJson(map);
	}

	/**
	 * 编辑栏目信息
	 * 
	 * @param resp
	 * @param req
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "edit/{id}")
	public String edit(HttpServletRequest req, @PathVariable("id") String id) throws Exception {
		Integer tempId = 0;
		tempId = isNum(id) ? Integer.valueOf(id) : 0;
		Module mod = moduleService.get(tempId);
		if (!isEmpty(mod)) {
			if (isEmpty(mod.getParent())) {
				mod.setParentId(0);
			} else {
				mod.setParentId(mod.getParent().getId());
			}
		}
		req.setAttribute("module", mod);
		return "module/add";
	}

	/**
	 * 删除信息
	 * 
	 * @param resp
	 * @param req
	 * @param ids
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest req, String ids) throws IOException {
		String msg = null;
		boolean success = false;
		String[] idss = null;
		if (!isEmpty(ids)) {
			idss = ids.split(",");
		}
		if (null != idss && idss.length > 0) {
			try {
				moduleService.delete(idss);
				msg = "栏目信息删除成功";
				success = true;
			} catch (Exception ex) {
				msg = "提示:" + ex.getMessage();
			}
		} else {
			msg = "没有选择要删除的栏目信息";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		// sendMsg(resp, toJson(map));
		return toJson(map);
	}

	/**
	 * 检查栏目标识符是否唯一
	 * 
	 * @return
	 */
	@RequestMapping(value = "/check")
	@ResponseBody
	public String check(String sign) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = mv.getModel();
		try {
			String moduleName = moduleService.findModuleName(sign);
			map.put("sign", sign);
			if (isEmpty(moduleName)) {
				map.put("success", true);
				map.put("msg", "栏目标识可以使用");
			} else {
				map.put("success", false);
				map.put("msg", sign+"\t\t标识不唯一，不能使用");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "发生异常，请重新输入");
			e.printStackTrace();
		}
		return toJson(map);
	}

	/**
	 * 添加栏目时显示上级栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getParent")
	@ResponseBody
	public String getParentModule() throws Exception {
		List<Map<String, Object>> list = moduleService.getParentModules();
		return toJson(list);
	}

	@RequestMapping(value = "/funcModule/{roleId}")
	@ResponseBody
	public String funcAndModule(@PathVariable Integer roleId) throws Exception {
		List<Map<String, Object>> dtos = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = moduleService.findTree(0, roleId);// 获取顶级栏目
		Map<Integer, Integer> roleMapFun = new HashMap<Integer, Integer>();
		if (!isEmpty(roleId)) {
			RoleInfo role = roleInfoService.get(roleId);
			Set<FuncInfo> funcset = role.getFuncs();
			for (FuncInfo func : funcset) {
				roleMapFun.put(func.getId(), role.getId());
			}
		}
		if (list != null) {
			for (Map<String, Object> dto : list) {
				Object tempId = dto.get("id");
				Integer childId = 0;
				childId = !isEmpty(tempId) ? Integer.valueOf(tempId.toString()) : 0;
				List<Map<String, Object>> modsChildList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> child = moduleService.findTree(childId, roleId);// 获取二级栏目
				if (child != null) {
					/* 根据二级栏目的编号获取二级栏目的信息及二级栏目下的所有功能 */
					for (Map<String, Object> cmap : child) {
						List<Map<String, Object>> funcList = new ArrayList<Map<String, Object>>();
						Object cmapId = cmap.get("id");
						Integer childCmapId = 0;
						childCmapId = !isEmpty(cmapId) ? Integer.valueOf(cmapId.toString()) : 0;
						Module mods = moduleService.get(childCmapId);
						if (!isEmpty(mods)) {
							Set<FuncInfo> funcset = mods.getFuncs();
							if (!isEmpty(funcset) && funcset.size() > 0) {
								for (FuncInfo func : funcset) {
									Map<String, Object> funcMap = new HashMap<String, Object>();
									funcMap.put("id", func.getId());
									funcMap.put("text", func.getFuncName());
									if (roleMapFun.containsKey(func.getId())) {
										funcMap.put("checked", true);
									} else {
										funcMap.put("checked", false);
									}
									Map<String, String> attr = new HashMap<String, String>();
									attr.put("attr", "funcnode");
									funcMap.put("attributes", attr);
									funcList.add(funcMap);
								}
								System.out.println(funcList);
							}
						}
						cmap.put("children", funcList);
						modsChildList.add(cmap);
					}
					dto.put("children", modsChildList);
				}
				dtos.add(dto);
			}
		}
		System.out.println("功能栏目的JSON:" + toJson(dtos));
		return toJson(dtos);
	}

	/**
	 * 显示树型结构栏目
	 * 
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public String preModuleTree() throws Exception {
		System.out.println("开始创建分配栏目的树");
		Integer roleId = 0;
		List<Map<String, Object>> dtos = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = moduleService.findTree(0, roleId);
		if (list != null) {
			for (Map<String, Object> dto : list) {
				Object tempId = dto.get("id");
				// String[] strId = !isEmpty(tempId) ?
				// tempId.toString().split("#") : null;
				Integer childId = 0;
				// if (isEmpty(strId) && strId.length > 0) {
				childId = !isEmpty(tempId) ? Integer.valueOf(tempId.toString()) : 0;
				// }
				// System.out.println(strId+"____strId0:"+strId[0]+"____strId1:"+strId[1]);
				System.out.println(tempId + "__childId:" + childId);
				List<Map<String, Object>> child = moduleService.findTree(childId, roleId);
				if (child != null) {
					dto.put("children", child);
				}
				dtos.add(dto);
			}
		}
		return toJson(dtos);
	}

	public String createTree() throws Exception {
		// List<MenuTreeDto> dtos = new ArrayList<MenuTreeDto>();
		// List<Module> list = moduleService.find(0);
		// if (list != null) {
		// for (Module mod : list) {
		// MenuTreeDto dto = new MenuTreeDto();
		// dto.setId(mod.getId().toString());
		// dto.setText(mod.getModuleName());
		// dto.setLink(mod.getModuleHref());
		// if (0 == mod.getParentId()) {
		// dto.setLeaf(false);
		// } else {
		// dto.setLeaf(true);
		// }
		// dtos.add(dto);
		// }
		// }
		// sendMsg(jsonResult);
		return null;
	}

	/**
	 * @param moduleService
	 *            the moduleService to set
	 */
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setRoleInfoService(RoleInfoService roleInfoService) {
		this.roleInfoService = roleInfoService;
	}
}

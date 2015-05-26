package com.manager.sys.action;

import static com.manager.sys.common.StringUtil.isEmpty;
import static com.manager.sys.common.StringUtil.isNum;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.sys.domain.FuncInfo;
import com.manager.sys.domain.Module;
import com.manager.sys.domain.RoleInfo;
import com.manager.sys.service.FuncService;
import com.manager.sys.service.ModuleService;
import com.manager.sys.service.RoleInfoService;

/**
 * 角色信息管理的控制器。
 * 
 * @author HMY
 * 
 */
@Controller
@RequestMapping(value = "/role")
public class RoleInfoAction extends AbstractAction {
	private RoleInfoService roleInfoService;
	private ModuleService moduleService;
	private FuncService funcService;

	/**
	 * 跳转到信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index() {
		return "role/list";
	}

	/**
	 * 通过异步获取信息，并转化为json字符串显示到表格
	 * 
	 * @param response
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(HttpServletRequest request, Integer page, Integer rows,
			ModelMap mod) throws Exception {
		String roleName = request.getParameter("queryString");
		if (isEmpty(roleName)) {
			roleName = null;
		}
		// System.out.println("角色名称：" + request.getParameter("queryString"));
		if (isEmpty(page)) {
			page = 1;
		}
		if (isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		Map<String, Object> map = roleInfoService.findByPage(roleName, page,
				rows);
		return toJson(map);
	}

	/**
	 * 跳转到添加信息的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add() {
		return "role/add";
	}

	/**
	 * 异步调用save方法保存角色信息
	 * 
	 * @param response
	 * @param model
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, RoleInfo role)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("角色信息：" + role);
		if (null != role) {
			role.setCreateTime(getNow());
		}
		String moduleVal = request.getParameter("treeVal");
		// System.out.println("选择的栏目：" + request.getParameter("treeVal"));
		Set<Module> modules = new HashSet<Module>();
		Set<FuncInfo> funcs = new HashSet<FuncInfo>();
		if (!isEmpty(moduleVal)) {
			String[] moduleIds = moduleVal.split(",");
			for (String strId : moduleIds) {
				// System.out.println("栏目编号值：" + strId + "  是否为空：" +
				// !isEmpty(strId));
				if (!isEmpty(strId)) {
					String[] treeVal = strId.split("#");
					if (null != treeVal && treeVal.length == 2) {
						// System.out.println("长度：" + treeVal.length + "  类型：" +
						// treeVal[0] + "   功能编号：" + treeVal[1]);
						if ("modules".equals(treeVal[0])) {
							System.out.println(treeVal[1]);
							Module mod = moduleService
									.get(isNum(treeVal[1]) ? Integer
											.valueOf(treeVal[1]) : 0);
							modules.add(mod);
							role.setModules(modules);
						}
						if ("funcnode".equals(treeVal[0])) {
							// System.out.println("funcode:" + treeVal.length +
							// "  类型：" + treeVal[0]
							// + "   功能编号：" + treeVal[1]);
							FuncInfo func = funcService
									.get(isNum(treeVal[1]) ? Integer
											.valueOf(treeVal[1]) : 0);
							// System.out.println("funInfo添加角色时:" + func);
							// if (!isEmpty(func)) {
							funcs.add(func);
							// System.out.println(funcs);
							role.setFuncs(funcs);
							// }
						}
					}
				}
			}
			roleInfoService.save(role);
			map.put("success", true);
			map.put("msg", "信息保存成功");
		} else {
			map.put("success", false);
			map.put("msg", "请选择栏目信息");
		}
		String str = toJson(map);
		System.out.println("添加栏目角色：" + str);
		// sendMsg(response, toJson(map));// 手动使用返回技术
		return str;
	}

	/**
	 * 删除角色信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(String ids) throws IOException {
		String msg;
		boolean success = false;
		String[] idss = null;
		if (!isEmpty(ids)) {
			idss = ids.split(",");
		}
		if (null != idss && idss.length > 0) {
			try {
				roleInfoService.delete(idss);
				msg = "角色信息删除成功";
				success = true;
			} catch (IllegalAccessException e) {
				msg = e.getMessage();
			} catch (Exception ex) {
				msg=ex.getMessage();
//				msg = "该角色下有栏目信息，请先解除与栏目的关联";
			}
		} else {
			msg = "没有选择要删除的角色信息";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		return toJson(map);
	}

	/**
	 * 编辑信息
	 * 
	 * @param response
	 * @param model
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{roleId}")
	public String edit(HttpServletRequest request,
			@PathVariable("roleId") String roleId) throws Exception {
		// System.out.println(roleId);
		// System.out.println("编辑信息");
		RoleInfo role = roleInfoService.get(isNum(roleId) ? Integer
				.valueOf(roleId) : 0);
		if (isEmpty(role)) {
			return null;
		}
		request.setAttribute("role", role);
		return "role/add";
	}

	/**
	 * @param roleInfoService
	 *            the roleInfoService to set
	 */
	public void setRoleInfoService(RoleInfoService roleInfoService) {
		this.roleInfoService = roleInfoService;
	}

	/**
	 * @param moduleService
	 *            the moduleService to set
	 */
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setFuncService(FuncService funcService) {
		this.funcService = funcService;
	}
}

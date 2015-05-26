package com.manager.sys.action;

import static com.manager.sys.common.StringUtil.isNum;
import static com.manager.sys.common.StringUtil.isEmpty;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.sys.domain.RoleInfo;
import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.RoleInfoService;
import com.manager.sys.service.UserInfoService;

@Controller
@RequestMapping(value = "/usr")
public class UserInfoAction extends AbstractAction {

	private UserInfoService userInfoService;
	private RoleInfoService roleInfoService;

	@RequestMapping(value = "/desk")
	public String myDesk() {
		return "usr/mydesk";
	}

	/**
	 * 跳转到显示记录的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index() {
		return "usr/list";
	}

	/**
	 * 罗列用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String list(HttpServletRequest req, Integer page, Integer rows)
			throws Exception {
		String queryString = req.getParameter("queryString");
		if (isEmpty(page)) {
			page = 1;
		}
		if (isEmpty(rows)) {
			rows = 10;
		}
		page = (page - 1) * rows;
		Map<String, Object> users = userInfoService.findByPage(page, rows,
				queryString);
		return toJson(users);
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public String add() throws Exception {
		return "usr/add";
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest req, UserInfo userInfo)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] roleIds = req.getParameterValues("roleIds");
		if (isEmpty(userInfo) || isEmpty(userInfo.getUsrName())) {
			map.put("success", false);
			map.put("msg", "用户帐号或者用户名称不能为空");
		} else if (!isEmpty(roleIds) && roleIds.length > 0) {
			Set<RoleInfo> roleSet = new HashSet<RoleInfo>();
			for (String roleId : roleIds) {
				try {
					if (!isEmpty(roleId)) {
						RoleInfo role = roleInfoService
								.get(isNum(roleId) ? Integer.valueOf(roleId)
										: 0);
						roleSet.add(role);
						userInfo.setRoles(roleSet);
					}
				} catch (NumberFormatException e) {
					map.put("success", false);
					map.put("msg", "错误:" + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					map.put("success", false);
					map.put("msg", "错误:" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		try {
			if (!isEmpty(userInfo.getUsrName())) {
				userInfo.setCreateTime(getNow());
				// 初始化的密码
				userInfo.setPassword("111111");
				userInfoService.saveOrUpdate(userInfo);
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

	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest req, UserInfo userInfo)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] roleIds = req.getParameterValues("roleIds");
		if (isEmpty(userInfo) || isEmpty(userInfo.getUsrName())) {
			map.put("success", false);
			map.put("msg", "用户帐号或者用户名称不能为空");
		} else if (!isEmpty(roleIds) && roleIds.length > 0) {
			Set<RoleInfo> roleSet = new HashSet<RoleInfo>();
			for (String roleId : roleIds) {
				try {
					if (!isEmpty(roleId)) {
						RoleInfo role = roleInfoService
								.get(isNum(roleId) ? Integer.valueOf(roleId)
										: 0);
						roleSet.add(role);
						userInfo.setRoles(roleSet);
					}
				} catch (NumberFormatException e) {
					map.put("success", false);
					map.put("msg", "错误:" + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					map.put("success", false);
					map.put("msg", "错误:" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		try {
			if (!isEmpty(userInfo.getUsrName())) {
				userInfo.setCreateTime(getNow());
				// 初始化的密码
				userInfoService.update(userInfo);
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
	
	
	@RequestMapping(value = "/edit/{usrId}")
	public String edit(HttpServletRequest req,
			@PathVariable("usrId") Integer usrId) throws Exception {
		// System.out.println(usrId);
		UserInfo userInfo = userInfoService.get(usrId);
		if (!isEmpty(userInfo)) {
			StringBuilder str = new StringBuilder();
			Set<RoleInfo> roles = userInfo.getRoles();
			if (roles != null && roles.size() > 0) {
				for (RoleInfo role : roles) {
					if (!isEmpty(role)) {
						str.append(role.getId() + ",");
					}
				}
			}
			System.out.println("roleIds:"+str);
			String temp = null;
			if (!isEmpty(str) && str.length() > 0) {
				temp = str.substring(0, str.length() - 1);
			} else {
				temp = str.toString();
			}
			req.setAttribute("roleIds", temp);
		}
		req.setAttribute("usr", userInfo);
		return "usr/edit";
	}

	/**
	 * 删除用户信息
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
		Map<String, Object> map = new HashMap<String, Object>();
		if (!isEmpty(ids)) {
			String[] id = ids.split(",");
			if (!isEmpty(id) && id.length > 0) {
				try {
					userInfoService.delete(id);
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
			map.put("msg", "请选择删除的用户信息");
		}
		return toJson(map);
	}

	@RequestMapping(value = "/check")
	@ResponseBody
	public String checkUsrId(String usrId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String usrName = userInfoService.findUsrName(usrId);
			// System.out.println("用户名："+usrId);
			map.put("usr", usrId);
			if (isEmpty(usrName)) {
				map.put("success", true);
				map.put("msg", "帐号可以使用");
			} else {
				map.put("success", false);
				map.put("msg", "帐号已存在，不能使用，请重输入");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "发生异常");
			e.printStackTrace();
		}
		return toJson(map);
	}

	/**
	 * 获取用户角色的集合
	 * 
	 * @param resp
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoles")
	@ResponseBody
	public String getRoleList(HttpServletRequest req) throws Exception {
		return toJson(roleInfoService.getAllRoles());
	}

	/**
	 * @param userInfoService
	 *            the userInfoService to set
	 */
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/**
	 * @param roleInfoService
	 *            the roleInfoService to set
	 */
	public void setRoleInfoService(RoleInfoService roleInfoService) {
		this.roleInfoService = roleInfoService;
	}
	
}

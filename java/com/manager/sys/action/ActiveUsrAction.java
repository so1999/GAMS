package com.manager.sys.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manager.sys.common.StringUtil;
import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.UserInfoService;

/**
 * 当前用户的控制器
 * 
 * @author Cheng
 * 
 */
@Controller
@RequestMapping(value = "/acusr")
public class ActiveUsrAction {
	private UserInfoService userInfoService;
	private Map<String, Object> map = new HashMap<String, Object>();


	@RequestMapping(value = "/indexpswd")
	public String indexPswd() {
		return "activeUsr/update_pswd";
	}

	@RequestMapping(value = "/updatePswd")
	@ResponseBody
	public String updateUsrPswd(HttpServletRequest req, String password,
			String newpswd, String oldpswd) throws Exception {
		HttpSession session = req.getSession(false);
		Object obj = session.getAttribute("usrInfo");
		String msg = "";
		boolean success = false;
		if (!StringUtil.isEmpty(obj)) {
			UserInfo usr = (UserInfo) obj;
			if (!StringUtil.isEmpty(usr)) {
				String usrname = usr.getUsrName();
				if (!StringUtil.isEmpty(usr.getPassword())
						&& usr.getPassword().equals(password)) {
					if (!StringUtil.isEmpty(newpswd) && newpswd.equals(oldpswd)) {
						userInfoService.updatePswd(usrname, newpswd);
						msg = "密码修改成功";
						success = true;
					} else {
						msg = "新密码和确认密码不一致";
					}
				} else {
					msg = "旧密码错误，不能修改密码";
				}
			}
		}
		map.put("msg", msg);
		map.put("success", success);
		return StringUtil.toJson(map);
	}



	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
}

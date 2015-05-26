package com.manager.sys.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.UserInfoService;

import static com.manager.sys.common.StringUtil.isEmpty;

@Controller
public class LoginAction {
	private UserInfoService userInfoService;

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest req, HttpServletResponse resp,
			String usrName, String password) throws Exception {
		boolean checkUsrId = false;
		UserInfo usr = null;
		if (isEmpty(usrName)) {
			req.setAttribute("error", "帐号不能为空");
			checkUsrId = true;
		} else if (isEmpty(password)) {
			req.setAttribute("error", "密码不能为空");
			checkUsrId = true;
		} else {
			usr = userInfoService.findUserByName(usrName);
			System.out.println(usrName + "__用户信息：" + usr);
			checkUsrId = false;
		}
		if (isEmpty(usr)) {
			req.setAttribute("error", "用户不存在");
			checkUsrId = true;
		} else {
			if (!usr.getPassword().equals(password)) {
				req.setAttribute("error", "用户名或密码错误");
				checkUsrId = true;
			} else {
				checkUsrId = false;
			}
		}
		if (checkUsrId) {
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			return null;
		} else {
			
			req.getSession().setAttribute("usrInfo", usr);
			req.getSession().setAttribute("uname", usrName);
			req.getSession().setAttribute("part", usr.getPart());
		}
		return "welcome";
	}
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.removeAttribute("loginUsr");
			session.invalidate();
		}
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		return null;
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
}

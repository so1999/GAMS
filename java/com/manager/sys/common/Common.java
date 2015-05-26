package com.manager.sys.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.manager.sys.domain.UserInfo;

import static com.manager.sys.common.StringUtil.isEmpty;

public class Common {
	public static List<Integer> toList(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		Integer[] ids = StringUtil.stringToInteger(args);
		if (args.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				list.add(!isEmpty(ids[i]) ? ids[i] : 0);
			}
		}
		return list;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param req
	 * @return
	 */
	public static UserInfo getUsrInfo(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		Object obj = null;
		if (!isEmpty(session)) {
			obj = session.getAttribute("loginUsr");
		}
		if (!StringUtil.isEmpty(obj)) {
			UserInfo usr = (UserInfo) obj;
			return usr;
		} else {
			return new UserInfo();
		}
	}
}

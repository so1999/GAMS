package com.manager.sys.auth;

import static com.manager.sys.common.StringUtil.isEmpty;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.manager.sys.common.Common;
import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.UserInfoService;

public class FuncDivAuthorTag extends TagSupport {
	private static final long serialVersionUID = 404217083997539081L;
	private boolean allowAuth = false;
	private UserInfoService userinfoService;
	private String funcSign;
	private Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public int doStartTag() throws JspException {
		/**
		 * 在这里使用spring注入，相当于在jsp中使用，因此这里获取页面的上下文。
		 */
		ServletContext servletContext = pageContext.getServletContext();
		/* spring获取容器的上下文 */
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		/* 手动获取bean */
		userinfoService = (UserInfoService) wac.getBean("userinfoService");
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		UserInfo usr = null;
		usr = Common.getUsrInfo(req);
		Map<String, String> map = new HashMap<String, String>();
		String str = null;
		if (!isEmpty(usr)) {
			str = String.valueOf(usr.getId());
		}
		try {
			map = userinfoService.checkAuthorize(str, funcSign);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (allowAuth) {
			if (!isEmpty(map) && map.containsKey(funcSign)) {
				LOG.debug("用户：{} 拥有{}的权限", usr.getUsrName(), funcSign);
				return EVAL_PAGE;
			}
		} else {
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public void setAllowAuth(boolean allowAuth) {
		this.allowAuth = allowAuth;
	}

	public void setFuncSign(String funcSign) {
		this.funcSign = funcSign;
	}
}

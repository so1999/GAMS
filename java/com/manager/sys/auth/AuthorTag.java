package com.manager.sys.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.UserInfoService;

import static com.manager.sys.common.StringUtil.isEmpty;

public class AuthorTag extends TagSupport {
	private static final long serialVersionUID = 404217083997539081L;
	private boolean allowAuth = false;
	private String funcSign;
	private String href;
	private String cssClass;
	private String text;
	private String id;
	private String onclick;
	private String iconCls;
	private boolean plain = false;
	private UserInfoService userInfoService;

	@Override
	public int doStartTag() throws JspException {
		/**
		 * 在这里使用spring注入，相当于在jsp中使用，因此这里获取页面的上下文。
		 */
		ServletContext servletContext = pageContext.getServletContext();
		/* spring获取容器的上下文 */
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		/* 手动获取bean */
		userInfoService = (UserInfoService) wac.getBean("userInfoService");
		// System.out.println("功能标识：" + funcSign);
		HttpSession session = pageContext.getSession();
		Object obj = session.getAttribute("usrInfo");
		UserInfo usr = null;
		Map<String, String> map = new HashMap<String, String>();
		String str = null;
		if (!isEmpty(obj)) {
			usr = (UserInfo) obj;
			str = String.valueOf(usr.getId());
			// System.out.println("登录的帐号：" + str);
		}
		try {
			map = userInfoService.checkAuthorize(str, funcSign);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// System.out.println("权限限制：" + (allowAuth + "____sign:" +
		// map.containsKey(funcSign)));

		if (allowAuth) {
			if (!isEmpty(map) && map.containsKey(funcSign)) {
				auth();
				return EVAL_PAGE;
			}
		} else {
			auth();
			return EVAL_PAGE;
		}
		// if (!allowAuth) {
		// auth();
		// return EVAL_PAGE;
		// } else {
		// if (allowAuth && map.containsKey(funcSign)) {
		// auth();
		// return EVAL_PAGE;
		// }
		// }
		return SKIP_BODY;
	}

	public void auth() {
		StringBuilder linkStr = new StringBuilder();
		linkStr.append("<");
		linkStr.append("a");
		if (!isEmpty(plain)) {
			linkStr.append(" plain=\"");
			linkStr.append(plain);
			linkStr.append("\"");
		}
		if (null != cssClass && cssClass.length() > 0) {
			linkStr.append("  class =\"");
			linkStr.append(cssClass);
			linkStr.append("\"");
		}
		if (null != iconCls && iconCls.length() > 0) {
			linkStr.append(" iconCls =\"");
			linkStr.append(iconCls);
			linkStr.append("\"");
		}
		if (null != href && href.length() > 0) {
			linkStr.append(" href = \"");
			linkStr.append(href);
			linkStr.append("\"");
		}
		if (null != id && id.length() > 0) {
			linkStr.append(" id =\"");
			linkStr.append(id);
			linkStr.append("\"");
		}
		if (null != onclick && onclick.length() > 0) {
			linkStr.append(" onclick =\"");
			linkStr.append(onclick);
			linkStr.append("\"");
		}
		linkStr.append(">");
		if (null != text && text.length() > 0) {
			linkStr.append(text);
		} else {
			linkStr.append("未知按钮");
		}
		linkStr.append("</a>");
		// System.err.println("执行--" + linkStr.toString());
		JspWriter out = pageContext.getOut();
		try {
			out.write(linkStr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param onclick
	 *            the onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * @param iconCls
	 *            the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setFuncSign(String funcSign) {
		this.funcSign = funcSign;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public void setPlain(boolean plain) {
		this.plain = plain;
	}

	public void setAllowAuth(boolean allowAuth) {
		this.allowAuth = allowAuth;
	}

}

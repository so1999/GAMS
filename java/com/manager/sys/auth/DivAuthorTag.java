package com.manager.sys.auth;

import static com.manager.sys.common.StringUtil.isEmpty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.manager.sys.domain.Module;
import com.manager.sys.domain.UserInfo;
import com.manager.sys.service.ModuleService;
import com.manager.sys.service.UserInfoService;

public class DivAuthorTag extends TagSupport {
	private static final long serialVersionUID = 404217083997539081L;
	private boolean allowAuth = false;
	private String funcSign;// 功能标识符
	private String id;
	private String dataOptions;
	private String iconCls;
	private UserInfoService userInfoService;
	private ModuleService moduleSrv;
	private String style;
	private StringBuilder linkStr = null;

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
		moduleSrv = (ModuleService) wac.getBean("moduleService");
		HttpSession session = pageContext.getSession();
		Object obj = session.getAttribute("usrInfo");
		UserInfo usr = null;
		Map<String, String> map = new HashMap<String, String>();
		String str = null;
		if (!isEmpty(obj)) {
			usr = (UserInfo) obj;
			str = String.valueOf(usr.getUsrName());
			// System.out.println("登录的帐号：" + str);
		}
		try {
			System.out.println("DIV标签的功能标识符：" + funcSign);
			System.out.println("当前用户的ID:"+str);
			map = userInfoService.checkAuthorize(str, null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			String xx = div(map);
			System.out.println("栏目的标识：" + xx);
			auth(xx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	// 输出字符串
	private void auth(String str) {
		JspWriter out = pageContext.getOut();
		try {
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 拼接父栏目 */
	private String div(Map<String, String> map) throws Exception {
		linkStr = new StringBuilder();
		List<Object> pmods = moduleSrv.findModule(0);
		if (!isEmpty(pmods) && pmods.size() > 0) {
			for (Object modsObj : pmods) {
				Module mods = (Module) modsObj;
				if (!isEmpty(mods)) {
					// 进行权限控制
					/*
					 * 描述：如果allowAuth为true，前面的标识符判断为true则为true,allowAuth为false,
					 * 则标识符的判断不起作用
					 */
					if (!allowAuth || (!isEmpty(map) && map.containsKey(mods.getModuleSign()))) {
						linkStr.append("<div ");
						linkStr.append(" title =\"");
						linkStr.append(mods.getModuleName());
						linkStr.append("\"");
						if (!isEmpty(style)) {
							linkStr.append(" style=\"");
							linkStr.append(style);
							linkStr.append("\"");
						}
						if (!isEmpty(dataOptions)) {
							linkStr.append(" data-options=\"");
							linkStr.append(dataOptions);
							linkStr.append("\"");
						}
						if (null != id && id.length() > 0) {
							linkStr.append(" id =\"");
							linkStr.append(id);
							linkStr.append("\"");
						}
						if (null != iconCls && iconCls.length() > 0) {
							linkStr.append(" iconCls =\"");
							linkStr.append(iconCls);
							linkStr.append("\"");
						}
						linkStr.append(">");
						List<Object> childmods = moduleSrv.findModule(mods.getId());
						if (childmods != null && childmods.size() > 0) {
							linkStr.append("<ul id=\"menu\">");
							for (Object cmodObj : childmods) {
								Module cmod = (Module) cmodObj;
								// 其逻辑和前面的一致
								if (!allowAuth || (!isEmpty(map) && map.containsKey(cmod.getModuleSign()))) {
									submenu(cmod.getModuleHref(), cmod.getModuleName());
								}
							}
							linkStr.append("</ul>");
						}
						linkStr.append("</div>");
					}
				}
			}
		}
		return linkStr.toString();
	}

	/* 拼接子栏目 */
	private void submenu(String id, String text) {
		linkStr.append("<li>");
		linkStr.append("<a ");
		if (null != id && id.length() > 0) {
			linkStr.append(" id =\"");
			linkStr.append(id);
			linkStr.append("\"");
		}
		linkStr.append(">");
		if (null != text && text.length() > 0) {
			linkStr.append(text);
		} else {
			linkStr.append("未知按钮");
		}
		linkStr.append("</a>");
		linkStr.append("</li>");
	}

	public void setAllowAuth(boolean allowAuth) {
		this.allowAuth = allowAuth;
	}

	public void setFuncSign(String funcSign) {
		this.funcSign = funcSign;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
